package com.wzp.pet.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * classpath:spring-context.xml测试service
 * classpath:spring-mybatis.xml测试dao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-context.xml","classpath:spring/spring-mybatis.xml"})
public class BaseTest {
}

