package com.template.service.utils;


import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project Name:dcp-opinion
 * File Name:ESUtil
 * Package Name:com.yk.dcp.opinion.utils
 * Date:2018/10/11
 * Author:liujie
 * Description:ES操作工具类
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */


public class ESUtil {
    /**
     * 获取一个jest客户端
     *
     * @param esUrl elasticsearch的http://ip:port
     * @return jest的客户端
     * 注：1、默认情况不超过两个并发线程
     * 2、默认情况下连接总数不超过20
     * 3、不要为每个请求构建jestclient，它是被单独设计的
     */
    public static JestClient getClient(String esUrl) {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(esUrl)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }

    /**
     * 创建索引
     *
     * @param jestClient jest客户端
     * @param indexName  索引名
     * @return 结果对象
     */
    public static JestResult createIndex(JestClient jestClient, String indexName) throws Exception {
        return jestClient.execute(new CreateIndex.Builder(indexName).build());
    }

    /**
     * 判断索引是否存在
     *
     * @param jestClient 客户端
     * @param indexName  索引名称
     * @return 结果对象
     * @throws Exception
     */
    public static JestResult indicesExist(JestClient jestClient, String indexName) throws Exception {
        IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
        return jestClient.execute(indicesExists);
    }

    /**
     * 删除索引
     *
     * @param jestClient 客户端
     * @param indexName  索引名
     * @return 结果对象
     * @throws Exception
     */
    public static JestResult deleteIndex(JestClient jestClient, String indexName) throws Exception {
        return jestClient.execute(new DeleteIndex.Builder(indexName).build());
    }

    /**
     * 创建文档
     *
     * @param jestClient jest客户端
     * @param indexName  索引名
     * @param typeName   类型名
     * @param source     文档资源 （map、json、pojo、JSONBuilder皆可）
     * @return 结果对象
     */
    public static JestResult createDocument(JestClient jestClient, String indexName, String typeName, Object source) throws Exception {

        Index index = new Index.Builder(source).index(indexName).type(typeName).build();
        return jestClient.execute(index);
    }

    /**
     * 批量创建文档
     *
     * @param jestClient jest客户端
     * @param indexName  索引名
     * @param typeName   类型名
     * @param sources    文档资源集合（pojo集合）
     * @return 结果对象
     */
    /*public static JestResult createDocuments(JestClient jestClient, String indexName, String typeName, List<NewsModel> sources) throws Exception {
        List<Index> indices = sources.stream().map(source -> new Index.Builder(source).index(indexName).type(typeName).build()).collect(Collectors.toList());
        Bulk bulk = new Bulk.Builder()
                .defaultIndex(indexName)
                .defaultType(typeName)
                .addAction(indices).build();
        return jestClient.execute(bulk);
    }*/

    public static JestResult createOriginDocuments(JestClient jestClient,
                                                   String indexName,
                                                   String typeName,
                                                   List<Object> sources) throws Exception {
        List<Index> indices = sources.stream().map(source -> new Index.Builder(source).index(indexName).type(typeName).build()).collect(Collectors.toList());
        Bulk bulk = new Bulk.Builder()
                .defaultIndex(indexName)
                .defaultType(typeName)
                .addAction(indices).build();
        return jestClient.execute(bulk);
    }


    /**
     * 全体查询生成器
     *
     * @return 查询生成器
     */
    public static QueryBuilder matchAllQuery() {
        return QueryBuilders.matchAllQuery();
    }

    /**
     * 单值匹配查询生成器
     *
     * @param field 字段
     * @param text  值
     * @return 查询生成器
     */
    public QueryBuilder matchQuery(String field, Object text){
        return QueryBuilders.matchQuery(field, text);
    }

    /**
     * 词组匹配查询生成器
     *
     * @param field 字段
     * @param text  值
     * @return 查询生成器
     */
    public static QueryBuilder matchPhraseQuery(String field, Object text) {
        return QueryBuilders.matchPhraseQuery(field, text);
    }

    /**
     * 通配符和正则表达式查询生成器
     *
     * @param field 字段
     * @param text  值
     * @return 查询生成器
     */
    public QueryBuilder wildcardQuery(String field, String text) {
        return QueryBuilders.wildcardQuery(field, text);
    }

