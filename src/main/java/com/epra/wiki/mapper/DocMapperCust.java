package com.epra.wiki.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/15 9:58 下午
 * @Description:
 */
public interface DocMapperCust {
    public void increaseViewCount(@Param("id") Long id);
    public void increaseVoteCount(@Param("id") Long id);
}
