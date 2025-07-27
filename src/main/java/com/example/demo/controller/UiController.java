package com.example.demo.controller;

import com.example.demo.service.LogService;
import com.example.demo.service.RobotRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class UiController {

    @Autowired
    private RobotRegistry registry;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("robots", registry.findAll());
        return "index";
    }

    @GetMapping(value = "/stream/logs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamLogs() {
        return LogService.subscribe();
    }
}
