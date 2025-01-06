package cn.com.fantasy.trafficcontrol.service;

import cn.com.fantasy.trafficcontrol.service.kafka.KafkaProducer;
import cn.com.fantasy.trafficcontrol.service.tokenbucket.UserTokenBucketManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @ClassName HandleRequestService
 * @Description 服务处理入口
 * @Author fantasyfan
 * @Date 2025-01-03 12:19 p.m.
 */
@Service
public class HandleRequestService {
    public ResponseEntity<String> getUser(String userId, String httpMethod, String api) {
        Random random = new Random();
        try {
            //模拟逻辑耗时
            Thread.sleep(random.nextInt(20)+10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("userId:" + userId + " Request to " + api + " successful");
    }

    public ResponseEntity<String> addUser(String userId, String httpMethod, String api)  {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(30)+10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("userId:" + userId + " Request to " + api + " successful");
    }

    public ResponseEntity<String> updateUser(String userId, String httpMethod, String api) {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(40)+10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("userId:" + userId + " Request to " + api + " successful");
    }

}
