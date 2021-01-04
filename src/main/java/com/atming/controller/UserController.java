package com.atming.controller;

import com.atming.entity.User;
import com.atming.service.UserService;
import com.atming.utils.PasswordSaltUtil;
import com.atming.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class UserController {


    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(value = "/login")
    public Result login(@RequestParam(name="username")String userName,@RequestParam(name="password")String password){
        User user = new User(userName,password);
        User loginUser = userService.getLogin(user);
        if(loginUser == null){
            return Result.fail("用户不存在");
        }else{
            String salt = loginUser.getSalt();
            PasswordSaltUtil passwordSalt = new PasswordSaltUtil(salt,"sha-256");
            boolean valid = passwordSalt.isPasswordValid(loginUser.getPassword(), user.getPassword());
            if(valid){
                return Result.success(loginUser.getUserName());
            }else{
                return Result.fail("用户名密码不正确");
            }
        }
    }
}
