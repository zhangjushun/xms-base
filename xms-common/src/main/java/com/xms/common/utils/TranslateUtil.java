package com.xms.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xms.common.utils.dto.TranslateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TranslateUtil {
    @Value("${translate.url}")
    private String url;
    @Value("${translate.api-version}")
    private String version;
    @Value("${translate.key}")
    private String key;
    @Value("${translate.region}")
    private String region;
    @Autowired
    RestTemplate restTemplate;
    public String translate(TranslateDto translateDto) {
        String from=translateDto.getFrom();
        String to=translateDto.getTo();
        String textType=translateDto.getTextType();
        String text=translateDto.getText();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("api-version", version);
        stringStringMap.put("from", from);
        stringStringMap.put("to", to);
        if(StringUtils.isNotBlank(textType)&&"html".equals(textType)){
            stringStringMap.put("textType", textType);
        }
        // 请求体
        String[] split = text.split("\\|\\*\\*\\|");
        JSONArray array = new JSONArray();
        for (String s:split) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", s);
            array.add(jsonObject);
        }
        String body = JSONArray.toJSONString(array);

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Ocp-Apim-Subscription-Key", this.key);
        headers.add("Ocp-Apim-Subscription-Region", this.region);

        // 请求
        HttpEntity<String> requst = new HttpEntity<>(body, headers);

        // 使用RestTemplate请求
        ResponseEntity<JSONArray> jsonArrayResponseEntity = restTemplate.postForEntity(this.url+"?api-version={api-version}&from={from}&to={to}", requst, JSONArray.class, stringStringMap);

        System.out.println("接口返回参数:" + jsonArrayResponseEntity);
        return jsonArrayResponseEntity.getBody().toString();
    }

}
