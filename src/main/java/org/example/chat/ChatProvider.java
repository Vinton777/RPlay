package org.example.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface ChatProvider {
    void sendGlobal(Component message);
    void sendLocal(Player sender, double radius, Component message);
}
