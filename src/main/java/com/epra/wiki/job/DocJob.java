package com.epra.wiki.job;

import com.epra.wiki.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/16 3:48 下午
 * @Description: 定时任务
 */
@Component
public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;

    /**
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron() {
        Long start = System.currentTimeMillis();
//        LOG.info("更新电子书下文档数据开始，耗时：{} 毫秒", start);
        docService.updateEbookInfo();
//        LOG.info("更新电子书下文档数据结束，耗时：{} 毫秒", System.currentTimeMillis() - start);
    }

}
