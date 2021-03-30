package com.atming.controller;

import com.atming.entity.OrganizationManger;
import com.atming.service.OrganizeService;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author annoming
 * @date 2021/3/21 7:32 上午
 * todo 过滤去过滤未登录的请求
 */
@Controller
@RequestMapping(value = "/profittrea")
public class OrganizeController {

    @Autowired
    private OrganizeService organizeService;
    private String operate;
    private OrganizationManger organization;
    private static Result message;

    @ResponseBody
    @PostMapping(value = "/organize/{action}.do")
    public Result organizeManager(@RequestBody OrganizationManger organize, @PathVariable String action){
        operate = action;
        organization = organize;

        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    @ResponseBody
    @GetMapping(value = "/organize/{action}.do")
    public Result getOrganizeId(@PathVariable String action){
        operate = action;
        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    private boolean getInputData(){
        try {

        } catch (Exception e) {
            message = Result.fail("接受前台数据出错");
            return false;
        }
        return true;
    }

    private boolean dealData(){
        int newOrganizeId;
        if ("ADD||ORGANIZATION".equals(operate)) {
            //判断邮箱是否已经注册
            OrganizationManger organize = organizeService.isExists(organization);
            if (organize == null) {
                organization.setStatus(100);
                organization.setCreateTime(new Date());
                organization.setUpdateTime(new Date());
                newOrganizeId = organizeService.addOneOrganize(organization);
                if (newOrganizeId == 1) {
                    message = Result.success(newOrganizeId);
                    //todo 设置定时发邮件任务
                }else{
                    message = Result.fail("添加失败");
                }
            } else{
                message = Result.fail("邮箱已被使用");
            }
        }else if("DELETE||ORGANIZATION".equals(operate)){
            //todo 判断是否有权限
            newOrganizeId = organizeService.deleteById(organization.getOrganizationId());
            if(newOrganizeId == 1){
                message = Result.success(newOrganizeId);
            }else{
                message = Result.fail("删除失败");
            }
        } else if ("UPDATE||ORGANIZATION".equals(operate)) {

        } else if ("QUERY||ORGANIZATION".equals(operate)) {
            List<OrganizationManger> list = organizeService.getAll();
            message = Result.success(list);
        } else if ("ID||ORGANIZATION".equals(operate)) {
            String idName = operate.substring(4).toLowerCase();
            String newId = CreateIdUtil.createId(idName);
            message = Result.success(newId);
        }
        return true;
    }
}
