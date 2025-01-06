package cn.com.fantasy.trafficcontrol.web.controller;

import cn.com.fantasy.trafficcontrol.service.HandleRequestService;
import cn.com.fantasy.trafficcontrol.service.TrafficControlAnnotation;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author fantasyfan
 * @Date 2025-01-03 12:11 p.m.
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private HandleRequestService service;

    @TrafficControlAnnotation
    @GetMapping("/get")
    public ResponseEntity<String> getUser(@PathParam("userId") String userId){
        return service.getUser(userId, RequestMethod.GET.name(), "/user/get");
    }

    @TrafficControlAnnotation
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@PathParam("userId") String userId){
        return service.addUser(userId, RequestMethod.POST.name(), "/user/add");
    }

    @TrafficControlAnnotation
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@PathParam("userId") String userId){
        return service.updateUser(userId, RequestMethod.PUT.name(), "/user/update");
    }
}
