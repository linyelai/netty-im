package com.linseven.userservice.service;

import com.linseven.userservice.model.UserPO;

/**
 * @author Admin
 */
public interface UserService {

    /**
     * 添加用户
     * @param userPO
     */
    void addUser(UserPO userPO);

    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    UserPO findByUsername(String username);

}
