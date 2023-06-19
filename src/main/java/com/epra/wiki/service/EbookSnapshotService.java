package com.epra.wiki.service;

import com.epra.wiki.mapper.EbookSnapshotMapperCust;
import com.epra.wiki.resp.StatisticResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获取首页数值数据：总阅读数、总点赞数、今日阅读数、今日点赞数、今日预计阅读数、今日预计阅读增长
     */
    public List<StatisticResp> getStatistic() {
        return ebookSnapshotMapperCust.getStatistic();
    }
}