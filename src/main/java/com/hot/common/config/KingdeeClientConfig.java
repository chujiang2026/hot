package com.hot.common.config;

import com.hot.common.config.KingdeeProperties;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KingdeeClientConfig {

    @Autowired
    private KingdeeProperties kingdeeProperties;

    @Bean
    public IdentifyInfo identifyInfo() {
        IdentifyInfo identify = new IdentifyInfo();
        identify.setServerUrl(kingdeeProperties.getServerUrl());
        identify.setdCID(kingdeeProperties.getDcId());
        identify.setUserName(kingdeeProperties.getUserName());
        identify.setPwd(kingdeeProperties.getPassword());
        identify.setAppId(kingdeeProperties.getAppId());
        identify.setAppSecret(kingdeeProperties.getAppSecret());
        identify.setlCID(kingdeeProperties.getLcId());
        return identify;
    }

    @Bean
    public K3CloudApi k3CloudApi(IdentifyInfo identifyInfo) {
        return new K3CloudApi(identifyInfo, true);
    }
}