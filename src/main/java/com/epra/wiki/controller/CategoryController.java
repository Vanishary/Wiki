package com.epra.wiki.controller;

import com.epra.wiki.req.CategoryQueryReq;
import com.epra.wiki.req.CategorySaveReq;
import com.epra.wiki.resp.CategoryQueryResp;
import com.epra.wiki.resp.CommonResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 3:17 下午
 * @Description: 分类管理接口Controller
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQueryReq) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> pageResp = categoryService.list(categoryQueryReq);
        resp.setContent(pageResp);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categoryReq) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
