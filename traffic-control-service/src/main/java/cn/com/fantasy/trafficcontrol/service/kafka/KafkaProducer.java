package cn.com.fantasy.trafficcontrol.service.kafka;

import cn.com.fantasy.trafficcontrol.service.tokenbucket.TrafficConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaStreamProducer
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-02 11:30 p.m.
 */
@Slf4j
@Component
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private TrafficConfig trafficConfig;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate,
                         TrafficConfig trafficConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.trafficConfig = trafficConfig;
    }

    public void sendMsg(String userId, String msg) {
        Long time = System.currentTimeMillis();
        ProducerRecord<String, String> record = new ProducerRecord<>(this.trafficConfig.getKafkaUserTrafficTopic(), 0, time, userId, msg);
        kafkaTemplate.send(record);
    }
}