    /**
     * 组合查询(与查询)
     *
     * @param conditions 查询生成器集合
     * @return 查询生成器
     */
    public QueryBuilder andConditionQuery(List<QueryBuilder> conditions) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder condition : conditions) {
            boolQueryBuilder = boolQueryBuilder.must(condition);
        }
        return boolQueryBuilder;
    }

    /**
     * 或查询
     *
     * @param conditions 查询生成器集合
     * @return 查询生成器
     */
    public static QueryBuilder orConditionQuery(List<QueryBuilder> conditions) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder condition : conditions) {
            boolQueryBuilder = boolQueryBuilder.should(condition);
        }
        return boolQueryBuilder;
    }

    /**
     * 查询
     *
     * @param jestClient 客户端
     * @param indexName  索引名
     * @param typeName   类型名
     * @param sourceType pojo类型
     * @param <T>        泛型
     * @return 查询结果集
     * @throws IOException
     */
    public static <T> Map<String,Object> search(JestClient jestClient,
                                                             String indexName,
                                                             String typeName,
                                                             int from,
                                                             Class<T> sourceType,
                                                             QueryBuilder queryBuilder) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String query = searchSourceBuilder.query(queryBuilder).toString();
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .setParameter("from",from)
                .setParameter("size",10)
                .build();
        Map<String,Object> map = new HashMap<>();
        SearchResult result = jestClient.execute(search);
        List<SearchResult.Hit<T, Void>> hits = result.getHits(sourceType);
        map.put("hits",hits);
        map.put("total",result.getTotal());
        return map;
    }

    public static <T> Map<String,Object> searchWithPageAndSortTest(JestClient jestClient,
                                                                                    String indexName,
                                                                                    String typeName,
                                                                                    Class<T> sourceType,
                                                                                    QueryBuilder queryBuilder,
                                                                                    int from,
                                                                                    int size) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String query = searchSourceBuilder.query(queryBuilder).toString();
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .setParameter("from",from)
                .setParameter("size",size)
                .build();
        SearchResult result = jestClient.execute(search);
        List<SearchResult.Hit<T, Void>> hits = result.getHits(sourceType);
        long count = result.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("hits",hits);
        map.put("total",count);
        return map;
    }

    /**
     * 文档id进行查询
     *
     * @param jestClient jest客户端
     * @param indexName  索引名
     * @param typeName   类型名
     * @param id         文档id
     * @param sourceType 文档资源pojo类型
     * @param <T>        对象类型
     * @return 对象
     */
    public <T> T getDocumentById(JestClient jestClient, String indexName, String typeName, String id, Class<T> sourceType) throws Exception {
        Get get = new Get.Builder(indexName, id).type(typeName).build();
        JestResult result = jestClient.execute(get);
        return result.getSourceAsObject(sourceType);
    }


    /**
     * 根据文档id删除文档
     *
     * @param jestClient jest客户端
     * @param indexName  索引名
     * @param typeName   类型名
     * @param id         文档id
     * @return 结果
     */
    public JestResult deleteDocument(JestClient jestClient, String indexName, String typeName, String id) throws Exception {
        return jestClient.execute(new Delete.Builder(id)
                .index(indexName)
                .type(typeName)
                .build());
    }

    /**
     * 查看节点信息
     *
     * @param jestClient 客户端
     * @return 结果对象
     * @throws Exception
     */
    public JestResult nodesInfo(JestClient jestClient) throws Exception {
        return jestClient.execute(new NodesInfo.Builder().build());
    }

    /**
     * 查看集群健康信息
     *
     * @param jestClient 客户端
     * @return 结果对象
     * @throws Exception
     */
    public JestResult health(JestClient jestClient) throws Exception {
        return jestClient.execute(new Health.Builder().build());
    }

    /**
     * 节点状态
     *
     * @param jestClient 客户端
     * @return 结果对象
     * @throws Exception
     */
    public JestResult nodesStates(JestClient jestClient) throws Exception {
        return jestClient.execute(new NodesStats.Builder().build());
    }


    /**
     * 关闭JestClient客户端
     *
     * @param jestClient 客户端
     * @throws IOException
     */
    public static void closeJestClient(JestClient jestClient) throws IOException {
        if (jestClient != null) {
            jestClient.close();
        }
    }
}
