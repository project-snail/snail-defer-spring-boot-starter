package com.snail.defer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer.properties
 * @Description:
 * @date: 2021/04/11
 */
@Data
@ConfigurationProperties(prefix = SnailDeferProperties.PREFIX)
public class SnailDeferProperties {

    public static final String PREFIX = "snail.defer";

    private Boolean enable = false;

}
