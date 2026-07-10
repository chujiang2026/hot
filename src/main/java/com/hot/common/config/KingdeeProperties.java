package com.hot.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "kingdee.k3cloud")
public class KingdeeProperties {
    private String serverUrl;
    private String dcId;
    private String userName;
    private String password;
    private String appId;
    private String appSecret;
    private Integer lcId = 2052;
}
