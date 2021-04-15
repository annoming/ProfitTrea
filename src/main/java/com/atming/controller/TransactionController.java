package com.atming.controller;

import com.atming.entity.*;
import com.atming.service.ManagerService;
import com.atming.service.TransactionService;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.DateTransformUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.JWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
                //判断委托方式
                if ("shijia".equals(transaction.getEntrustType())) {
                    //判断是否超过可用资金
                    if (transaction.getBuyPrice() * transaction.getBuyNumber() > transaction.getUsefulFund()) {
                        message = Result.fail("资金不足");
                    } else {
                        //判断买入数量是否为100整数倍
                        if (transaction.getBuyNumber() > 0 && transaction.getBuyNumber() % 100 == 0) {
                            //判断买入数量是否超过最大可买数量
                            if (transaction.getBuyNumber() < transaction.getMaxBuyNumber()) {

                                Date now = new Date();
                                String organizedId = transaction.getOrganizationId();
                                //获取所在市场信息
                                Market market = transactionService.findMarket(organizedId);
                                //判断是否在可买卖时间内，否则进入撤单页
                                Date morningStart = DateTransformUtil.transformNowDate(market.getMorningStart());
                                Date morningEnd = DateTransformUtil.transformNowDate(market.getMorningEnd());
                                Date afternoonStart = DateTransformUtil.transformNowDate(market.getAfternoonStart());
                                Date afternoonEnd = DateTransformUtil.transformNowDate(market.getAfternoonEnd());

                                if ((now.compareTo(morningStart) > 0
                                        && now.compareTo(morningEnd) < 0) || (
                                        now.compareTo(afternoonStart) > 0
                                                && now.compareTo(afternoonEnd) < 0
                                )
                                ) {
                                    //插入数据到当日委托中
                                    TodayEntrust todayEntrust = new TodayEntrust();
                                    todayEntrust.setEntrustId(CreateIdUtil.createRandomId(9));
                                    todayEntrust.setUserId(transaction.getUserId());
                                    todayEntrust.setEntrustNumber(transaction.getBuyNumber());
                                    todayEntrust.setEntrustPrice(transaction.getBuyPrice());
                                    todayEntrust.setTransactionPrice(transaction.getBuyPrice());
                                    todayEntrust.setTransactionNumber(transaction.getBuyNumber());
                                    todayEntrust.setEntrustDate(DateTransformUtil.transformToDate());
                                    todayEntrust.setEntrustTime(DateTransformUtil.transformToTime());
                                    todayEntrust.setEntrustType("市价");
                                    todayEntrust.setDescription("未成交");
                                    todayEntrust.setOperation(transaction.getOperation());
                                    todayEntrust.setStockCode(transaction.getStockCode());
                                    todayEntrust.setStockName("");

                                    int insertEntrust = transactionService.insertEntrust(todayEntrust);
                                    if (insertEntrust != 1) {
                                        message = Result.fail("插入失败");
                                    }
                                    //委托时间为开市时间 更新当日成交、资产股份、更新当日委托状态
                                    //当日成交
                                    TodayTransaction nowTransaction = new TodayTransaction();
                                    nowTransaction.setTransactionId(todayEntrust.getEntrustId());
                                    nowTransaction.setUserId(todayEntrust.getUserId());
                                    nowTransaction.setTransactionNumber(todayEntrust.getEntrustNumber());
                                    nowTransaction.setTransactionPrice(todayEntrust.getEntrustPrice());
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

                                    //资产股份
                                    Fund stockFund = new Fund();
                                    stockFund.setUserId(todayEntrust.getUserId());
                                    stockFund.setStockCode(todayEntrust.getStockCode());
                                    stockFund.setStockName(todayEntrust.getStockName());
                                    stockFund.setStockNumber(todayEntrust.getEntrustNumber());
                                    stockFund.setUsefulNumber(todayEntrust.getEntrustNumber());
                                    stockFund.setFreezeNumber(0);
                                    stockFund.setCostPrice(todayEntrust.getEntrustPrice());
                                    // todo 市价为现价
                                    stockFund.setMarketPrice(todayEntrust.getTransactionPrice());
                                    stockFund.setMarketValue(stockFund.getMarketPrice() * stockFund.getStockNumber());
                                    // todo 浮动盈亏
                                    stockFund.setFloatProfit((stockFund.getMarketPrice() - stockFund.getCostPrice()) * 100);
                                    Double profitRatio = (stockFund.getMarketPrice() - stockFund.getCostPrice()) / stockFund.getMarketPrice() * 100;
                                    String ratio = profitRatio + "%";
                                    stockFund.setProfitRatio(ratio);

                                    int fundCode = transactionService.insertStockFund(stockFund);
                                    if (fundCode != 1) {
                                        message = Result.fail("插入失败");
                                    }

                                } else {
                                    //插入数据到当日委托中
                                    TodayEntrust todayEntrust = new TodayEntrust();
                                    todayEntrust.setEntrustId(CreateIdUtil.createRandomId(9));
                                    todayEntrust.setUserId(transaction.getUserId());
                                    todayEntrust.setEntrustNumber(transaction.getBuyNumber());
                                    todayEntrust.setEntrustPrice(transaction.getBuyPrice());
                                    todayEntrust.setTransactionPrice(0.0000);
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
                                        message = Result.fail("插入失败");
                                    }

                                    RevokeList revoke = new RevokeList();
                                    revoke.setId(todayEntrust.getEntrustId());
                                    revoke.setUserId(transaction.getUserId());
                                    revoke.setStockCode(transaction.getStockCode());
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
                                    //资产变动、冻结金额
                                    System.out.println(revoke);

                                }
                                message = Result.success("买入成功");
                            } else {
                                message = Result.fail("超过最大可买数量");
                            }
                        } else {
                            message = Result.fail("买入数量必须是100的整数倍");
                        }
                    }
                } else if ("xianjia".equals(transaction.getEntrustType())) {

                } else {
                    message = Result.fail("未知委托方式");
                }
            } else if ("STOCK||SALE".equals(operate)) {
                //卖出接口

            } else if ("QUERY||REVOKE".equals(operate)) {
                String userId = transaction.getUserId();
                List<RevokeList> revokeLists = transactionService.findUserRevoke(userId);
                message = Result.success(revokeLists);
            } else if ("QUERY||ENTRUST".equals(operate)) {
                String userId = transaction.getUserId();
                List<TodayEntrust> entrustList = transactionService.findUserEntrust(userId);
                if (entrustList == null) {
                    message = Result.fail("获取当日委托数据失败");
                } else {
                    message = Result.success(entrustList);
                }
            } else if ("QUERY||FUND".equals(operate)) {
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
                //执行撤单接口
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
            }
        } catch (Exception e) {
            message = Result.fail("后台处理数据异常");
        }
        return true;
    }


}
