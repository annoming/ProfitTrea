package com.atming.controller;

import com.atming.entity.Permission;
import com.atming.entity.Role;
import com.atming.entity.User;
import com.atming.service.PermissionService;
import com.atming.service.RoleService;
import com.atming.service.UserService;
import com.atming.utils.PasswordSaltUtil;
import com.atming.utils.TokenUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 23:49
 * @Description
 */
@Controller
@RequestMapping(value = "/profittrea")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    private String operate;
    private User loginer;
    private Result message;


    @ResponseBody
    @PostMapping(value = "/login/{action}.do")
    public Result login(@RequestBody User user,@PathVariable String action){
        loginer = user;
        operate = action;
        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }

    @PostMapping(value = "/logout/{action}.do")
    @ResponseBody
    public Result logout(@RequestBody User user,@PathVariable String action){
        loginer = user;
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
            if (!StringUtils.isNotBlank(loginer.getUserName()) && !StringUtils.isNotBlank(operate)) {
                message = Result.fail("接收前台数据出错");
                return false;
            }
        } catch (Exception e) {
            message = Result.fail("处理前台数据出错");
            return false;
        }
        return true;
    }

    private boolean dealData() {
        String token = loginer.getUserName();
        if ("SIGNOUT".equals(operate)) {
            message = Result.exit("退出");
        } else if ("VERIFY".equals(operate)) {
            try{
                TokenUtil.verify(token);
            }catch (Exception e){
                if (e.getMessage().startsWith("The Token can't be used before")) {
                    message = Result.error("token未生效");
                    return false;
                }
                if (e.getMessage().startsWith("The Token has expired on")) {
                    message = Result.refuse("token已过期，请重新登录");
                    return false;
                }
            }
            message = Result.success(token);
        } else if("LOGIN".equals(operate)){

            if (StringUtils.isBlank(loginer.getUserName())) {
                message  = Result.fail("用户名不为空");
                return false;
            }
            if (StringUtils.isBlank(loginer.getPassword())) {
                message  = Result.fail("密码不为空");
                return false;
            }

            User loginUser = userService.getLogin(loginer);
            if(loginUser == null){
                message  = Result.fail("用户不存在");
                return false;
            }else{
                int roleType = loginUser.getRoleType();
                //获取用户权限
                Role role = roleService.findById(roleType);
                Permission permission = permissionService.selectOneByPermissionId(role.getPermissionType());
                String salt = loginUser.getSalt();
                PasswordSaltUtil passwordSalt = new PasswordSaltUtil(salt,"sha-256");
                boolean valid = passwordSalt.isPasswordValid(loginUser.getPassword(), loginer.getPassword());
                if(valid){
                    token = TokenUtil.getToken(loginUser);
                    Map verify = new HashMap<>();
                    verify.put("token", token);
                    verify.put("username", loginUser.getUserName());
                    verify.put("permission", permission.getPermissionId());
                    verify.put("url", permission.getPermissionUrl());
                    message =  Result.success(verify);
                }else{
                    message = Result.fail("用户名密码不正确");
                    return false;
                }
            }
        } else if ("ROLE".equals(operate)) {
            int roleType = loginer.getRoleType();
            Role role = roleService.findById(roleType);
            if (role == null) {
                message = Result.fail("获取用户角色异常");
                return false;
            }
            Permission permission = permissionService.selectOneByPermissionId(role.getPermissionType());
            if (permission == null) {
                message = Result.fail("获取用户角色权限异常");
                return false;
            }

            message = Result.success(permission.getPermissionId());
        }

        return true;
    }
}
