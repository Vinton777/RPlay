package org.example.platform;

import org.example.scheduler.*;

public class PaperPlatform implements Platform {

    private final SchedulerService scheduler = new PaperScheduler();

    public PaperPlatform(Object plugin) {}

    @Override public SchedulerService scheduler() { return scheduler; }
    @Override public String name() { return "Paper"; }
}
