package com.atming;

import com.atming.entity.User;
import com.atming.mapper.DataMapper;
import com.atming.mapper.OrganizeMapper;
import com.atming.mapper.UserMapper;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.PasswordSaltUtil;
import com.atming.utils.TimerUtil;
import com.atming.utils.TokenUtil;
import com.auth0.jwt.interfaces.Claim;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.tools.jstat.Token;

import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/4
 * @Time: 19:05
 * @Description
 */
public class LoginTest extends ParentTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testLogin() {
        User user = userMapper.findOne("admin");
        String salt = user.getSalt();
        PasswordSaltUtil passwordSalt = new PasswordSaltUtil(salt, "sha-256");
        boolean valid = passwordSalt.isPasswordValid(user.getPassword(), "231650");
        System.out.println(user);
        System.out.println(valid);
    }


    @Autowired
    private OrganizeMapper organizeMapper;

    @Test
    public void testCreateId() {
        CreateIdUtil.createId("organization");
    }

    @Test
    public void testStringToInt() {
        String str = "0000100";
        System.out.println(Integer.parseInt(str));
    }

    @Test
    public void testCreateToken() {
        String token = TokenUtil.getToken(new User("PRU-00005"));
        System.out.println(token);
        Map<String, Claim> map = TokenUtil.verify(token);
        //签发时间
        Claim iat = map.get("iat");
        //过期时间
        Claim exp = map.get("exp");
        //生效时间
        Claim nbf = map.get("nbf");
        //过期时间 - 生效时间
        Long expTime = exp.asLong();
        Long iatTime = nbf.asLong();
        Long nbfTime = nbf.asLong();
        Date date = new Date();
        Long now = date.getTime();
        if (now < iatTime) {
            System.out.println("token未生效");
        } else {
            if (expTime - now < expTime - nbfTime) {
                System.out.println("token生效中");
            } else {
                System.out.println("token失效");
            }
        }
    }

    @Test
    public void TestFiveOrder() {
        TimerUtil t = new TimerUtil();
        t.sendFiveOrder("600360" ,"nihao");
    }
}
