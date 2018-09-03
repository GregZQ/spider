package com.sprider.pipleline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 导入文件，按需要重写process方法。
 *    将eveninfo数据导入eventinfo对应的csv文件
 *    将deviceinfo输入导入deviceinfo对应的csv文件
 */
public class FilePip extends FilePipeline {
    private static final Logger logger= LoggerFactory.getLogger(FilePip.class);

    public FilePip() {
    }

    public FilePip(String path) {
        super(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        String eventFile="eventInfo.csv";
        String deviceFile="deviceInfo.csv";
        try {
            PrintWriter evnetPrintWriter = new PrintWriter(new FileWriter(this.getFile(path+eventFile),true));
            PrintWriter devcePrintWriter=new PrintWriter(new FileWriter(this.getFile(path+deviceFile),true));
            String temp=resultItems.get("eventInfo");
            //进行数据整理
            if (temp!=null){
                temp=temp.replace(": {\"version\":","");
                temp=temp.replace("\"packageName\":","");
                temp=temp.replace("\"mobile\":","");
                temp=temp.replace("\"eventId\":","");
                temp=temp.replace("\"time\":","");
                temp=temp.replace("\"clientType\":","");
                temp=temp.replace("{","");
                temp=temp.replace("}","");
                temp=temp.replace("\"","");
                System.out.println(temp);
                evnetPrintWriter.write(temp+"\n");
            }
            temp=resultItems.get("deviceInfo");
            if (temp!=null){
                temp=temp.replace(": {\"mobile\":","");
                temp=temp.replace("\"imei\":","");
                temp=temp.replace("\"clientType\":","");
                temp=temp.replace("\"","");
                temp=temp.replace("}","");
                temp=temp.replace("{","");
                System.out.println(temp);
                devcePrintWriter.write(temp+"\n");
            }
            evnetPrintWriter.close();
            devcePrintWriter.close();
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        }
    }
}
