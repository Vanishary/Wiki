package com.epra.wiki.controller;

import com.epra.wiki.req.DocQueryReq;
import com.epra.wiki.req.DocSaveReq;
import com.epra.wiki.resp.CommonResp;
import com.epra.wiki.resp.DocQueryResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 3:17 下午
 * @Description: 文档管理接口Controller
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    private static final Logger LOG = LoggerFactory.getLogger(DocController.class);

    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq docQueryReq) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> pageResp = docService.list(docQueryReq);
        resp.setContent(pageResp);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq docReq) {
        CommonResp resp = new CommonResp<>();
        docService.save(docReq);
        return resp;
    }

    @DeleteMapping("/delete/{idStr}")
    public CommonResp delete(@PathVariable String idStr) {
        CommonResp resp = new CommonResp<>();
        if (!ObjectUtils.isEmpty(idStr)) {
            List<String> list = Arrays.asList(idStr.split(","));
            docService.delete(list);
        }
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }
}
