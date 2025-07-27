package com.example.demo.task;

import com.example.demo.domain.Robot;
import com.example.demo.service.LogService;

public class KillYouselfTask implements Task {

    @Override
    public String getName() {
        return "SelfDestruct";
    }

    @Override
    public void execute(Robot robot) {
        LogService.sendLog(robot.getType() + " [" + robot.getId() + "] received self-destruct order");
        robot.shutdown();
    }
}
