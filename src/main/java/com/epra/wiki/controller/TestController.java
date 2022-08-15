package com.epra.wiki.controller;

import com.epra.wiki.domain.Test;
import com.epra.wiki.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 3:17 下午
 * @Description: TODO
 */
@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Value("${test.name:Test}")
    private String testName;

    @Resource
    private TestService testService;

    @GetMapping("/hello")
    public String hello() {
        return "{\"name\":\"Hello World!!!\"}" + testName;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name) {
        return "Hello World!!POST!" + name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}
