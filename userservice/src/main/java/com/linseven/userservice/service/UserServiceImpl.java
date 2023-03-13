package com.linseven.userservice.service;

import com.linseven.userservice.dao.UserMapper;
import com.linseven.userservice.exception.BusinessException;
import com.linseven.userservice.model.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/13 10:21
 */
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void addUser(UserPO userPO)throws BusinessException {

        String username = userPO.getUsername();
        UserPO result = userMapper.findByUsername(username  );
        if(result!=null){
            throw new BusinessException("用户已存在！");
        }
        String password = passwordEncoder.encode(userPO.getPassword());

        userPO.setPassword(password);
        int flag = userMapper.addUser(userPO);
        if(flag<=0){
            throw new BusinessException("添加用户失败！");
        }


    }

    @Override
    public UserPO findByUsername(String username) {

        return userMapper.findByUsername(username);
    }
}
