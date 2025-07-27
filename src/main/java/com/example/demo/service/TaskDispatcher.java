package com.example.demo.service;

import com.example.demo.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDispatcher {

    private final RobotRegistry registry;

    @Autowired
    public TaskDispatcher(RobotRegistry registry) {
        this.registry = registry;
    }

    public void dispatchTo(String robotId, Task task) {
        registry.get(robotId).ifPresent(r -> r.assign(task));
    }

    public void broadcast(Task task) {
        registry.findAll().forEach(r -> r.assign(task));
    }
}
