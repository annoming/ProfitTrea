package com.atming.mapper;

import com.atming.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 23:42
 * @Description
 */
public interface UserMapper {
    User findOne(String userName);
}
