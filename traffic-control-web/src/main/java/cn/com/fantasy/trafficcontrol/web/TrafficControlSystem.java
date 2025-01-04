package cn.com.fantasy.trafficcontrol.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"cn.com.fantasy.trafficcontrol"})
public class TrafficControlSystem {
    public static void main( String[] args ){
        SpringApplication.run(TrafficControlSystem.class, args);
    }
}
