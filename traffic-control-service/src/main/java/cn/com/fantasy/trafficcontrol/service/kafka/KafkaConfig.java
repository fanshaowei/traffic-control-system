package cn.com.fantasy.trafficcontrol.service.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName KafkaConfig
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-04 12:06 a.m.
 */
@Data
@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.application-id}")
    private String appIdConfig;
}
