package cn.com.fantasy.trafficcontrol.service.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName KafkaConfig
 * @Description kafka配置
 * @Author fantasyfan
 * @Date 2025-01-04 12:06 a.m.
 */
@Data
@Configuration
public class KafkaConfig {
    /**
     *  kafka服务地址
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * kafka流计算应用id
     */
    @Value("${spring.kafka.streams.application-id}")
    private String appIdConfig;
}
