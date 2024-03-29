package com.epra.wiki.service;

import com.epra.wiki.domain.Ebook;
import com.epra.wiki.domain.EbookExample;
import com.epra.wiki.mapper.EbookMapper;
import com.epra.wiki.req.EbookQueryReq;
import com.epra.wiki.req.EbookSaveReq;
import com.epra.wiki.resp.EbookQueryResp;
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
 * @Description: 电子书列表查询接口Service
 */
@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq ebookQueryReq) {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(ebookQueryReq.getName())) {
            criteria.andNameLike("%" + ebookQueryReq.getName() + "%");
        }

        if (!ObjectUtils.isEmpty(ebookQueryReq.getCategory2Id())) {
            criteria.andCategory2IdEqualTo(ebookQueryReq.getCategory2Id());
        }

        PageHelper.startPage(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooksList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<EbookQueryResp> respList = CopyUtil.copyList(ebooksList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EbookSaveReq ebookSaveReq) {
        Ebook ebook = CopyUtil.copy(ebookSaveReq, Ebook.class);
        if (ObjectUtils.isEmpty(ebook.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    /**
     * 删除
     */
    public void delete(long id) {
//        if (!ObjectUtils.isEmpty(id)) {
            // delete
            ebookMapper.deleteByPrimaryKey(id);
//        } else {
            // 更新
//            ebookMapper.updateByPrimaryKey(ebook);
//        }
    }
}

