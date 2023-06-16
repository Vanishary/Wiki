package com.epra.wiki.service;

import com.epra.wiki.controller.DemoController;
import com.epra.wiki.domain.Content;
import com.epra.wiki.domain.Doc;
import com.epra.wiki.domain.DocExample;
import com.epra.wiki.exception.BusinessException;
import com.epra.wiki.exception.BusinessExceptionCode;
import com.epra.wiki.mapper.ContentMapper;
import com.epra.wiki.mapper.DocMapper;
import com.epra.wiki.mapper.DocMapperCust;
import com.epra.wiki.req.DocQueryReq;
import com.epra.wiki.req.DocSaveReq;
import com.epra.wiki.resp.DocQueryResp;
import com.epra.wiki.resp.PageResp;
import com.epra.wiki.util.CopyUtil;
import com.epra.wiki.util.RedisUtil;
import com.epra.wiki.util.RequestContext;
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
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

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
     *
     * @param
     * @return
     */
    public List<DocQueryResp> all(Long ebookId) {

        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
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
        // doc.id=content.id
        Doc doc = CopyUtil.copy(docSaveReq, Doc.class);
        Content content = CopyUtil.copy(docSaveReq, Content.class);
        if (ObjectUtils.isEmpty(doc.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新   WithBLOBs 表示带大字段
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
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

    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        // 文档阅读数1
        docMapperCust.increaseViewCount(id);
        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else {
            return content.getContent();
        }
    }

    public void voteCount(Long id) {
        // docMapperCust.increaseVoteCount(id);
        // 远程IPdoc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddr();
        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 3600 * 24)) {
            docMapperCust.increaseVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
    }

    /**
     * 定时更新Ebook点赞、阅读数等信息
     */
    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }
}

