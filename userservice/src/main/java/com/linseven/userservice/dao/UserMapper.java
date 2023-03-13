package com.linseven.userservice.dao;

import com.linseven.userservice.model.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper  {

    UserPO findByUsername(String name);

    /**
     * 添加用户
     * @param userPO
     * @return
     */
    int addUser(UserPO userPO);
}
