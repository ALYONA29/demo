package com.example.demo.domain;

import com.example.demo.service.LogService;
import com.example.demo.task.Task;

public class ExplorerRobot extends AbstractRobot {

    public ExplorerRobot() {
        super("Explorer");
    }

    @Override
    public void perform(Task task) {
        LogService.sendLog(getType() + " [" + getId() + "] starts exploring");
        task.execute(this);
        // креативность: имитируем анализ области
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        LogService.sendLog(getType() + " [" + getId() + "] finished exploring");
    }
}
