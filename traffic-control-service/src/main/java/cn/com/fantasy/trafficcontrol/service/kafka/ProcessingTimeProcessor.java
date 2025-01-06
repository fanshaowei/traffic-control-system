package cn.com.fantasy.trafficcontrol.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName ProcessingTimeProcessor
 * @Description 扩展对消息的处理（预留）
 * @Author fantasyfan
 * @Date 2025-01-03 9:23 p.m.
 */
@Slf4j
@Component
public class ProcessingTimeProcessor implements Processor<String, String, String, String> {
    private ProcessorContext context;
    private KeyValueStore<String, Long> stateStore;

    @Override
    public void init(ProcessorContext<String, String> context) {
        this.context = context;
        Processor.super.init(context);
    }

    @Override
    public void process(Record<String, String> record) {
        // 记录请求的开始时间
        long currentTime = System.currentTimeMillis();
        log.info("\n ----stream process---- \n {},用户:{}, 消息:{}",
                formatTimestamp(currentTime), record.key(), record.value());
        context.forward(record);
    }

    private String formatTimestamp(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    @Override
    public void close() {
        Processor.super.close();
    }
}
