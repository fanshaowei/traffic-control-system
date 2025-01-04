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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private static final int REQUEST_COUNT = 500;
    // 模拟的线程数
    private static final int THREAD_COUNT = 100;
    private static final String[] apis = {"/user/get", "/user/add", "/user/update"};

    @Test
    public void testHighConcurrency() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < REQUEST_COUNT; i++) {
            int finalI = i;
            executorService.submit(() -> {
                sendReq(finalI, "6");
                sendReq(finalI, "7");
                sendReq(finalI, "8");
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            Thread.sleep(90000);//等待统计结果
        }

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
}
