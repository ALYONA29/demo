package com.example.demo.service;

import com.example.demo.domain.AbstractRobot;
import com.example.demo.domain.CleanerRobot;
import com.example.demo.domain.ExplorerRobot;
import com.example.demo.domain.Robot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RobotRegistry {
    private static final Map<String, AbstractRobot> robots = new ConcurrentHashMap<>();

    public static void register(AbstractRobot robot) {
        robots.put(robot.getId(), robot);
    }

    public static void remove(Robot robot) {
        robots.remove(robot.getId());
    }

    public Optional<AbstractRobot> get(String id) {
        return Optional.ofNullable(robots.get(id));
    }

    public List<AbstractRobot> findAll() {
        return new ArrayList<>(robots.values());
    }

    public List<AbstractRobot> findByType(String type) {
        return robots.values().stream()
                .filter(r -> r.getType().equals(type))
                .collect(Collectors.toList());
    }

    public AbstractRobot createRobot(String type) {
        AbstractRobot robot;
        switch (type) {
            case "Cleaner":
                robot = new CleanerRobot();
                break;
            case "Explorer":
            default:
                robot = new ExplorerRobot();
                break;
        }
        register(robot);
        return robot;
    }
}
