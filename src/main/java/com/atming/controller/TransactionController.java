package com.atming.controller;

import com.atming.annotation.UserLoginToken;
import com.atming.entity.*;
import com.atming.service.ManagerService;
import com.atming.service.TransactionService;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.DateTransformUtil;
import com.atming.utils.TimerUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.JWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.tools.jconsole.JConsole;

import java.util.*;

/**
 * @author annoming
 * @date 2021/4/9 9:24 上午
 */

@Controller
@RequestMapping(value = "profittrea")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ManagerService managerService;
    private BuyInfo transaction;
    private String operate;
    private static Result message;


    @PostMapping(value = "/transaction/{action}.do")
    @ResponseBody
    @UserLoginToken
    public Result getData(@RequestBody BuyInfo stock, @PathVariable String action) {
        operate = action;
        transaction = stock;
        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    @GetMapping(value = "/transaction/{action}.do")
    @UserLoginToken
    @ResponseBody
    public Result getData(@PathVariable String action) {
        operate = action;
        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    private boolean getInputData() {
        try {
            if (!StringUtils.isNotBlank(operate)) {
                message = Result.fail("接收前台数据出错");
                return false;
            }
        } catch (Exception e) {
            message = Result.fail("接受前台数据出错");
            return false;
        }
        return true;
    }

    private boolean dealData() {
        try {
            if ("QUERY||STOCK".equals(operate)) {
                //查询优质股票
                List<Stock> stocks = transactionService.findStock();
                if (stocks != null) {
                    message = Result.success(stocks);
                } else {
                    message = Result.fail("数据异常");
                }
            } else if ("QUERY||BUYSTOCK".equals(operate)) {
                //模糊查询买卖的股票
                String stockCode = transaction.getStockCode();
                List<Stock> buyList = transactionService.findBuyStock(stockCode);
                if (buyList != null) {
                    message = Result.success(buyList);
                } else {
                    message = Result.fail("数据异常");
                }
            } else if ("QUERY||SHARE".equals(operate)) {
                //查询资产
                String token = transaction.getStockCode();
                if (token != null) {
                    String userId = JWT.decode(token).getAudience().get(0);
                    Share share = transactionService.findOneShare(userId);
                    if (share != null) {
                        message = Result.success(share);
                    } else {
                        message = Result.fail("资产数据获取异常");
                    }
                } else {
                    message = Result.fail("token失效，请重新登录");
                }
            } else if ("STOCK||BUY".equals(operate)) {
                //校验数据
                String stockCode = transaction.getStockCode();
                String stockName = transaction.getStockName();
                String entrustType = transaction.getEntrustType();
                Double buyPrice = transaction.getBuyPrice();
                Double usefulFund = transaction.getUsefulFund();
                Integer buyNumber = transaction.getBuyNumber();
                Integer maxBuyNumber = transaction.getMaxBuyNumber();
                String organizationId = transaction.getOrganizationId();
                String userId = transaction.getUserId();
                String operation = transaction.getOperation();
                Date now = new Date();

                if (StringUtils.isBlank(stockCode)) {
                    message = Result.fail("证券代码不能为空");
                    return false;
                }

                if (StringUtils.isBlank(stockName)) {
                    message = Result.fail("证券名称不能为空");
                    return false;
                }

                if (StringUtils.isBlank(entrustType)) {
                    message = Result.fail("委托方式不能为空");
                    return false;
                }

                if (buyPrice <= 0.0) {
                    message = Result.fail("买入价格为正整数");
                    return false;
                }

                if (usefulFund <= 0.0) {
                    message = Result.fail("可用资金不足");
                    return false;
                }

                if (buyNumber <= 0) {
                    message = Result.fail("买入数量为不于零");
                    return false;
                } else {
                    if (buyNumber % 100 != 0) {
                        message = Result.fail("买入数量不为100整数倍");
                        return false;
                    }
                }

                if (maxBuyNumber <= 0) {
                    message = Result.fail("最大可买数量不足");
                    return false;
                }

                if (StringUtils.isBlank(operation)) {
                    message = Result.fail("未确认委托操作");
                    return false;
                }


                //判断是否超过可用资金
                if (transaction.getBuyPrice() * transaction.getBuyNumber() > transaction.getUsefulFund()) {
                    message = Result.fail("资金不足");
                } else {
                    //是否超过最大可买数量
                    if (transaction.getBuyNumber() <= transaction.getMaxBuyNumber()) {
                        // TODO: 2021/4/28 判断涨跌限制
                        //插入数据到当日委托中
                        TodayEntrust todayEntrust = new TodayEntrust();
                        todayEntrust.setEntrustId(CreateIdUtil.createRandomId(9));
                        todayEntrust.setUserId(transaction.getUserId());
                        todayEntrust.setEntrustNumber(transaction.getBuyNumber());
                        todayEntrust.setEntrustPrice(transaction.getBuyPrice());
                        todayEntrust.setTransactionPrice(0.0);
                        todayEntrust.setTransactionNumber(0);
                        todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                        todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                        todayEntrust.setEntrustType("市价");
                        todayEntrust.setDescription("未成交");
                        todayEntrust.setOperation(transaction.getOperation());
                        todayEntrust.setStockCode(transaction.getStockCode());
                        todayEntrust.setStockName(transaction.getStockName());

                        int insertEntrust = transactionService.insertEntrust(todayEntrust);
                        if (insertEntrust != 1) {
                            message = Result.fail("插入委托数据失败");
                            return false;
                        }

                        //获取所在市场开市信息
                        Market market = transactionService.findMarket(organizationId);
                        Date morningStart = DateTransformUtil.transformNowDate(market.getMorningStart());
                        Date morningEnd = DateTransformUtil.transformNowDate(market.getMorningEnd());
                        Date afternoonStart = DateTransformUtil.transformNowDate(market.getAfternoonStart());
                        Date afternoonEnd = DateTransformUtil.transformNowDate(market.getAfternoonEnd());

                        //撤单数据
                        RevokeList revoke = new RevokeList();
                        revoke.setId(todayEntrust.getEntrustId());
                        revoke.setUserId(transaction.getUserId());
                        revoke.setStockCode(transaction.getStockCode());
                        revoke.setStockName(transaction.getStockName());
                        revoke.setEntrustPrice(transaction.getBuyPrice());
                        revoke.setEntrustNumber(transaction.getBuyNumber());
                        revoke.setOperation("买入");
                        revoke.setDate(DateTransformUtil.transformToString());
                        revoke.setTransactionNumber(0);
                        revoke.setTransactionPrice(0.0000);
                        //插入数据到撤销表
                        int insertCode = transactionService.insertRevokeToList(revoke);
                        if (insertCode != 1) {
                            message = Result.fail("插入失败");
                        }

                        //判断委托方式
                        if ("shijia".equals(transaction.getEntrustType())) {
                            //判断是否在可买卖时间内，更新撤单数据
                            if ((now.compareTo(morningStart) > 0
                                    && now.compareTo(morningEnd) < 0) || (
                                    now.compareTo(afternoonStart) > 0
                                            && now.compareTo(afternoonEnd) < 0)) {
                                //委托时间为开市时间 更新当日成交、资产股份、更新当日委托状态
                                /*
                                    当日成交：两种情况
                                        1、优先匹配系统中用户卖出的证券
                                        2、系统自动全部买入卖出
                                 */
                                //根据证券代码匹配市场中卖出的证券
                                List<TodayEntrust> entrust = transactionService.findStockEntrust(stockCode);
                                int updateEntrust;
                                TodayTransaction nowTransaction = new TodayTransaction();
                                Stock buyStock = transactionService.findBuyStock(todayEntrust.getStockCode()).get(0);;
                                //证券剩余可交易数量
                                int remainNumber = transaction.getBuyNumber();
                                for (TodayEntrust t : entrust
                                ) {
                                    if ("卖出".equals(t.getOperation())) {
                                        if ("未成交".equals(t.getDescription())) {
                                            //匹配相同的交易价格，卖出价和买入价相同的委托合同
                                            if (t.getEntrustPrice().equals(todayEntrust.getEntrustPrice())) {
                                                //获取市场上证券的实时数据
                                                if (remainNumber > 0) {
                                                    //卖出数量足够买入,更新买入委托数据和卖出委托
                                                    if (t.getEntrustNumber() >= remainNumber) {
                                                        //交易成功的价格为当时市场的价格
                                                        todayEntrust.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                                                        todayEntrust.setTransactionNumber(remainNumber);
                                                        todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                                        todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                                        todayEntrust.setDescription("全部成交");

                                                        //更新委托合同的部分数据
                                                        updateEntrust = transactionService.updateEntrust(todayEntrust);
                                                        if (updateEntrust != 1) {
                                                            message = Result.fail("更新委托数据失败");
                                                            return false;
                                                        }

                                                    } else {
                                                        //分批次交易
                                                        todayEntrust.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                                                        todayEntrust.setTransactionNumber(t.getEntrustNumber());
                                                        todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                                        todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                                        todayEntrust.setDescription("部分成交");

                                                        //更新委托合同数据
                                                        updateEntrust = transactionService.updateEntrust(todayEntrust);
                                                        if (updateEntrust != 1) {
                                                            message = Result.fail("更新委托数据失败");
                                                            return false;
                                                        }
                                                    }
                                                    remainNumber = remainNumber - t.getEntrustNumber();
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                //系统自动全部买入卖出
                                if (remainNumber > 0) {
                                    todayEntrust.setTransactionPrice(transaction.getBuyPrice());
                                    todayEntrust.setTransactionNumber(transaction.getBuyNumber());
                                    todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                    todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                    todayEntrust.setDescription("全部成交");

                                    //更新委托合同数据
                                    updateEntrust = transactionService.updateEntrust(todayEntrust);
                                    if (updateEntrust != 1) {
                                        message = Result.fail("更新委托数据失败");
                                        return false;
                                    }
                                }

                                //更新股份资产
                                Fund fund = new Fund();
                                fund.setUserId(todayEntrust.getUserId());
                                fund.setStockCode(todayEntrust.getStockCode());
                                fund.setStockName(todayEntrust.getStockName());
                                //T+1交易制度   T日买入股票  T+1日才能卖出    所以T日可用数量为0
                                fund.setStockNumber(todayEntrust.getEntrustNumber());
                                fund.setUsefulNumber(0);
                                fund.setFreezeNumber(todayEntrust.getTransactionNumber());
                                // TODO: 2021/4/28 成本价取多手平均值
                                fund.setCostPrice(todayEntrust.getTransactionPrice());
                                // TODO: 2021/4/28 市场最新价
                                fund.setMarketPrice(0.0);
                                fund.setMarketValue(fund.getMarketPrice() * fund.getStockNumber());
                                //浮动盈亏 = （市价 - 成本价- 手续费）*100
                                fund.setFloatProfit((fund.getMarketPrice() - fund.getCostPrice() - 0) * 100);
                                //盈亏比例 = （市价  - 成本价）/成本价*100
                                fund.setProfitRatio(((fund.getMarketPrice() - fund.getCostPrice()) / fund.getMarketPrice() * 100) + "%");
                                transactionService.insertStockFund(fund);

                                // TODO: 2021/5/11 更新撤单数据  匹配用户撤单数据
                                int revokeResult = transactionService.deleteRevokeById(revoke.getId());
                                if(revokeResult != 1){
                                    message = Result.fail("更新撤单数据失败");
                                }

                                // TODO: 2021/5/11 更新成交数据     匹配用户更新
                                //当前用户更新成交数据
                                nowTransaction.setTransactionId(todayEntrust.getEntrustId());
                                nowTransaction.setUserId(todayEntrust.getUserId());
                                //成交数量为委托成功数量
                                nowTransaction.setTransactionNumber(todayEntrust.getTransactionNumber());
                                nowTransaction.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                                nowTransaction.setTransactionAmount(nowTransaction.getTransactionPrice() * nowTransaction.getTransactionNumber());
                                nowTransaction.setTransactionDate(DateTransformUtil.transformToDate());
                                nowTransaction.setRevokeNumber(0);
                                nowTransaction.setOperation(todayEntrust.getOperation());
                                nowTransaction.setStockCode(todayEntrust.getStockCode());
                                nowTransaction.setStockName(todayEntrust.getStockName());

                                int transactionResult = transactionService.insertTransaction(nowTransaction);
                                if (transactionResult != 1) {
                                    message = Result.fail("插入失败");
                                }

                            }

                            //更新资金数据前查询撤单数据
                            List<RevokeList> revokeLists = transactionService.findUserRevoke(revoke.getUserId());
                            double revokeCount = 0.0;
                            if(revokeLists != null){
                                for (RevokeList r:revokeLists
                                ) {
                                    if (r.getStockCode().equals(todayEntrust.getStockCode())) {
                                        revokeCount =  r.getEntrustPrice() * r.getEntrustNumber() * (1 + market.getCommission());
                                    }
                                }
                            }

                            //更新资金资产
                            Share share = new Share();
                            share.setUserId(todayEntrust.getUserId());
                            share.setAvailableBalance(0.0);
                            share.setFundBalance(0.0);
                            share.setFreezeBalance(revokeCount);
                            share.setTotalFund(0.0);
                            share.setTotalProfit(0.0);
                            int updateShare = transactionService.updateShare(share);
                            if(updateShare != 1){
                                message = Result.fail("更新资金数据失败");
                                return false;
                            }
                            message = Result.success("委托成功");
                        } else if ("xianjia".equals(transaction.getEntrustType())) {
                        } else {
                            message = Result.fail("未知委托方式");
                        }
                    } else {
                        message = Result.fail("超过最大可买数量");
                    }
                }

            } else if ("STOCK||SALE".equals(operate)) {
                //校验数据
                String stockCode = transaction.getStockCode();
                String stockName = transaction.getStockName();
                String entrustType = transaction.getEntrustType();
                Double salePrice = transaction.getBuyPrice();
                Integer saleNumber = transaction.getBuyNumber();
                Integer maxSaleNumber = transaction.getMaxBuyNumber();
                String organizationId = transaction.getOrganizationId();
                String userId = transaction.getUserId();
                String operation = transaction.getOperation();

                Date now = new Date();

                if (StringUtils.isBlank(stockCode)) {
                    message = Result.fail("证券代码不能为空");
                    return false;
                }

                if (StringUtils.isBlank(stockName)) {
                    message = Result.fail("证券名称不能为空");
                    return false;
                }

                if (StringUtils.isBlank(entrustType)) {
                    message = Result.fail("委托方式不能为空");
                    return false;
                }

                if (salePrice <= 0.0) {
                    message = Result.fail("卖出入价格为正数");
                    return false;
                }


                if (saleNumber <= 0) {
                    message = Result.fail("卖出数量必须在1和最大可卖数量之间");
                    return false;
                }

                if (maxSaleNumber <= 0) {
                    message = Result.fail("持股数为零不能卖出");
                    return false;
                }

                if (StringUtils.isBlank(operation)) {
                    message = Result.fail("未确认委托操作");
                    return false;
                }

                //判断委托方式


                //是否超过最大可卖数量
                if (saleNumber <= maxSaleNumber) {
                    //插入数据到当日委托中
                    TodayEntrust todayEntrust = new TodayEntrust();
                    todayEntrust.setEntrustId(CreateIdUtil.createRandomId(9));
                    todayEntrust.setUserId(transaction.getUserId());
                    todayEntrust.setEntrustNumber(transaction.getBuyNumber());
                    todayEntrust.setEntrustPrice(transaction.getBuyPrice());
                    todayEntrust.setTransactionPrice(0.0);
                    todayEntrust.setTransactionNumber(0);
                    todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                    todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                    todayEntrust.setEntrustType("市价");
                    todayEntrust.setDescription("未成交");
                    todayEntrust.setOperation(transaction.getOperation());
                    todayEntrust.setStockCode(transaction.getStockCode());
                    todayEntrust.setStockName(transaction.getStockName());

                    int insertEntrust = transactionService.insertEntrust(todayEntrust);
                    if (insertEntrust != 1) {
                        message = Result.fail("插入委托数据失败");
                        return false;
                    }

                    //获取所在市场开市信息
                    Market market = transactionService.findMarket(organizationId);
                    Date morningStart = DateTransformUtil.transformNowDate(market.getMorningStart());
                    Date morningEnd = DateTransformUtil.transformNowDate(market.getMorningEnd());
                    Date afternoonStart = DateTransformUtil.transformNowDate(market.getAfternoonStart());
                    Date afternoonEnd = DateTransformUtil.transformNowDate(market.getAfternoonEnd());

                    RevokeList revoke = new RevokeList();
                    revoke.setId(todayEntrust.getEntrustId());
                    revoke.setUserId(transaction.getUserId());
                    revoke.setStockCode(transaction.getStockCode());
                    revoke.setStockName(transaction.getStockName());
                    revoke.setEntrustPrice(transaction.getBuyPrice());
                    revoke.setEntrustNumber(transaction.getBuyNumber());
                    revoke.setOperation("卖出");
                    revoke.setDate(DateTransformUtil.transformToString());
                    revoke.setTransactionNumber(0);
                    revoke.setTransactionPrice(0.0000);
                    //插入数据到撤销表
                    int insertCode = transactionService.insertRevokeToList(revoke);
                    if (insertCode != 1) {
                        message = Result.fail("插入失败");
                    }

                    if ("shijia".equals(transaction.getEntrustType())) {
                        //判断是否在可买卖时间内,更新撤单数据
                        if ((now.compareTo(morningStart) > 0
                                && now.compareTo(morningEnd) < 0) || (
                                now.compareTo(afternoonStart) > 0
                                        && now.compareTo(afternoonEnd) < 0)) {
                            //委托时间为开市时间 更新当日成交、资产股份、更新当日委托状态
                                /*
                                    当日成交：两种情况
                                        1、优先匹配系统中用户买入的证券
                                        2、系统自动全部买入买入
                                 */
                            //根据证券代码匹配市场中买入的证券
                            List<TodayEntrust> entrust = transactionService.findStockEntrust(stockCode);
                            int updateEntrust;
                            TodayTransaction nowTransaction = new TodayTransaction();
                            Stock buyStock = transactionService.findBuyStock(todayEntrust.getStockCode()).get(0);
                            //证券剩余可交易数量
                            int remainNumber = transaction.getBuyNumber();
                            for (TodayEntrust t : entrust
                            ) {
                                if ("买入".equals(t.getOperation())) {
                                    if ("未成交".equals(t.getDescription())) {
                                        //匹配相同的交易价格，卖出价和买入价相同的委托合同
                                        if (t.getEntrustPrice().equals(todayEntrust.getEntrustPrice())) {
                                            //获取市场上证券的实时数据
                                            if (remainNumber > 0) {
                                                //卖出数量足够买入,更新买入委托数据和卖出委托
                                                if (t.getEntrustNumber() >= remainNumber) {
                                                    //交易成功的价格为当时市场的价格
                                                    todayEntrust.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                                                    todayEntrust.setTransactionNumber(remainNumber);
                                                    todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                                    todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                                    todayEntrust.setDescription("全部成交");

                                                    //更新委托合同的部分数据
                                                    updateEntrust = transactionService.updateEntrust(todayEntrust);
                                                    if (updateEntrust != 1) {
                                                        message = Result.fail("更新委托数据失败");
                                                        return false;
                                                    }
                                                } else {
                                                    //分批次交易
                                                    todayEntrust.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                                                    todayEntrust.setTransactionNumber(t.getEntrustNumber());
                                                    todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                                    todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                                    todayEntrust.setDescription("部分成交");

                                                    //更新委托合同数据
                                                    updateEntrust = transactionService.updateEntrust(todayEntrust);
                                                    if (updateEntrust != 1) {
                                                        message = Result.fail("更新委托数据失败");
                                                        return false;
                                                    }
                                                }
                                                remainNumber = remainNumber - t.getEntrustNumber();
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }


                            //系统自动全部买入卖出
                            if (remainNumber > 0) {
                                todayEntrust.setTransactionPrice(transaction.getBuyPrice());
                                todayEntrust.setTransactionNumber(transaction.getBuyNumber());
                                todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                todayEntrust.setDescription("全部成交");

                                //更新委托合同数据
                                updateEntrust = transactionService.updateEntrust(todayEntrust);
                                if (updateEntrust != 1) {
                                    message = Result.fail("更新数据到失败");
                                    return false;
                                }
                            }

                            // TODO: 2021/5/11 更新撤单数据  匹配用户撤单数据
                            int revokeResult = transactionService.deleteRevokeById(revoke.getId());
                            if(revokeResult != 1){
                                message = Result.fail("更新撤单数据失败");
                            }

                            // TODO: 2021/5/11 更新成交数据  匹配用户成交数据
                            //当前用户更新成交数据
                            nowTransaction.setTransactionId(todayEntrust.getEntrustId());
                            nowTransaction.setUserId(todayEntrust.getUserId());
                            //成交数量为委托成功数量
                            nowTransaction.setTransactionNumber(todayEntrust.getTransactionNumber());
                            nowTransaction.setTransactionPrice(Double.parseDouble(buyStock.getNewPrice()));
                            nowTransaction.setTransactionAmount(nowTransaction.getTransactionPrice() * nowTransaction.getTransactionNumber());
                            nowTransaction.setTransactionDate(DateTransformUtil.transformToDate());
                            nowTransaction.setRevokeNumber(0);
                            nowTransaction.setOperation(todayEntrust.getOperation());
                            nowTransaction.setStockCode(todayEntrust.getStockCode());
                            nowTransaction.setStockName(todayEntrust.getStockName());

                            int transactionResult = transactionService.insertTransaction(nowTransaction);
                            if (transactionResult != 1) {
                                message = Result.fail("插入失败");
                            }


                            // TODO: 2021/5/11 更新资产数据 
                            //更新当前用户资产股份数据
                            operate = "update";
                            Fund fund = transactionService.findFundByStock(todayEntrust.getStockCode());
                            if (fund == null) {
                                message = Result.fail("获取资产股份数据异常");
                                return false;
                            }
                            fund.setUserId(todayEntrust.getUserId());
                            fund.setStockCode(todayEntrust.getStockCode());
                            fund.setStockName(todayEntrust.getStockName());
                            //todo T+1交易制度   T日买入股票  T+1日才能卖出    所以T日可用数量为0
                            fund.setStockNumber(todayEntrust.getEntrustNumber());
                            fund.setUsefulNumber(0);
                            fund.setFreezeNumber(todayEntrust.getEntrustNumber());
                            // TODO: 2021/4/28 成本价取多手平均值
                            fund.setCostPrice(todayEntrust.getTransactionPrice());
                            // TODO: 2021/4/28 市场最新价
                            fund.setMarketPrice(0.0);
                            fund.setMarketValue(fund.getMarketPrice() * fund.getStockNumber());
                            /**浮动盈亏 = （市价 - 成本价- 手续费）*100*/
                            fund.setFloatProfit((fund.getMarketPrice() - fund.getCostPrice() - 0) * 100);
                            /**盈亏比例 = （市价  - 成本价）/成本价*100*/
                            fund.setProfitRatio(((fund.getMarketPrice() - fund.getCostPrice()) / fund.getMarketPrice() * 100) + "%");

                            if ("update".equals(operate)) {
                                //更新证券资产
                                transactionService.updateStockFund(fund);
                            } else {
                                message = Result.fail("资产更新异常");
                                return false;
                            }

                            //todo 更新匹配成功用户委托数据、成交数据、资产数据、撤单数据


                        }
                        message = Result.success("委托成功");
                    } else if ("xianjia".equals(transaction.getEntrustType())) {
                    } else {
                        message = Result.fail("未知委托方式");
                        return false;
                    }
                } else {
                    message = Result.fail("超过最大可买数量");
                    return false;
                }


            } else if ("QUERY||REVOKE".equals(operate)) {
                String userId = transaction.getUserId();
                List<RevokeList> revokeLists = transactionService.findUserRevoke(userId);
                message = Result.success(revokeLists);
            } else if ("QUERY||ENTRUST".equals(operate)) {
                String userId = transaction.getUserId();
                String entrustToday = DateTransformUtil.transformToDate();
                List<TodayEntrust> entrustList = transactionService.findUserEntrust(userId, entrustToday);
                if (entrustList == null) {
                    message = Result.fail("获取当日委托数据失败");
                } else {
                    message = Result.success(entrustList);
                }
            } else if ("QUERY||FUND".equals(operate)) {
                //todo 获取用户股份资产后，判断是T日还是T+1日
                String userId = transaction.getUserId();
                List<Fund> funds = transactionService.findUserFund(userId);
                if (funds == null) {
                    message = Result.fail("获取用户股份数据失败");
                }
                message = Result.success(funds);

            } else if ("QUERY||TRANSACTION".equals(operate)) {
                String userId = transaction.getUserId();
                List<TodayTransaction> dayTransaction = transactionService.findUserTodayTransaction(userId, DateTransformUtil.transformToDate());
                if (dayTransaction == null) {
                    message = Result.fail("获取用户当天成交数据失败");
                }
                message = Result.success(dayTransaction);

            } else if ("QUERY||HISTORY".equals(operate)) {
                String userId = transaction.getUserId();
                List<TodayTransaction> transaction = transactionService.findUserTransaction(userId);
                if (transaction == null) {
                    message = Result.fail("获取用户历史成交数据失败");
                }
                message = Result.success(transaction);
            } else if ("STOCK||REVOKE".equals(operate)) {
                String organizationId = transaction.getOrganizationId();
                //执行撤单接口
                ArrayList revoke = transaction.getRevoke();
                Market market = transactionService.findMarket(organizationId);
                if (revoke == null) {
                    message = Result.fail("接受撤单数据失败");
                    return false;
                }

                if (revoke.size() == 0) {
                    message = Result.fail("请选择需要撤单的委托");
                    return false;
                }

                Map map = new HashMap();
                String revokeId;
                int revokeResult;
                for (int i = 0; i < revoke.size(); i++) {
                    map = (Map) revoke.get(i);
                    revokeId = (String) map.get("id");
                    //删撤单表数据
                    revokeResult = transactionService.deleteRevokeById(revokeId);
                    //更新资金数据
                    Share share = new Share();
                    share.setUserId((String) map.get("userId"));
                    int  entrustNumber =  (int)map.get("entrustNumber");
                    double entrustPrice =  (double)map.get("entrustPrice");
                    share.setFreezeBalance(-(entrustNumber * entrustPrice * (1 + market.getCommission())));
                    share.setTotalFund(0.0);
                    share.setAvailableBalance(entrustNumber * entrustPrice * (1 + market.getCommission()));
                    share.setFundBalance(entrustNumber * entrustPrice * (1 + market.getCommission()));
                    share.setTotalProfit(0.0);
                    //更新委托数据
                    String stockCode = (String)map.get("stockCode");
                    String date = DateTransformUtil.transformToDate();
                    int deleteTodayEntrust = transactionService.deleteByTodayEntrust(stockCode,date);
                    int updateShare = transactionService.updateShare(share);
                    if(updateShare != 1){
                        message = Result.fail("撤单后更新资产数据失败");
                        return false;
                    }
                    if (revokeResult != 1) {
                        message = Result.fail("撤销委托失败");
                        return false;
                    }
                    if(deleteTodayEntrust != 1){
                        message = Result.fail("撤单后更新当日委托失败");
                        return false;
                    }
                    message = Result.success(revokeResult);
                }
            } else if ("QUERY||ORGANIZETRANSACTION".equals(operate)) {
                String organize = transaction.getOrganizationId();
                //查询组织下所有用户
                List<User> userList = managerService.findAllUser(organize);
                List resultList = new ArrayList();
                //遍历用户根据用户id查询交易记录
                for (User user : userList
                ) {
                    String userId = user.getUserId();
                    List<TodayTransaction> transactionList = transactionService.findUserTransaction(userId);
                    resultList.addAll(transactionList);
                }
                if (resultList.size() != 0) {
                    message = Result.success(resultList);
                } else {
                    message = Result.fail("数据异常");
                }
            } else if ("QUERY||ORGANIZATIONSHARE".equals(operate)) {
                String organize = transaction.getOrganizationId();
                //查询组织下所有用户
                List<User> userList = managerService.findAllUser(organize);
                List<Share> resultList = new ArrayList();
                //遍历用户根据用户id查询各用户资产情况
                for (User user : userList
                ) {
                    String userId = user.getUserId();
                    Share shareList = transactionService.findOneShare(userId);
                    resultList.add(shareList);
                }
                if (resultList.size() != 0) {
                    Map map = new HashMap<>();
                    map.put("users", userList);
                    map.put("share", resultList);
                    message = Result.success(map);
                } else {
                    message = Result.fail("数据异常");
                }
            } else if ("QUERY||FIVE".equals(operate)) {
                TimerUtil t = new TimerUtil();
                String stockCode = transaction.getStockCode();
                String stockName = transaction.getStockName();
                t.sendFiveOrder(stockCode, stockName);
                FiveOrder fiveOrder = transactionService.findFiveOrder(stockCode);
                if (fiveOrder == null) {
                    message = Result.fail("获取五档数据失败");
                    return false;
                }
                message = Result.success(fiveOrder);

            } else if ("QUERY||USESALE".equals(operate)) {
                String userId = transaction.getUserId();
                String stockCode = transaction.getStockCode();
                //查询资产股份表
                Fund fund = transactionService.findFundByIdAndStock(userId, stockCode);
                Integer maxSale;
                if (fund == null) {
                    maxSale = 0;
                    message = Result.success(maxSale);
                    return false;
                }
                maxSale = fund.getUsefulNumber();
                message = Result.success(maxSale.toString());

            }else if("SEND||FUND".equals(operate)){
                String userId = transaction.getUserId();
                Double fund = transaction.getUsefulFund();
                Share share = new Share();
                share.setUserId(userId);
                share.setAvailableBalance(0.0);
                share.setFundBalance(0.0);
                share.setFreezeBalance(0.0);
                share.setTotalFund(fund);
                share.setTotalProfit(0.0);
                int updateShare = transactionService.updateShare(share);
                if (updateShare != 1) {

                }
            } else if ("ALL||TRANSACTION".equals(operate)) {
                List<TodayTransaction> transactionList;
                String userId = transaction.getUserId();
                String organization = transaction.getOrganizationId();
                if (StringUtils.isBlank(userId)) {
                    message = Result.fail("查询参数不能空");
                    return false;
                }else{
                    List<User> users = managerService.findAllUser(organization);
                    String isOrganizeUser = "N";
                    for (User u:users
                         ) {
                        if (u.getUserId().equals(userId)) {
                            isOrganizeUser = "Y";
                        }
                    }
                    if ("N".equals(isOrganizeUser)) {
                        message = Result.fail("该用户不存在当前组织中");
                        return false;
                    }
                    transactionList = transactionService.findUserTransaction(userId);
                }
                message = Result.success(transactionList);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            message = Result.fail("后台处理数据异常");
        }
        return true;
    }


}
