package com.sprider.utils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.text.SimpleDateFormat;

/**
 * 创建request的工具
 */
public class RequestUtils {
    //创建post请求
    public static Request createPostRequest(){
        Request request=new Request();

        request.addHeader(SearchProperties.CON_TYPE,SearchProperties.CON_TYPE_VALUE);
        request.addHeader(SearchProperties.KBN_VERSION,SearchProperties.KBN_VERSION_VALUE);
        request.setMethod(HttpConstant.Method.POST);
        request.setUrl(SearchProperties.URL);
        return  request;
    }
    //创建带body的post请求
    public static Request createPostRequest(long beginTime,long endTime,int start,int count){

        Request request=createPostRequest();
        HttpRequestBody body=createBody(beginTime,endTime,start,count);
        request.setRequestBody(body);
        return request;
    }

    /**
     * 由于公司内部使用的elk，查询需要查询体，四个参数都是方法体内使用到的。参照请求的url  body进行构造
     * @param beginTime  查询的开始时间
     * @param endTime    查询的结束时间
     * @param start      查询开始
     * @param count      查询数量
     * @return
     */
    public static HttpRequestBody createBody(long beginTime,long endTime,int start,int count){

        HttpRequestBody requestBody=new HttpRequestBody();
        String logFile=longToDate(endTime);
        //如果测试logile改成2018.08.30
        String body="{\"index\":[\"logstash-"+logFile+"\"],\"ignore_unavailable\":true}\n" +
                "{\"size\":"
                +count+",\"_source\":[\"message\"],\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"from\":"
                +start+",\"query\":{\"filtered\":{\"query\":{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true}},\"filter\":{\"bool\":{\"must\":[{\"range\":{\"@timestamp\":{\"gte\":"
                +beginTime+",\"lte\":"
                +endTime+",\"format\":\"epoch_millis\"}}}],\"must_not\":[]}}}},\"fields\":[\"*\",\"_source\"],\"script_fields\":{}}\n" +
                "\n";
        requestBody.setBody(body.getBytes());
        return requestBody;
    }

    public static String longToDate(long currentTime){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
        String time=simpleDateFormat.format(currentTime);
        System.out.println(time);
        return time;
    }
}
