package com.atming.controller;

import com.atming.entity.OrganizationManger;
import com.atming.entity.Permission;
import com.atming.entity.Role;
import com.atming.entity.User;
import com.atming.service.*;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.EmailUtil;
import com.atming.utils.PasswordSaltUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.JWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author annoming
 * @date 2021/3/28 5:14 下午
 */

@RequestMapping(value = "/profittrea")
@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private OrganizeService organizeService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    private String operate;
    private User manager;
    private static Result message;

    @ResponseBody
    @PostMapping(value = "/user/{action}.do")
    public Result managerUser(@RequestBody User user, @PathVariable String action) {
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
    public Result userManager(@PathVariable String action) {
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
            if (!StringUtils.isNotBlank(operate) && manager == null) {
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
        int newUserId;
        if ("ADD||MANAGER".equals(operate)) {
            //判断邮箱是否已经注册
            User oldUser = managerService.findOneByEmail(manager);
            if (oldUser == null) {
                String salt = UUID.randomUUID().toString();
                PasswordSaltUtil passUtil = new PasswordSaltUtil(salt, "sha-256");
                String randomPass = passUtil.createRandomPass();
                String password = passUtil.encode(randomPass);
                manager.setPassword(password);
                manager.setSalt(salt);
                manager.setCreateTime(new Date());
                manager.setUpdateTime(new Date());
                newUserId = managerService.insertUser(manager);
                if (newUserId == 1) {
                    message = Result.success(newUserId);
                    emailUtil.run(manager, randomPass);
                } else {
                    message = Result.fail("添加失败");
                }
            } else {

                message = Result.fail("邮箱已被使用");
            }
        } else if ("DELETE||MANAGER".equals(operate)) {
            //todo 判断是否有权限
            newUserId = managerService.deleteManagerById(manager.getUserId());
            if (newUserId == 1) {
                message = Result.success(newUserId);
            } else {
                message = Result.fail("删除失败");
            }
        } else if ("UPDATE||USER".equals(operate)) {


        } else if ("QUERY||MANAGER".equals(operate)) {
            String organization = manager.getOrganization();
            List<User> list;
            if(manager.getRoleType() == 100){
                list = managerService.findAll();
            }else{
                list = managerService.findAll(organization);
            }
            List<User> managers = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (!"PRO-00001".equals(list.get(i).getOrganization())) {
                    managers.add(list.get(i));
                }
            }
            List<User> users = new ArrayList<>();
            for (int i = 0; i < managers.size(); i++) {
                String organizeId = managers.get(i).getOrganization();
                OrganizationManger manger = organizeService.findOrganizeById(organizeId);
                if (manger != null) {
                    managers.get(i).setOrganization(manger.getOrganizeName());
                    users.add(managers.get(i));
                }
            }
            message = Result.success(users);
        } else if ("ID||USER".equals(operate)) {
            String idName = operate.substring(4).toLowerCase();
            String newId = CreateIdUtil.createId(idName);
            message = Result.success(newId);
        } else if ("QUERY||ORGANIZATION".equals(operate)) {
            String token = manager.getUserName();
            if(token != null){
                String userId = JWT.decode(token).getAudience().get(0);
                User user = managerService.getUserById(userId);
                message = Result.success(user);
            }else{
                message = Result.fail("token失效请重新登录");
            }

        } else if ("QUERY||PERMISSION".equals(operate)) {
            String token = manager.getUserName();
            String userId = JWT.decode(token).getAudience().get(0);
            User user = managerService.getUserById(userId);
            Role role = roleService.findById(user.getRoleType());
            Permission permission = permissionService.selectOneByPermissionId(role.getPermissionType());
            message = Result.success(permission);
        } else if ("QUERY||USER".equals(operate)) {
            String organization = manager.getOrganization();
            List<User> list = managerService.findAllUser(organization);
            List<User> users = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String organizeId = list.get(i).getOrganization();
                OrganizationManger manger = organizeService.findOrganizeById(organizeId);
                if (manger != null) {
                    list.get(i).setOrganization(manger.getOrganizeName());
                    users.add(list.get(i));
                }
            }
            message = Result.success(users);
        } else if ("ADD||USER".equals(operate)) {
            //判断邮箱是否已经注册
            String organization = manager.getOrganization();
            List<User> users = managerService.findAllUser(organization);
            int flag = 0;
            for (User user : users) {
                if (user.getEmail().equals(manager.getEmail())) {
                    flag = 1;
                    break;
                }
            }
            if (flag != 1) {
                //生成密码盐
                String salt = UUID.randomUUID().toString();
                PasswordSaltUtil passUtil = new PasswordSaltUtil(salt, "sha-256");
                //生成随机密码
                String randomPass = passUtil.createRandomPass();
                //使用密码盐给密码加密
                String password = passUtil.encode(randomPass);
                manager.setPassword(password);
                manager.setSalt(salt);
                manager.setCreateTime(new Date());
                manager.setUpdateTime(new Date());
                newUserId = managerService.insertUser(manager);
                if (newUserId == 1) {
                    //TODO 初始化用户资产额度

                    message = Result.success(newUserId);
                    emailUtil.run(manager, randomPass);
                } else {
                    message = Result.fail("添加失败");
                }
            } else {

                message = Result.fail("邮箱已被使用");
            }
        } else if ("DELETE||USER".equals(operate)) {

        } else if ("UPDATE||USER".equals(operate)) {

        }
        return true;
    }
}
