package com.example.demo.service;

import com.example.demo.domain.AbstractRobot;
import com.example.demo.task.KillYouselfTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityTracker {

    private static final int QUEUE_THRESHOLD = 5;

    private final RobotRegistry registry;

    private final TaskDispatcher dispatcher;

    @Autowired
    public ActivityTracker(RobotRegistry registry, TaskDispatcher dispatcher) {
        this.registry = registry;
        this.dispatcher = dispatcher;
    }

    @Scheduled(fixedDelay = 5000)
    public void monitor() {
        List<AbstractRobot> all = registry.findAll();

        if (registry.findByType("Explorer").isEmpty()) {
            registry.createRobot("Explorer");
        }
        if (registry.findByType("Cleaner").isEmpty()) {
            registry.createRobot("Cleaner");
        }

        all.forEach(r -> {
            if (r.getQueueSize() > QUEUE_THRESHOLD) {
                registry.createRobot(r.getType());
                LogService.sendLog("Spawned new " + r.getType() + " due to high load");
            }
        });

        if (Math.random() < 0.1 && !all.isEmpty()) {
            AbstractRobot victim = all.get((int) (Math.random() * all.size()));
            dispatcher.dispatchTo(victim.getId(), new KillYouselfTask());
        }
    }
}
