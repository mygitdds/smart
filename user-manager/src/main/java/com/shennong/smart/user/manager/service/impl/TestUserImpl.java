package com.shennong.smart.user.manager.service.impl;

import com.shennong.smart.user.manager.service.TestUser;

/**
 * TestUserImpl
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/2
 */
public class TestUserImpl implements TestUser {
    @Override
    public void addUser() {
        System.out.println("测试新增成功了。");
    }
}
