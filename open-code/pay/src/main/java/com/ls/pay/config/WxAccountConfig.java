package com.ls.pay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className: WxAccountConfig
 * @description:
 * @author: liusCoding
 * @create: 2020-02-14 11:47
 */
@Component
@ConfigurationProperties("wx")
@Getter
@Setter
public class WxAccountConfig {

    private String appId;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String returnUrl;
}
