package com.sprider.processor;

import com.sprider.utils.RequestUtils;
import com.sprider.utils.SearchProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 获取所有的数据量
 */
public class MainProcessor implements PageProcessor {

    private static final Logger logger= LoggerFactory.getLogger(MainProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public int count=0;


    @Override
    public void process(Page page) {
        String sums=new JsonPathSelector(SearchProperties.SUM_PATH).select(page.getRawText());
        count=Integer.valueOf(sums);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
