package com.zgy.develop.ioc.test;

import com.zgy.develop.annotation.ioc.Autowired;
import com.zgy.develop.annotation.ioc.Service;

/**
 * @author zgy
 * @data 2021/4/25 15:23
 */

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public void test() {
        testMapper.test();
    }
}
