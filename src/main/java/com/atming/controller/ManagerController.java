package com.atming.controller;

import com.atming.entity.OrganizationManger;
import com.atming.entity.User;
import com.atming.service.ManagerService;
import com.atming.service.OrganizeService;
import com.atming.service.UserService;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author annoming
 * @date 2021/3/28 5:14 下午
 */

@RequestMapping(value = "/profittrea")
@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    private String operate;
    private User manager;
    private static Result message;

    @ResponseBody
    @PostMapping(value = "/user/{action}.do")
    public Result managerUser(@RequestBody User user, @PathVariable String action){
        operate = action;
        manager = user;

        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    @ResponseBody
    @GetMapping(value = "/user/{action}.do")
    public Result userManager(@PathVariable String action){
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
        int newUserId;
        if ("ADD||USER".equals(operate)) {
            //判断邮箱是否已经注册
            User oldUser = managerService.findOneByEmail(manager);
            if (oldUser == null) {
                manager.setPassword(manager.getUserId());
                manager.setSalt(manager.getUserId());
                manager.setOrganization(manager.getUserId());
                manager.setCreateTime(new Date());
                manager.setUpdateTime(new Date());
                newUserId = managerService.insertUser(manager);
                if (newUserId == 1) {
                    message = Result.success(newUserId);
                    //todo 设置定时发邮件任务
                }else{
                    message = Result.fail("添加失败");
                }
            } else{
                message = Result.fail("邮箱已被使用");
            }
        }else if("DELETE||USER".equals(operate)){
            //todo 判断是否有权限
            newUserId = managerService.deleteManagerById(manager.getUserId());
            if(newUserId == 1){
                message = Result.success(newUserId);
            }else{
                message = Result.fail("删除失败");
            }
        } else if ("UPDATE||USER".equals(operate)) {

        } else if ("QUERY||USER".equals(operate)) {
            List<User> list = managerService.findAll();
            message = Result.success(list);
        } else if ("ID||USER".equals(operate)) {
            String idName = operate.substring(4).toLowerCase();
            String newId = CreateIdUtil.createId(idName);
            message = Result.success(newId);
        }
        return true;
    }
}
