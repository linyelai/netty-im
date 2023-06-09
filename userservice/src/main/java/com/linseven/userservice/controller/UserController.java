package com.linseven.userservice.controller;

import com.linseven.userservice.annotation.IgnoreToken;
import com.linseven.userservice.model.UserPO;
import com.linseven.userservice.service.UserService;
import com.linseven.userservice.utils.JWTUtil;
import com.linseven.userservice.utils.RedisUtil;
import com.linseven.userservice.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:14
 */
@RestController
public class UserController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JWTUtil     jwtUtil;

    @Autowired
    private UserService userService;

    private static final String AUTH = "authorization Bear";

    @Autowired
    private ThreadLocal<UserVo> threadLocal;

    @PostMapping("/login")
    public Response login(String username,String password){

        int userId = 1;
        UserVo userVo = new UserVo();
        userVo.setUserId(userId);
        userVo.setUsername(username);
        String token = jwtUtil.Token(userVo);
        Response response = new Response();
        response.setData(token);
        return  response;
    }


    @PostMapping("/signUp")
    @IgnoreToken
    public Response signUp(@RequestBody UserPO userPO){

        userService.addUser(userPO);
        UserPO newUser = userService.findByUsername(userPO.getUsername());
        UserVo userVo = new UserVo();
        userVo.setUserId(newUser.getId());
        userVo.setUsername(newUser.getUsername());
        String token = jwtUtil.Token(userVo);
        Response response = new Response();
        response.setData(token);
        return  response;
    }

    @GetMapping("/refreshToken")
    public  Response refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String token = httpServletRequest.getHeader(AUTH);
        UserVo userVo  = (UserVo) redisUtil.get(token);
        if(userVo==null){
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return  null;
        }
        String newToken = jwtUtil.Token(userVo);
        redisUtil.del(token);
        Response response = new Response();
        response.setData(newToken);
        return  response;
    }

    @GetMapping("/getIMServer")
    public Response getIMServer(){
        Response response = new Response();

        return response;
    }

    @GetMapping("/getUserInfo")
    public Response<UserVo> getUserInfo(){
        Response response = new Response();
        UserVo userVo = threadLocal.get();
        response.setData(userVo);
        return response;
    }
}
