package com.linseven.userservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.linseven.userservice.controller.Response;
import com.linseven.userservice.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:15
 */
@Slf4j
@Component
public class JWTUtil {

    private static final String SECRET = "!Q@W#E$R^Y&U";
    //token签发者
    private static final String ISSUSRE = "HZSTYGZPT";
    //token过期时间
    public static final Long EXPIRE_DATE = 1000 * 60L;
    @Autowired
    private   RedisUtil redisUtil;

    /**
     * 生成token
     *
     * @param userVo
     * @return
     */
    public  String Token(UserVo userVo) {
        //当前时间
        Date now = new Date();
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期
        //1. header
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withIssuer(ISSUSRE)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
                .withClaim("username", userVo.getUsername())
                .withClaim("userId", userVo.getUserId())
                .sign(algorithm);
        redisUtil.set("token:"+token,userVo,EXPIRE_DATE);
        return token;
    }

    /**
     * 生成token
     *
     * @param map
     * @return
     */
    public  String createToken(Map<String, String> map) {
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期

        //创建builder对象
        JWTCreator.Builder builder = JWT.create();
        //遍历map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SECRET));
        return token;
    }



    /**
     * 验证token
     * 验证过程中如果有异常，则抛出；
     * 如果没有,则返回 DecodedJWT 对象来获取用户信息;
     *
     * @param token
     */
    public  DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

}