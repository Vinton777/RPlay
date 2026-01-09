package org.example.command;

import org.example.chat.ChatProvider;
import org.example.config.ConfigManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class DoCommand implements CommandExecutor {

    private final ChatProvider chat;
    private final ConfigManager cfg;

    public DoCommand(ChatProvider chat, ConfigManager cfg) {
        this.chat = chat;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!(s instanceof Player p) || a.length == 0) return true;

        chat.sendGlobal(cfg.format("do", Map.of(
                "player", p.getName(),
                "message", String.join(" ", a)
        )));
        return true;
    }
}
