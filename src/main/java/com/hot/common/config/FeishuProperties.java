package com.hot.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 飞书自建应用配置。对应 application.yml 中 feishu.* 节点。
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "feishu")
public class FeishuProperties {

    /** 飞书开放平台应用的 App ID。 */
    private String appId;

    /** 飞书开放平台应用的 App Secret。 */
    private String appSecret;
    private String codeUrl;
    private String tokenUrl;
    private String userUrl;
    private String redirectUrl;

}
