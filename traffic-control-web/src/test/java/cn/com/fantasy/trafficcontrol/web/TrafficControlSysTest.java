package cn.com.fantasy.trafficcontrol.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TrafficControlSysTest
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-04 1:04 a.m.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TrafficControlSysTest {
    @Autowired
    private MockMvc mockMvc;

    // 模拟的并发请求数
    private static final int REQUESTS_PER_SECOND = 500;
    // 模拟用户数
    private static final int THREAD_COUNT = 100;

    private static final int TEST_TOTAL_SECONDS = 90000;
    private static final String[] apis = {"/user/get", "/user/add", "/user/update"};

    @Test
    public void testHighConcurrency() throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        //每秒发送500个请求
        scheduler.scheduleAtFixedRate(()->{
            for (int i = 0; i < REQUESTS_PER_SECOND; i++) {
                int finalI = i;
                executorService.submit(() -> {
                    sendReq(finalI, "1");//user1
                    sendReq(finalI, "2");//user2
                    sendReq(finalI, "3");//user3
                    sendReq(finalI, "4");//user4
                });
            }
        }, 0,1, TimeUnit.SECONDS);

        //等待统计结果
        Thread.sleep(TEST_TOTAL_SECONDS);

        //关闭线程池
        elegantShutdown(scheduler, executorService);

        System.out.println("All requests completed.");
    }

    private void sendReq(int threadNo, String userId){
        try {
            // 随机获取请求接口
            Random random = new Random();
            String api = apis[random.nextInt(apis.length)];

            // 发送请求到端点
            MvcResult result = null;
            switch (api){
                case "/user/get":
                    api += "?userId=" + userId;
                    result = mockMvc.perform(MockMvcRequestBuilders.get(api))
                            .andReturn();
                    break;
                case "/user/add":
                    api += "?userId=" + userId;
                    result = mockMvc.perform(MockMvcRequestBuilders.post(api))
                            .andReturn();
                    break;
                case "/user/update":
                    api += "?userId=" + userId;
                    result = mockMvc.perform(MockMvcRequestBuilders.put(api))
                            .andReturn();
                    break;
            }

            int status = result.getResponse().getStatus();
            String response = result.getResponse().getContentAsString();
            if (status != HttpStatus.OK.value()) {
                System.err.println("Thread " + threadNo + " Non-200 Response: Status = " + status + ", Response = " + response);
            } else {
                System.out.println("Thread " + threadNo + " Success Response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void elegantShutdown(ScheduledExecutorService scheduler, ExecutorService executorService){
        // 添加钩子以优雅关闭线程池
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            executorService.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                executorService.shutdownNow();
            }
        }));
    }
}
