package org.example.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.example.scheduler.SchedulerService;

public class VanillaChatProvider implements ChatProvider {

    private final SchedulerService scheduler;

    public VanillaChatProvider(SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void sendGlobal(Component message) {
        scheduler.runGlobal(() -> Bukkit.broadcast(message));
    }

    @Override
    public void sendLocal(Player sender, double radius, Component message) {
        double sq = radius * radius;
        sender.getWorld().getPlayers().forEach(p ->
                scheduler.run(p, () -> {
                    if (p.getLocation().distanceSquared(sender.getLocation()) <= sq) {
                        p.sendMessage(message);
                    }
                })
        );
    }
}
