package com.epra.wiki.service;

import com.epra.wiki.domain.User;
import com.epra.wiki.domain.UserExample;
import com.epra.wiki.exception.BusinessException;
import com.epra.wiki.exception.BusinessExceptionCode;
import com.epra.wiki.mapper.UserMapper;
import com.epra.wiki.req.UserLoginReq;
import com.epra.wiki.req.UserQueryReq;
import com.epra.wiki.req.UserResetPasswordReq;
import com.epra.wiki.req.UserSaveReq;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.resp.UserLoginResp;
import com.epra.wiki.resp.UserQueryResp;
import com.epra.wiki.util.CopyUtil;
import com.epra.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/11 10:12 上午
 * @Description: 用户查询接口Service
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq userQueryReq) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(userQueryReq.getLoginName())) {
            criteria.andLoginNameEqualTo(userQueryReq.getLoginName());
        }

        PageHelper.startPage(userQueryReq.getPage(), userQueryReq.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 重置密码
     */
    public void resetPassword(UserResetPasswordReq userResetPasswordReq) {
        User user = CopyUtil.copy(userResetPasswordReq, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 保存
     */
    public void save(UserSaveReq userSaveReq) {
        User user = CopyUtil.copy(userSaveReq, User.class);
        if (ObjectUtils.isEmpty(userSaveReq.getId())) {
            User user1 =  selectByLoginName(userSaveReq.getLoginName());
            if (ObjectUtils.isEmpty(user1)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }

        } else {
            user.setLoginName(null);
            user.setPassword(null);
            // 使用Selective时，user中有值更新，空不更新
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 删除
     */
    public void delete(long id) {
//        if (!ObjectUtils.isEmpty(id)) {
        // delete
        userMapper.deleteByPrimaryKey(id);
//        } else {
        // 更新
//            ebookMapper.updateByPrimaryKey(ebook);
//        }
    }

    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    /**
     * 登录
     */
    public UserLoginResp login(UserLoginReq userLoginReq) {
        User userDb = selectByLoginName(userLoginReq.getLoginName());

        if (ObjectUtils.isEmpty(userDb)) {
            // 用户不存在
            LOG.info("用户不存在,{}", userLoginReq.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(userLoginReq.getPassword())) {
                // 验证成功
                UserLoginResp userLoginResp =  CopyUtil.copy(userDb,UserLoginResp.class);
                return userLoginResp;
            } else {
                // 密码校验不通过
                LOG.info("密码不对，用户名：{},输入密码：{}",userLoginReq.getLoginName(),userLoginReq.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}
