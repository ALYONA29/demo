package com.example.demo.domain;

import com.example.demo.task.Task;

public interface Robot {
    String getId();
    String getType();
    void assign(Task task);
    void perform(Task task);
    void shutdown();
    int getQueueSize();
}
