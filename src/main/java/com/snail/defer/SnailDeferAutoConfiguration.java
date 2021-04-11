package com.snail.defer;

import com.snail.defer.advisor.DeferPointcutAdvisor;
import com.snail.defer.config.SnailDeferProperties;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer
 * @Description:
 * @date: 2021/04/11
 */
@Configuration
@EnableConfigurationProperties(SnailDeferProperties.class)
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnProperty(prefix = SnailDeferProperties.PREFIX, value = "enable", havingValue = "true", matchIfMissing = true)
public class SnailDeferAutoConfiguration {

    @Bean
    public PointcutAdvisor dynamicDatasourceAnnotationAdvisor(SnailDeferProperties properties) {
        return new DeferPointcutAdvisor(properties);
    }

}
