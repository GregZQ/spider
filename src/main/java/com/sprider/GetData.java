package com.sprider;

import com.sprider.pipleline.FilePip;
import com.sprider.processor.DataProcessor;
import com.sprider.processor.MainProcessor;
import com.sprider.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主方法，通过它进行数据的爬取。
 *    1.查询总数据量
 *    2.开线程进行数据分页爬取
 *
 *
 *   对于方法体当中的startTime与endTime，
 *   可以设置定时任务，通过getBeforeDay获取之前一天的区间，
 *   从而获取之前一天内的数据
 */
public class GetData  {
    private static int count=0;
    private static final Logger  logger= LoggerFactory.getLogger(GetData.class);

    public void startTask(){
        Map<String,Long> map=new HashMap<>();
        //long startTime=map.get("startTime");
        //long  endTime=map.get("endTime");
        long startTime=Long.valueOf("1535212800000");
        long endTime=Long.valueOf("1535817599999");
        logger.info("-----查询数据量------");
        Request countRequest= RequestUtils.createPostRequest(startTime,
                endTime,0,0);
        MainProcessor mainProcessor=new MainProcessor();
        Spider.create(mainProcessor).addRequest(countRequest).run();
        logger.info("----数据共有-----"+mainProcessor.count);
        GetData.count=mainProcessor.count;
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        DataProcessor dataProcessor=new DataProcessor();
        for (int i=0;i<count;) {
            int start = i;
            executorService.execute(new Runnable() {
                int temp = start;

                @Override
                public void run() {
                    Request request = RequestUtils.createPostRequest(
                            startTime, endTime, temp,
                            5000);
                    logger.info("开始线程执行-----------");
                    Spider.create(dataProcessor).addRequest(request)
                            .addPipeline(new FilePip("D:\\webmagic\\"))
                            .run();
                }
            });
            i = (start + 5000);
        }
        executorService.shutdown();
    }

    /**
     * 获取之前一天的最早于最晚
     * @return
     */
    public static Map<String,Long> getBeforeDay(){
        Map<String,Long>  map=new HashMap<>();
        //获取精准时间
        long  accurateTime=System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String accurate=simpleDateFormat.format(accurateTime);
        Date date=null;
        try {
            date=simpleDateFormat.parse(accurate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endTime=date.getTime()-1;
        long startTime=date.getTime()-86400000;
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return map;
    }
    public static void main(String args[]){
        GetData getData=new GetData();
        getData.startTask();

        //GetData.getBeforeDay();
    }
}
