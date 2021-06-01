package com.atming.controller;

import com.atming.annotation.UserLoginToken;
import com.atming.entity.OrganizationManger;
import com.atming.entity.User;
import com.atming.service.ManagerService;
import com.atming.service.OrganizeService;
import com.atming.service.UserService;
import com.atming.utils.DateTransformUtil;
import com.atming.utils.PasswordSaltUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.JWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author annoming
 * @date 2021/3/28 5:10 下午
 */
@Controller
@RequestMapping(value = "profittrea")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private OrganizeService organizeService;
    private String operate;
    private Map<String,Object> data;
    private static Result message;


    @ResponseBody
    @PostMapping(value = "/account/{action}.do")
    @UserLoginToken
    public Result operateAccount(@RequestBody Map<String, Object> info, @PathVariable String action) {

        operate = action;
        data = info;

        if (!getInputData()) {
            return message;
        }

        if (!dealData()) {
            return message;
        }
        return message;
    }


    public boolean getInputData() {
        try {
            if (!StringUtils.isNotBlank(operate) && data == null) {
                message = Result.fail("接收前台数据出错");
                return false;
            }
        } catch (Exception e) {
            message = Result.fail("接受前台数据出错");
            return false;
        }
        return true;
    }

    public boolean dealData() {
        if ("CHANGE||PASS".equals(operate)) {
            String oldPass = (String) data.get("oldPass");
            String newPass = (String) data.get("newPass");
            String checkPass = (String) data.get("checkPass");
            Map<String,Object> userInfo = (Map<String, Object>) data.get("user");
            String  userPass = (String) userInfo.get("password");
            String salt = (String) userInfo.get("salt");
            User user = new User();
            PasswordSaltUtil passwordSaltUtil = new PasswordSaltUtil(salt,"sha-256");
            //判断旧密码是否正确
            if (passwordSaltUtil.isPasswordValid(userPass, oldPass)) {
                if (newPass.equals(checkPass)) {
                    //给密码加盐
                    String savePass = passwordSaltUtil.encode(newPass);
                    user.setUserId((String) userInfo.get("userId"));
                    user.setPassword(savePass);
                    int result = userService.updateUserInfo(user);
                    if(result != 1){
                        message = Result.fail("更新数据失败");
                    }else{
                        message = Result.success(result);
                    }
                }else{
                    message = Result.fail("两次密码输入不一致");
                }
            }else{
                message = Result.fail("旧密码错误");
            }
        } else if ("CHANGE||TIME".equals(operate)) {
            String organize = (String) data.get("organize");
            String startMorning = (String) data.get("startMorning");
            String endMorning = (String) data.get("endMorning");
            String startAfternoon = (String) data.get("startAfternoon");
            String endAfternoon = (String) data.get("endAfternoon");

            Date morningStart = DateTransformUtil.transformStringTime(startMorning);
            Date morningEnd = DateTransformUtil.transformStringTime(endMorning);
            Date afternoonStart = DateTransformUtil.transformStringTime(startAfternoon);
            Date afternoonEnd = DateTransformUtil.transformStringTime(endAfternoon);

            if (!StringUtils.isNotBlank(startMorning)) {
                message = Result.fail("设置上午开市时间异常");
                return false;
            }

            if (!StringUtils.isNotBlank(endMorning)) {
                message = Result.fail("设置上午收市时间异常");
                return false;
            }
            if (!StringUtils.isNotBlank(startAfternoon)) {
                message = Result.fail("设置下午开市时间异常");
                return false;
            }

            if (!StringUtils.isNotBlank(endAfternoon)) {
                message = Result.fail("设置下午收市时间异常");
                return false;
            }

            //判断开始时间比结束时间晚
            if (morningStart.compareTo(morningEnd) > 0) {
                message = Result.fail("设置开收市时间异常");
                return false;
            }

            if(afternoonStart.compareTo(afternoonEnd) > 0){
                message = Result.fail("设置开收市时间异常");
                return false;
            }

            int updateResult = userService.updateMarketTime(organize, startMorning, endMorning, startAfternoon, endAfternoon);
            if (updateResult == 1) {
                message = Result.success(updateResult);
            }else{
                message = Result.fail("更新数据失败");
            }


        } else if ("UPDATE||USERNAME".equals(operate)) {
            String userName = (String) data.get("userName");
            String userId = (String) data.get("userId");
            if (StringUtils.isBlank(userName)) {
                message = Result.fail("用户名不能为空");
                return false;
            }

            if (StringUtils.isBlank(userId)) {
                message = Result.fail("获取用户id异常");
                return false;
            }
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            int updateUserName = userService.updateUserNameById(user);
            if (updateUserName != 1) {
                message = Result.fail("更新用户名异常");
            }
            message = Result.success(updateUserName);
        } else if ("UPDATE||EMAIL".equals(operate)) {
            String email = (String) data.get("email");
            String userId = (String) data.get("userId");
            if (StringUtils.isBlank(email)) {
                message = Result.fail("邮箱不能为空");
                return false;
            }

            if (StringUtils.isBlank(userId)) {
                message = Result.fail("获取用户id异常");
                return false;
            }

            User user = new User();
            user.setUserId(userId);
            user.setEmail(email);
            int updateEmail = userService.updateEmailById(user);
            if (updateEmail != 1) {
                message = Result.fail("更新邮箱异常");
            }
            message = Result.success(updateEmail);
        } else if ("QUERY||INFO".equals(operate)) {
            String token = (String) data.get("token");
            if (token != null) {
                String userId = JWT.decode(token).getAudience().get(0);
                User user = managerService.getUserById(userId);
                OrganizationManger organization = organizeService.findOrganizeById(user.getOrganization());
                user.setOrganization(organization.getOrganizeName());
                message = Result.success(user);
            } else {
                message = Result.refuse("token失效,请重新登录");
            }
        }
        return true;
    }

}
