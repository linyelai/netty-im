package com.linseven.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:06
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){

        return "index";
    }
}
