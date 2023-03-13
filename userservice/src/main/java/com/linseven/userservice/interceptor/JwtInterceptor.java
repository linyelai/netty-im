package com.linseven.userservice.interceptor;

import com.alibaba.fastjson.JSON;
import com.linseven.userservice.annotation.IgnoreToken;
import com.linseven.userservice.controller.Response;
import com.linseven.userservice.exception.ValidationException;
import com.linseven.userservice.utils.JWTUtil;
import com.linseven.userservice.utils.RedisUtil;
import com.linseven.userservice.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:26
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {


    private static final String AUTH = "Authorization";
    private static final String AUTH_USER_NAME = "user-name";

    @Value("${spring.profiles.active}")
    private String profiles;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setContentType("application/json;charset=utf-8");

        //如果是开发环境，则不需要token。直接通过
        if (!StringUtils.isEmpty(profiles) && profiles.equals("dev")) {
            return true;
        }


        //如果接口或者类上有@IgnoreToken注解，意思该接口不需要token就能访问，需要放行
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //先从类上获取该注解，判断类上是否加了IgnoreToken ，代表不需要token，直接放行
        IgnoreToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreToken.class);
        if (annotation == null) {
            //再从方法上获取该注解
            if (method.isAnnotationPresent(IgnoreToken.class)) {
                annotation = method.getAnnotation(IgnoreToken.class);
                log.info("请求方法 {} 上有注解 {} ", method.getName(), annotation);
            }
        }
        if (annotation != null) {

            return true;
        }

        String token = request.getHeader(AUTH);
        if (StringUtils.isEmpty(token)) {
            throw new ValidationException("Authorization不允许为空，请重新登录");
        }
        token = token.substring(7,token.length());
        JSON result = (JSON)redisUtil.get("token:"+token);
        if(result==null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        //UserVo userVo = JSON.toJavaObject(result,UserVo.class);






        return true;
    }



}
