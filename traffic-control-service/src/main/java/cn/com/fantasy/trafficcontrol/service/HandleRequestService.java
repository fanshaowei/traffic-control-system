package cn.com.fantasy.trafficcontrol.service;

import cn.com.fantasy.trafficcontrol.service.kafka.KafkaProducer;
import cn.com.fantasy.trafficcontrol.service.tokenbucket.UserTokenBucketManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @ClassName HandleRequestService
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-03 12:19 p.m.
 */
@Service
public class HandleRequestService {
    @Autowired
    private UserTokenBucketManage userTokenBucketManage;

    @Autowired
    private KafkaProducer streamProducer;

    public ResponseEntity<String> handleRequest(String userId, String httpMethod, String api) {
        return ResponseEntity.ok("userId:" + userId + " Request to " + api + " successful");
    }

}
