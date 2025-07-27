package com.example.demo.domain;

import com.example.demo.service.LogService;
import com.example.demo.task.Task;

public class CleanerRobot extends AbstractRobot {

    public CleanerRobot() {
        super("Cleaner");
    }

    @Override
    public void perform(Task task) {
        LogService.sendLog(getType() + " [" + getId() + "] starts cleaning");
        task.execute(this);
        // креативность: собираем "мусор"
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        LogService.sendLog(getType() + " [" + getId() + "] finished cleaning");
    }
}
