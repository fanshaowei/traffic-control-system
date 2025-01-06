package cn.com.fantasy.trafficcontrol.service.tokenbucket;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TrafficConfig
 * @Description 流量监听配置
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

    /**
     * 流量消息监听通道
     */
    @Value("${traffic.kafka-user-traffic-topic}")
    private String kafkaUserTrafficTopic;
}
