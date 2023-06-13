package com.epra.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.epra.wiki.req.UserLoginReq;
import com.epra.wiki.req.UserQueryReq;
import com.epra.wiki.req.UserResetPasswordReq;
import com.epra.wiki.req.UserSaveReq;
import com.epra.wiki.resp.CommonResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.resp.UserLoginResp;
import com.epra.wiki.resp.UserQueryResp;
import com.epra.wiki.service.UserService;
import com.epra.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

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

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq userLoginReq) {
        userLoginReq.setPassword(DigestUtils.md5DigestAsHex(userLoginReq.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(userLoginReq);

        // 生成单点登录token，并放入redis中
        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token:{}，并放入redis中", token.toString());
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token, JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从Redis中删除token:{}", token);
        return resp;
    }
}
