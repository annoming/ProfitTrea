package com.atming.controller;

import com.atming.annotation.UserLoginToken;
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
import sun.util.resources.cldr.ml.CalendarData_ml_IN;

import java.util.*;

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
    @UserLoginToken
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
    @UserLoginToken
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
            newUserId = managerService.deleteManagerById(manager.getUserId());
            if (newUserId == 1) {
                message = Result.success(newUserId);
            } else {
                message = Result.fail("删除失败");
            }
        } else if ("QUERY||MANAGER".equals(operate)) {
            String organization = manager.getOrganization();
            List<User> list;
            List<User> mana = null;
            //角色为超级管理员
            if (manager.getRoleType() == 100) {
                list = managerService.findAll();
            } else {
                list = managerService.findAll(organization);
            }
            mana = new ArrayList<>();
            for (User u:list
            ) {
                if(u.getRoleType() != 104){
                    mana.add(u);
                }
            }
            List<User> managers = new ArrayList<>();
            for (int i = 0; i < mana.size(); i++) {
                if (!"PRO-00001".equals(mana.get(i).getOrganization())) {
                    managers.add(mana.get(i));
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
            if (token != null) {
                String userId = JWT.decode(token).getAudience().get(0);
                User user = managerService.getUserById(userId);
                message = Result.success(user);
            } else {
                message = Result.refuse("token失效,请重新登录");
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
            List<User> list;
            List<User> users = new ArrayList<>();
            //超级管理员查询所有用户
            if ("PRO-00001".equals(organization)) {
                list = managerService.findAll();
                for (User u :list
                     ) {
                    if(u.getRoleType() == 104){
                        users.add(u);
                    }
                }
            }else{
                list = managerService.findAllUser(organization);
                for (User u:list
                     ) {
                    String organizeId = u.getOrganization();
                    OrganizationManger manger = organizeService.findOrganizeById(organizeId);
                    if (manger != null) {
                        u.setOrganization(manger.getOrganizeName());
                        users.add(u);
                    }
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
            String userId = manager.getUserId();
            String realName = manager.getRealName();
            String email = manager.getEmail();
            String studentNumber = manager.getStudentNumber();
            String major = manager.getMajor();
            String grade = manager.getGrade();

            if (StringUtils.isBlank(userId)) {
                message = Result.fail("用户id获取失败");
                return false;
            }

            if (StringUtils.isBlank(realName)) {
                message = Result.fail("姓名不能为空");
                return false;
            }

            if (StringUtils.isBlank(email)) {
                message = Result.fail("邮箱不能为空");
                return false;
            }

            if (StringUtils.isBlank(studentNumber)) {
                message = Result.fail("学号不能为空");
                return false;
            }

            if (StringUtils.isBlank(major)) {
                message = Result.fail("专业不能为空");
                return false;
            }

            int updateUser = managerService.updateUserById(userId, realName, email, studentNumber, major, grade, new Date());
            if(updateUser == 1){
                message = Result.success(updateUser);
            }else{
                message = Result.fail("更新用户信息失败");
                return false;
            }
        } else if ("MATCH||MANAGERNAME".equals(operate)) {
            //根据组织名名模糊查询管理员信息
            String organization = manager.getOrganization();
            String userName = manager.getUserName();
            int roleType = manager.getRoleType();
            List<User> list;
            List<User> users = new ArrayList<>();
            List<User> manage = new ArrayList<>();
            //超级管理员用户查询所有管理员信息
            if ("PRO-00001".equals(organization)) {
                list = managerService.findAdminAsName(userName);
            }else{
                //非超级管理员，查询当前组织下管理员
                list = managerService.findManagerAsName(organization, userName);
            }

            if (list == null) {
                message = Result.fail("查询管理员信息异常");
                return false;
            }

            for (User u:list
            ) {
                if (104 == u.getRoleType()) {
                    users.add(u);
                }else{
                    manage.add(u);
                }
            }
            if (roleType == 101) {
                users = manage;
            }

            List managers = new ArrayList();
            for (User u : users
            ) {
                if (!"admin".equals(u.getUserName())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("value", u.getUserName());
                    managers.add(map);
                }
            }
            message = Result.success(managers);

        } else if ("QUERY||MANAGERONE".equals(operate)) {
            String organization = manager.getOrganization();
            String userName = manager.getUserName();
            Date startTime = manager.getCreateTime();
            Date endTime = manager.getUpdateTime();
            List<User> list;
            if (StringUtils.isBlank(userName)) {
                if (startTime == null || endTime == null) {
                    message = Result.fail("搜索字段不能为空");
                    return false;
                }else{
                    //按照日期搜索
                    if ("PRO-00001".equals(organization)) {
                        list = managerService.findAllDate(startTime, endTime);
                    }else{
                        list = managerService.findByDate(organization,startTime, endTime);
                    }
                }
            }else{
                if (startTime == null || endTime == null) {
                    //按照用户名搜索
                    list = managerService.findOneByUserName(userName);
                }else{
                    //按照日期和用户名搜索
                    list = managerService.findByNameAndDate(userName, startTime, endTime);
                }
            }
            if (list == null) {
                message = Result.fail("获取信息异常");
                return false;
            }else{
                message = Result.success(list);
            }
        } else if ("UPDATE||MANAGER".equals(operate)) {
            String realName = manager.getRealName();
            String email = manager.getEmail();
            String userId = manager.getUserId();

            if (StringUtils.isBlank(userId)) {
                message = Result.fail("前台入参异常");
            }
            if (StringUtils.isBlank(realName)) {
                message = Result.fail("姓名不能为空");
                return false;
            }

            if (StringUtils.isBlank(email)) {
                message = Result.fail("邮箱不能为空");
                return false;
            }

            int managerUpdate = managerService.updateInfoById(userId, realName, email, new Date());
            if(managerUpdate == 1){
                message = Result.success(managerUpdate);
            }else{
                message = Result.fail("更新管理员信息失败");
                return false;
            }
        }
        return true;
    }
}
