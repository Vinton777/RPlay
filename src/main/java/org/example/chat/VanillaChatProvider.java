package org.example.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanillaChatProvider implements ChatProvider {

    @Override
    public void sendGlobal(Component message) {
        Bukkit.getServer().sendMessage(message);
    }

    @Override
    public void sendLocal(Player source, double radius, Component message) {
        double radiusSquared = radius * radius;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(source.getWorld())) continue;
            if (player.getLocation().distanceSquared(source.getLocation()) > radiusSquared) continue;

            player.sendMessage(message);
        }
    }
}
