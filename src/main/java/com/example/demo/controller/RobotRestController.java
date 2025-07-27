package com.example.demo.controller;

import com.example.demo.service.RobotRegistry;
import com.example.demo.service.TaskDispatcher;
import com.example.demo.task.KillYouselfTask;
import com.example.demo.task.MoveTask;
import com.example.demo.task.Task;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotRestController {

    private final TaskDispatcher dispatcher;

    private final RobotRegistry registry;

    @Autowired
    public RobotRestController(TaskDispatcher dispatcher, RobotRegistry registry) {
        this.dispatcher = dispatcher;
        this.registry = registry;
    }


    @GetMapping
    public List<String> listRobots() {
        return registry.findAll().stream()
                .map(r -> r.getType() + "[" + r.getId() + "]")
                .toList();
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<Void> assignTask(@PathVariable String id, @RequestBody TaskRequest request) {
        Task task;

        if (request.getType().equals("KILL")) {
            task = new KillYouselfTask();
        } else {
            task = new MoveTask();
        }

        dispatcher.dispatchTo(id, task);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/broadcast")
    public ResponseEntity<Void> broadcastTask(@RequestBody TaskRequest request) {
        Task inner;

        if (request.getType().equals("KILL")) {
            inner = new KillYouselfTask();
        } else {
            inner = new MoveTask();
        }
        dispatcher.broadcast(inner);
        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    public static class TaskRequest {
        private String type;
    }
}
