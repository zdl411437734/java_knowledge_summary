package com.zdltech.javaexercise.ssedemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@CrossOrigin
public class SseEmitterController {

    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        return SseEmitterServer.connect(userId);
    }

    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

}

