package cn.com.fantasy.trafficcontrol.service.tokenbucket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName TokenBucket
 * @Description 令牌桶，根据时间周期动态生成token
 *
 * @Author fantasyfan
 * @Date 2025-01-02 3:25 p.m.
 */
@Data
@Slf4j
public class TokenBucket {
    /**
     * 令牌桶容量
     */
    private Long capacity;

    /**
     * 时间周期
     */
    private Long timeInterval;

    /**
     * 上次创建token时间
     */
    private Long lastCreateTimestamp;

    /**
     * 现存令牌数
     */
    private Long tokens;

    public TokenBucket(TrafficConfig trafficConfig) {
        this.capacity = trafficConfig.getQpsLimit();
        this.tokens = trafficConfig.getQpsLimit();
        this.timeInterval = trafficConfig.getRequestLimitInterval();
        // 初始化时，创建token时间为当前初始化时间
        this.lastCreateTimestamp = System.currentTimeMillis();
    }

    /**
     * 用户访问接口，消费token
     * @return
     */
    public synchronized Boolean consumeToken(){
        createTokens();
        if (this.tokens > 0){
            this.tokens --;
            return true;
        }
        return false;
    }

    /**
     * 根据时间周期生成token
     * 对比当前时间与上次生成token时间的间隔，占流量统计窗口的比例，计算需要补充的token数量
     */
    private void createTokens(){
        long current = System.currentTimeMillis();
        // 距离上次填充token的时间间隔
        long passTime = current - lastCreateTimestamp;
        // 根据时间间隔周期，计算需要填充的token数
        long newTokens = (passTime / this.timeInterval) * this.capacity;
        if(newTokens > 0) {
            tokens = Math.min(this.capacity, this.tokens + newTokens);
            lastCreateTimestamp = current;
        }
    }
}
