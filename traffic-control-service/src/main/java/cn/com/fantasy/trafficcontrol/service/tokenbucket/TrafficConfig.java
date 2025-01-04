package cn.com.fantasy.trafficcontrol.service.tokenbucket;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName TrafficConfig
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-02 3:50 p.m.
 */
@Data
@Configuration
public class TrafficConfig {

    /**
     * 限流时间区间,毫秒为单位
     */
    @Value("${traffic.requestLimitInterval}")
    private Long requestLimitInterval;

    /**
     * 限流大小
     */
    @Value("${traffic.qpsLimit}")
    private Long qpsLimit;

    @Value("${traffic.kafka-user-traffic-topic}")
    private String kafkaUserTrafficTopic;
}
