package org.example.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FoliaScheduler implements SchedulerService {

    private final JavaPlugin plugin;

    public FoliaScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run(Player player, Runnable task) {
        player.getScheduler().run(plugin, t -> task.run(), null);
    }

    @Override
    public void runGlobal(Runnable task) {
        Bukkit.getGlobalRegionScheduler().run(plugin, t -> task.run());
    }
}
