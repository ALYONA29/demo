package com.example.demo.task;

import com.example.demo.domain.Robot;
import com.example.demo.service.LogService;

import java.util.Random;

public class MoveTask implements Task {
    private static final String[] DIRECTIONS = {"NORTH", "SOUTH", "WEST", "EAST"};
    private final String direction;

    public MoveTask() {
        this.direction = DIRECTIONS[new Random().nextInt(DIRECTIONS.length)];
    }

    @Override
    public String getName() {
        return "Move to " + direction;
    }

    @Override
    public void execute(Robot robot) {
        LogService.sendLog(robot.getType() + " [" + robot.getId() + "] moves " + direction);
    }
}
