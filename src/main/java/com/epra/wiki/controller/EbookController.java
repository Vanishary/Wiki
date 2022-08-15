package com.epra.wiki.controller;

import com.epra.wiki.domain.Ebook;
import com.epra.wiki.service.EbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 3:17 下午
 * @Description: TODO
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    private static final Logger LOG = LoggerFactory.getLogger(EbookController.class);

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public List<Ebook> list() {
        return ebookService.list();
    }
}
