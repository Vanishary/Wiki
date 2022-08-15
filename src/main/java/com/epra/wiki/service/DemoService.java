package com.epra.wiki.service;

import com.epra.wiki.domain.Demo;
import com.epra.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 10:31 下午
 * @Description: TODO
 */
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
