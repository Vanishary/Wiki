package com.epra.wiki.service;

import com.epra.wiki.domain.Ebook;
import com.epra.wiki.domain.EbookExample;
import com.epra.wiki.mapper.EbookMapper;
import com.epra.wiki.req.EbookReq;
import com.epra.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/14 10:31 下午
 * @Description: TODO
 */
@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq ebookReq) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + ebookReq.getName() + "%");
        List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebooksList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp);
            respList.add(ebookResp);
        }
        return respList;
    }
}
