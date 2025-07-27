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

    @Autowired
    private RobotRegistry registry;

    @Autowired
    private TaskDispatcher dispatcher;

    // Каждые 5 секунд проверяем активность
    @Scheduled(fixedDelay = 5000)
    public void monitor() {
        List<AbstractRobot> all = registry.findAll();

        // Если ни одного Explorer нет — создаём
        if (registry.findByType("Explorer").isEmpty()) {
            registry.createRobot("Explorer");
        }
        // Если ни одного Cleaner нет — создаём
        if (registry.findByType("Cleaner").isEmpty()) {
            registry.createRobot("Cleaner");
        }

        // Если у какого-то робота очередь > threshold — дублируем
        all.forEach(r -> {
            if (r.getQueueSize() > QUEUE_THRESHOLD) {
                registry.createRobot(r.getType());
                LogService.sendLog("Spawned new " + r.getType() + " due to high load");
            }
        });

        // Пример: раз в минуту шлём self-destruct случайному
        if (Math.random() < 0.1 && !all.isEmpty()) {
            AbstractRobot victim = all.get((int) (Math.random() * all.size()));
            dispatcher.dispatchTo(victim.getId(), new KillYouselfTask());
        }
    }
}
