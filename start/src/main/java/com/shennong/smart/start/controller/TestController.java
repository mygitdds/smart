package com.shennong.smart.start.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.shennong.smart.order.manager.service.TestSofa;
import com.shennong.smart.user.manager.service.TestUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/2
 */
@RestController
public class TestController {
    @SofaReference
    private TestUser users;
    @SofaReference
    private TestSofa sofa;
    @RequestMapping("/addUser")
    public String addUser(){
        users.addUser();
        return "成功的";
    }
    @RequestMapping("/testSofa")
    public String testSofa(){
        sofa.addSofa();
        return "成功了";
    }
}
