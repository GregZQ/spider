package com.sprider.pipleline;

import com.sprider.dao.DataDao;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


/**
 * 导入数据库，按需要重写process方法
 */
public class DataBasePip implements PageModelPipeline {
    @Autowired
    private DataDao dataDao;

    @Override
    public void process(Object o, Task task) {

    }
}
