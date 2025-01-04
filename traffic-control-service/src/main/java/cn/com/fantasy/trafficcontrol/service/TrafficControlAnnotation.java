package cn.com.fantasy.trafficcontrol.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName TrafficcontrolAnnotation
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-04 2:50 a.m.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrafficControlAnnotation {
}
