package cn.com.fantasy.trafficcontrol.service.tokenbucket;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserTokenBucketManage
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-02 3:42 p.m.
 */
@Configuration
public class UserTokenBucketManage {
    @Getter
    private ConcurrentHashMap<String, TokenBucket> userTokenBuckets = new ConcurrentHashMap<String, TokenBucket>();

    @Autowired
    private TrafficConfig trafficConfig;

    private TokenBucket getTokenBucket() {
        return new TokenBucket(trafficConfig);
    }

    /**
     * 处理用户请求
     * @param userId
     * @return
     */
    public Boolean allowRequest(String userId){
        TokenBucket bucket = userTokenBuckets.computeIfAbsent(userId, k -> getTokenBucket());
        boolean b = bucket.consumeToken();
        return b;
    }

}
