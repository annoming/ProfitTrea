package com.atming.mapper;

import com.atming.entity.User;

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

    User findById(String userId);

    int updateUserByUserId(User user);

    int updateUserName(User user);

    int updateEmail(User user);


    int updateMarketByOrganize(String organizeId, String startMorning, String endMorning, String startAfternoon, String endAfternoon);
}
