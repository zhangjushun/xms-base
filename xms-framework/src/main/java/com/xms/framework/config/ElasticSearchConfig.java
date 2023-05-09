package com.xms.framework.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
    @Value("${es717.elasticsearch.ip:}")
    private String ip;
    @Bean
    public ElasticsearchClient elasticsearchClient(){
        if(StringUtils.isBlank(ip)){
            return null;
        }
        RestClient restClient = RestClient.builder(
                new HttpHost(ip, 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
