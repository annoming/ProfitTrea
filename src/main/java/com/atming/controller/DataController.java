package com.atming.controller;

import com.atming.entity.Stock;
import com.atming.entity.User;
import com.atming.service.DataService;
import com.atming.utils.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 3:28 下午
 */
@RequestMapping(value = "profittrea")
@Controller
public class DataController {

    @Autowired
    private DataService dataService;
    private String operate;
    private Stock stockInfo;
    private static Result message;


    @PostMapping(value = "/data/{action}.do")
    @ResponseBody
    public Result getData(@RequestBody Stock stock,@PathVariable String action) {
        operate = action;
        stockInfo = stock;
        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    @GetMapping(value = "/data/{action}.do")
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
        try{
            if ("QUERY||DATA".equals(operate)) {
                String stockType = stockInfo.getStockType();
                List<Stock> list = dataService.findStock(stockType);
                if (list != null) {
                    message = Result.success(list);
                } else {
                    message = Result.fail("数据异常");
                }
            }else if("QUERY||FOUNDATION".equals(operate)){

            }
        }catch (Exception e){
            message = Result.fail("后台处理数据异常");
        }
        return true;
    }
}