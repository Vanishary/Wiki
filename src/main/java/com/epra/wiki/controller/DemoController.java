package com.epra.wiki.controller;

import com.epra.wiki.domain.Demo;
import com.epra.wiki.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 3:17 下午
 * @Description: TODO
 */
@RestController
public class DemoController {

    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private DemoService demoService;

    @GetMapping("/demo/list")
    public List<Demo> list() {
        return demoService.list();
    }
}
