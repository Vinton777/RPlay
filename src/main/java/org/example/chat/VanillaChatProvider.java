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
        for (Player player : Bukkit.getOnlinePlayers()) {
            scheduler.run(player, () -> player.sendMessage(message));
        }
    }

    @Override
    public void sendLocal(Player source, double radius, Component message) {
        if (radius < 0) return;
        double radiusSquared = radius * radius;
        var location = source.getLocation();
        var world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        for (Player player : Bukkit.getOnlinePlayers()) {
            scheduler.run(player, () -> {
                var recipientLocation = player.getLocation();
                if (!recipientLocation.getWorld().equals(world)) return;
                double dx = recipientLocation.getX() - x;
                double dy = recipientLocation.getY() - y;
                double dz = recipientLocation.getZ() - z;
                if (dx * dx + dy * dy + dz * dz <= radiusSquared) {
                    player.sendMessage(message);
                }
            });
        }
    }
}
