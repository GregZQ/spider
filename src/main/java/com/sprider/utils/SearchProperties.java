package com.sprider.utils;

/**
 * 爬虫需要的属性
 */
public class SearchProperties {
    //jsonpath匹配规则
    public static final String SUM_PATH="$.responses[0].hits.total";
    //jsonpath匹配规则
    public static final String DATA_PATH="$.responses[0].hits.hits[*]._source.message";

    public static final String URL="http:/localhost:5601/elasticsearch/_msearch";

    public static final String KBN_VERSION="kbn-version";
    public static final String KBN_VERSION_VALUE="4.6.6";

    public static final String CON_TYPE="Content-Type";
    public static final String CON_TYPE_VALUE="text/plain";

}
