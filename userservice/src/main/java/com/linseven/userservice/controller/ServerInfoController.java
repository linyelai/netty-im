package com.linseven.userservice.controller;

import com.linseven.userservice.model.IMServerInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/13 11:52
 */
@RestController
public class ServerInfoController {

    @GetMapping("/getServerInfo")
    public IMServerInfo getServerInfo(){


        return null;
    }
}
