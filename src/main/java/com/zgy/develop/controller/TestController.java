package com.zgy.develop.controller;

import com.zgy.develop.annotation.custom.CosmoController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zgy
 * @data 2021/4/19 17:21
 */

@RequestMapping("test")
@CosmoController
public class TestController {

    @GetMapping("test")
    public Integer test() {
        return 100;
    }
}
