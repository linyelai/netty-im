package com.linseven.userservice.controller;

import com.linseven.userservice.utils.JWTUtil;
import com.linseven.userservice.utils.RedisUtil;
import com.linseven.userservice.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private static final String AUTH = "authorization Bear";

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
}