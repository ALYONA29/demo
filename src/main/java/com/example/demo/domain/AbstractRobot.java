package com.example.demo.domain;

import com.example.demo.service.LogService;
import com.example.demo.service.RobotRegistry;
import com.example.demo.task.Task;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRobot implements Robot, Runnable {
    private final String id = UUID.randomUUID().toString();
    private final String type;
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
    private volatile boolean active = true;

    protected AbstractRobot(String type) {
        this.type = type;
        Thread thread = new Thread(this, "Robot-" + type + "-" + id);
        thread.setDaemon(true);
        thread.start();
        LogService.sendLog(type + " [" + id + "] created");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void assign(Task task) {
        queue.offer(task);
        LogService.sendLog(type + " [" + id + "] assigned task: " + task.getName());
    }

    @Override
    public void shutdown() {
        active = false;
        LogService.sendLog(type + " [" + id + "] shutting down");
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public void run() {
        try {
            while (active) {
                Task task = queue.poll(1, TimeUnit.SECONDS);
                if (task != null) {
                    perform(task);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            RobotRegistry.remove(this);
            LogService.sendLog(type + " [" + id + "] removed from registry");
        }
    }
}
