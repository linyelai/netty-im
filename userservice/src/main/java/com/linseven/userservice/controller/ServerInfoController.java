package com.linseven.userservice.controller;

import com.linseven.IMServerInfo;
import com.linseven.userservice.service.IMServerInfoService;
import com.linseven.userservice.vo.UserVo;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/13 11:52
 */
@RestController
public class ServerInfoController {

    @Autowired
    private IMServerInfoService imServerInfoService;
    @Autowired
    private ThreadLocal<UserVo> threadLocal ;

    @GetMapping("/getServerInfo")
    public Response<IMServerInfo> getServerInfo(HttpServletRequest request) throws Exception {

        Response response = new Response();
        String ip = request.getRemoteAddr();
        UserVo userVo = threadLocal.get();
        String userId = userVo.getUserId()+"";
        IMServerInfo imServerInfo = imServerInfoService.getServerInfo(ip,userId);
        response.setData(imServerInfo);
        threadLocal.remove();
        return response;

    }
}
