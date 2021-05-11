package com.atming.controller;

import com.atming.annotation.UserLoginToken;
import com.atming.entity.Announce;
import com.atming.entity.News;
import com.atming.entity.Stock;
import com.atming.service.DataService;
import com.atming.service.QuotationService;
import com.atming.utils.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 5:20 下午
 */

@Controller
@RequestMapping(value = "profittrea")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;
    private String operate;
//    private News newList;
    private static Result message;


    @GetMapping(value = "/quotation/{action}.do")
    @ResponseBody
    @UserLoginToken
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
            if ("QUERY||NEWS".equals(operate)) {
                List<News> news = quotationService.selectAllNews();
                if (news != null) {
                    message = Result.success(news);
                } else {
                    message = Result.fail("数据异常");
                }
            } else if ("QUERY||ANNOUNCE".equals(operate)) {
                List<Announce> announces = quotationService.selectAllAnnounce();
                if (announces != null) {
                    message = Result.success(announces);
                } else {
                    message = Result.fail("数据异常");
                }
            }
        }catch (Exception e){
            message = Result.fail("后台处理数据异常");
        }
        return true;
    }
}
