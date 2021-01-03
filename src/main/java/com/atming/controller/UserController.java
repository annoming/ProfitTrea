package com.atming.controller;

import com.atming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}
