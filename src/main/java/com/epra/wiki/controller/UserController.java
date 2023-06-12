package com.epra.wiki.controller;

import com.epra.wiki.req.UserQueryReq;
import com.epra.wiki.req.UserResetPasswordReq;
import com.epra.wiki.req.UserSaveReq;
import com.epra.wiki.resp.CommonResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.resp.UserQueryResp;
import com.epra.wiki.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/11 10:09 上午
 * @Description: 用户查询接口Controller
 */

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq userQueryReq) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> pageResp = userService.list(userQueryReq);
        resp.setContent(pageResp);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq userReq) {
        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(userReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq userResetPasswordReq) {
        userResetPasswordReq.setPassword(DigestUtils.md5DigestAsHex(userResetPasswordReq.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(userResetPasswordReq);
        return resp;
    }
}
