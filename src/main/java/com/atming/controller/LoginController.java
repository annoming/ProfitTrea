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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private Result message;

    @ResponseBody
    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        User loginUser = userService.getLogin(user);
        if(loginUser == null){
            return Result.fail("用户不存在");
        }else{
            int roleType = loginUser.getRoleType();
            //获取用户权限
            Role role = roleService.findById(roleType);
            Permission permission = permissionService.selectOneByPermissionId(role.getPermissionType());
            String salt = loginUser.getSalt();
            PasswordSaltUtil passwordSalt = new PasswordSaltUtil(salt,"sha-256");
            boolean valid = passwordSalt.isPasswordValid(loginUser.getPassword(), user.getPassword());
            if(valid){
                String token = TokenUtil.getToken(loginUser);
                Map map = new HashMap<>();
                map.put("token", token);
                map.put("username", loginUser.getUserName());
                map.put("permission", permission.getPermissionId());
                map.put("url", permission.getPermissionUrl());
                return Result.success(map);
            }else{
                return Result.fail("用户名密码不正确");
            }
        }
    }

    @PostMapping(value = "/logout.do")
    @ResponseBody
    public Result logout(@RequestBody User user){
        String token = user.getUserName();
        if (token != null) {
            message =  Result.success("token未失效,请删除token");
        }else{
            message = Result.fail("token已经失效");
        }
        return  message;
    }
}
