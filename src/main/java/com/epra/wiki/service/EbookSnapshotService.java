package com.epra.wiki.service;

import com.epra.wiki.mapper.EbookSnapshotMapperCust;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/19 4:13 下午
 * @Description: TODO
 */
@Service
public class EbookSnapshotService {

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void genSnapshot() {
        ebookSnapshotMapperCust.genSnapshot();
    }

}