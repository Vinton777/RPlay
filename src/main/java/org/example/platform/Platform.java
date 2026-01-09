package org.example.platform;

import org.example.scheduler.SchedulerService;

public interface Platform {
    SchedulerService scheduler();
    String name();
}
