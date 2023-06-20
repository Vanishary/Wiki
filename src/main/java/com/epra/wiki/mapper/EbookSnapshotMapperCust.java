package com.epra.wiki.mapper;

import com.epra.wiki.resp.StatisticResp;

import java.util.List;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/19 4:12 下午
 * @Description: TODO
 */

public interface EbookSnapshotMapperCust {
    public void genSnapshot();

    public List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
