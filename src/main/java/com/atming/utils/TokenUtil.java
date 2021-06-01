package com.atming.utils;

import com.atming.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author annoming
 * @date 2021/4/6 2:28 下午
 */

public class TokenUtil {

    //token加解密秘钥
    private static String SECRET = "ANNOMING";


    public static String getToken(User user) {
        String token;
        Calendar nowTime = Calendar.getInstance();
        Calendar nowTime2 = Calendar.getInstance();
        //指定token生效时间:当前时间0s后
        nowTime2.add(Calendar.SECOND, 0);
        Date effectTime = nowTime2.getTime();
        //指定token过期时间:当前时间1min后
        nowTime.add(Calendar.MINUTE, 20);
        Date maturityTime = nowTime.getTime();
        token= JWT.create().withAudience(user.getUserId())
                //设置签发时间
                .withIssuedAt(new Date())
                //设置过期时间
                .withExpiresAt(maturityTime)
                //定义token生效时间，在此之前都无法使用
                .withNotBefore(effectTime)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static Map<String, Claim> verify(String token) {
        if (token != null) {
            return verifyToken(token);
        }else{
            return null;
        }
    }

    private static Map<String, Claim> verifyToken(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }
}
