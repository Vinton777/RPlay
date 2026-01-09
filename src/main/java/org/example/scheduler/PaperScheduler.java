package org.example.scheduler;

import org.bukkit.entity.Player;

public class PaperScheduler implements SchedulerService {

    @Override
    public void run(Player player, Runnable task) {
        task.run();
    }

    @Override
    public void runGlobal(Runnable task) {
        task.run();
    }
}
