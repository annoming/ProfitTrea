package com.atming.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.atming.annotation.PassToken;
import com.atming.annotation.UserLoginToken;
import com.atming.entity.User;
import com.atming.service.UserService;
import com.atming.utils.TokenUtil;
import com.atming.utils.result.Result;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author annoming
 * @date 2021/4/6 2:33 下午
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    private static Result message;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");

        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                if (token == null) {
                    message = Result.error("无token，请重新登录");
                    sendResponse(httpServletResponse,message);
                }
                String userName;
                try {
                    // 获取 token 中的 user id
                    userName = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    message = Result.error("解析token出错");
                    sendResponse(httpServletResponse,message);
                    return false;
                }
                User loginUser = new User(userName);
                User user = userService.selectUserById(loginUser.getUserName());
                if (user == null) {
                    message = Result.error("用户不存在，请重新登录");
                    sendResponse(httpServletResponse,message);
                    return false;
                }
                try{
                    // 验证 token
                    TokenUtil.verify(token);
                }catch (Exception e){
                    if (e.getMessage().startsWith("The Token can't be used before")) {
                        message = Result.warning("token未生效");
                        sendResponse(httpServletResponse,message);
                        return false;

                    }
                    if (e.getMessage().startsWith("The Token has expired on")) {
                        message = Result.refuse("token已过期，请重新登录");
                        sendResponse(httpServletResponse,message);
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    private void sendResponse(HttpServletResponse response, Result message) {
        try {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
//            writer.print(message);
            writer.print(JSONObject.toJSONString(message, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat));
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
