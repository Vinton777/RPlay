package org.example.command;

import org.example.chat.ChatProvider;
import org.example.config.ConfigManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class MeCommand implements CommandExecutor {

    private final ChatProvider chat;
    private final ConfigManager cfg;

    public MeCommand(ChatProvider chat, ConfigManager cfg) {
        this.chat = chat;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!(s instanceof Player p) || a.length == 0) return true;

        chat.sendLocal(p, cfg.localRadius(),
                cfg.format("me", Map.of(
                        "player", p.getName(),
                        "message", String.join(" ", a)
                ))
        );
        return true;
    }
}
