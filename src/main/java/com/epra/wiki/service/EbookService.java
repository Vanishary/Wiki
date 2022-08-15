package com.epra.wiki.service;

import com.epra.wiki.domain.Ebook;
import com.epra.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }
}
