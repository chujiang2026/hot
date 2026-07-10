package com.hot.modules.fs.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hot.common.config.FeishuProperties;
import com.hot.common.util.HttpClientUtil;
import com.hot.common.util.JsonUtils;
import com.hot.modules.sys.dao.BasicDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FsService {

    @Autowired
    private FeishuProperties feishuProperties;

    @Autowired
    private BasicDao basicDao;

    public String init() {
        String url = "";
        try {
            url = URLEncoder.encode(feishuProperties.getRedirectUrl(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return feishuProperties.getCodeUrl()+"?client_id="+feishuProperties.getAppId()+"&response_type=code&redirect_uri="+url;
    }

    //飞书获取 user_access_token
    public String getAccessToken(String code) throws Exception{
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", feishuProperties.getAppId());
        body.put("client_secret", feishuProperties.getAppSecret());
        body.put("code", code);
        body.put("redirect_uri", feishuProperties.getRedirectUrl());
        String json = HttpClientUtil.doPostJson(feishuProperties.getTokenUrl(), JsonUtils.toJson(body));
        basicDao.insertFsLog("getAccessToken", json);
        Map<String,Object> m=new ObjectMapper().readValue(json, new TypeReference<HashMap<String,Object>>(){});
        if (m.get("code").toString().equals("0")) {
            return m.get("access_token").toString();
        }
        return null;
    }

    public String getPhone(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userToken);
        String json = HttpClientUtil.doGet(feishuProperties.getUserUrl(),headers);
        basicDao.insertFsLog("user_info", json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            if (jsonNode.get("code").intValue() == 0) {
                JsonNode data = jsonNode.get("data");
                return data.get("mobile").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
