package com.example.demo.task;

import com.example.demo.domain.Robot;

public class BroadcastTask implements Task {
    private final Task innerTask;

    public BroadcastTask(Task innerTask) {
        this.innerTask = innerTask;
    }

    @Override
    public String getName() {
        return "Broadcast(" + innerTask.getName() + ")";
    }

    @Override
    public void execute(Robot robot) {
        // Ничего не делает у конкретного робота
    }

    public Task getInnerTask() {
        return innerTask;
    }
}
