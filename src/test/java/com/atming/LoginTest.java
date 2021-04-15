package com.atming;

import com.atming.entity.User;
import com.atming.mapper.OrganizeMapper;
import com.atming.mapper.UserMapper;
import com.atming.utils.CreateIdUtil;
import com.atming.utils.PasswordSaltUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/4
 * @Time: 19:05
 * @Description
 */
public class LoginTest extends ParentTest{

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testLogin(){
        User user = userMapper.findOne("admin");
        String salt = user.getSalt();
        PasswordSaltUtil passwordSalt = new PasswordSaltUtil(salt,"sha-256");
        boolean valid = passwordSalt.isPasswordValid(user.getPassword(), "231650");
        System.out.println(user);
        System.out.println(valid);
    }


    @Autowired
    private OrganizeMapper organizeMapper;
    @Test
    public void testCreateId(){
        CreateIdUtil.createId("organization");
    }

    @Test
    public void testStringToInt() {
        String str = "0000100";
        System.out.println(Integer.parseInt(str));
    }
}
