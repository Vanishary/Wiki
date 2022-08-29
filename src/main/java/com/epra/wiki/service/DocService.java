package com.epra.wiki.service;

import com.epra.wiki.controller.DemoController;
import com.epra.wiki.domain.Doc;
import com.epra.wiki.domain.DocExample;
import com.epra.wiki.mapper.DocMapper;
import com.epra.wiki.req.DocQueryReq;
import com.epra.wiki.req.DocSaveReq;
import com.epra.wiki.resp.DocQueryResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.util.CopyUtil;
import com.epra.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 10:31 下午
 * @Description: 文档列表查询接口Service
 */
@Service
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq docQueryReq) {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();

        if (!ObjectUtils.isEmpty(docQueryReq.getName())) {
            criteria.andNameLike("%" + docQueryReq.getName() + "%");
        }

        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docsList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docsList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<DocQueryResp> respList = CopyUtil.copyList(docsList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 查询全表
     * @param
     * @return
     */
    public List<DocQueryResp> all() {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();

//        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docsList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = CopyUtil.copyList(docsList, DocQueryResp.class);

        return respList;
    }

    /**
     * 保存
     */
    public void save(DocSaveReq docSaveReq) {
        Doc doc = CopyUtil.copy(docSaveReq, Doc.class);
        if (ObjectUtils.isEmpty(doc.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    /**
     * 删除
     */
    public void delete(long id) {
            docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> idStr) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(idStr);

        docMapper.deleteByExample(docExample);
    }
}

