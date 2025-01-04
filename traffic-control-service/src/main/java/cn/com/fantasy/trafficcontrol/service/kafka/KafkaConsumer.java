package cn.com.fantasy.trafficcontrol.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @ClassName KafkaStreamProducer
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-02 10:44 p.m.
 */
//@Component
@Slf4j
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    /**
     * 测试消息消费
     */
    @KafkaListener(topics = "user-traffic-control",id = "user-traffic-control-group")
    public void listenTopic(ConsumerRecord<String, String> record){
        logger.info("---receive {}, {} ",record.key(), record.value());
    }
}
