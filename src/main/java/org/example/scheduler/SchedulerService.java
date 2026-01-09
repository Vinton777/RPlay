package org.example.scheduler;

import org.bukkit.entity.Player;

public interface SchedulerService {
    void run(Player player, Runnable task);
    void runGlobal(Runnable task);
}
