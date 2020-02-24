package com.ls.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className: AlipayAccoutConfig
 * @description:
 * @author: liusCoding
 * @create: 2020-02-14 12:02
 */

@Component
@ConfigurationProperties("alipay")
@Data
public class AlipayAccountConfig {
    private String appId;

    private String privateKey;

    private String publicKey;

    private String notifyUrl;

    private String returnUrl;
}
