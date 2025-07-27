package com.example.demo.service;

import com.example.demo.task.BroadcastTask;
import com.example.demo.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDispatcher {

    @Autowired
    private RobotRegistry registry;

    public void dispatchTo(String robotId, Task task) {
        registry.get(robotId).ifPresent(r -> r.assign(task));
    }

    public void broadcast(Task task) {
        registry.findAll().forEach(r -> r.assign(task));
    }

    public void dispatchBroadcastTask(BroadcastTask broadcastTask) {
        Task inner = broadcastTask.getInnerTask();
        broadcast(inner);
    }
}
