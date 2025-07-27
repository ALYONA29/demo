package com.example.demo.task;

import com.example.demo.domain.Robot;

public interface Task {
    String getName();
    void execute(Robot robot);
}
