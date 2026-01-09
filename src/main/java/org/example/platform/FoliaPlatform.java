package org.example.platform;

import org.example.scheduler.*;
import org.bukkit.plugin.java.JavaPlugin;

public class FoliaPlatform implements Platform {

    private final SchedulerService scheduler;

    public FoliaPlatform(JavaPlugin plugin) {
        this.scheduler = new FoliaScheduler(plugin);
    }

    @Override public SchedulerService scheduler() { return scheduler; }
    @Override public String name() { return "Folia"; }
}
