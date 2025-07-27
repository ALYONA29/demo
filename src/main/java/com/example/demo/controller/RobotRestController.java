package com.example.demo.controller;

import com.example.demo.service.RobotRegistry;
import com.example.demo.service.TaskDispatcher;
import com.example.demo.task.KillYouselfTask;
import com.example.demo.task.MoveTask;
import com.example.demo.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotRestController {

    @Autowired
    private TaskDispatcher dispatcher;

    @Autowired
    private RobotRegistry registry;

    @GetMapping
    public List<String> listRobots() {
        return registry.findAll().stream()
                .map(r -> r.getType() + "[" + r.getId() + "]")
                .toList();
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<Void> assignTask(@PathVariable String id, @RequestBody TaskRequest request) {

        Task task = switch (request.getType()) {
            case "MOVE" -> new MoveTask();
            case "KILL" -> new KillYouselfTask();
            default -> new MoveTask();
        };
        dispatcher.dispatchTo(id, task);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/broadcast")
    public ResponseEntity<Void> broadcastTask(@RequestBody TaskRequest request) {
        Task inner = switch (request.getType()) {
            case "MOVE" -> new MoveTask();
            case "KILL" -> new KillYouselfTask();
            default -> new MoveTask();
        };
        dispatcher.broadcast(inner);
        return ResponseEntity.ok().build();
    }

    // Вспомогательный DTO
    public static class TaskRequest {
        private String type;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
