package com.sprider.processor;

import com.sprider.utils.SearchProperties;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

/**
 * 用于获取数据，并根据选择的Pipeline进行数据保存
 */
public class DataProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(60000);

    @Override
    public void process(Page page) {
        System.out.println(page.getRawText());
        List<String> list= new JsonPathSelector(SearchProperties.DATA_PATH).selectList(page.getRawText());
        String temp=null;
        int pos=-1;
       for(int i=0;i<list.size();i++){
           temp=list.get(i);
           pos=temp.indexOf("deviceInfo");
           if (pos!=-1){
               page.putField("deviceInfo",temp.substring(pos+"deviceInfo-".length()));
           }else{
               pos=temp.indexOf("eventInfo");
                if (pos!=-1){
                    page.putField("eventInfo",temp.substring(pos+"eventInfo-".length()));
                }
           }
       }


    }


    @Override
    public Site getSite() {
        return site;
    }
}
