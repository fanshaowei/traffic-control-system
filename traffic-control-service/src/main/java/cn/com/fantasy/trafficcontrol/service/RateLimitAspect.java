package cn.com.fantasy.trafficcontrol.service;

import cn.com.fantasy.trafficcontrol.service.kafka.KafkaProducer;
import cn.com.fantasy.trafficcontrol.service.tokenbucket.UserTokenBucketManage;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName RateLimitAspect
 * @Description 注解在对应的请求方法上，做用户流量统计拦截
 * @Author fantasyfan
 * @Date 2025-01-04 2:58 a.m.
 */
@Aspect
@Component
public class RateLimitAspect {
    @Autowired
    private UserTokenBucketManage userTokenBucketManage;

    @Autowired
    private KafkaProducer streamProducer;

    @Around("@annotation(annotation)")
    public Object around(ProceedingJoinPoint joinPoint, TrafficControlAnnotation annotation) throws Throwable {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userId = request.getParameter("userId");
            String requestUri = request.getRequestURI();
            String httpMethod = request.getMethod();

            if (userId == null) {
                throw new RuntimeException("Missing userId");
            }

            boolean b = userTokenBucketManage.allowRequest(userId);
            if(b) {
                streamProducer.sendMsg(userId, httpMethod + ": " + requestUri);
                return joinPoint.proceed();
            }else{
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                        .body("userId:" + userId + " Request limit exceeded. Please try again later.");
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }

    }
}
