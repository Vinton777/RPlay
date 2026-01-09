package org.example.command;

import org.example.chat.ChatProvider;
import org.example.config.ConfigManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TryCommand implements CommandExecutor {

    private final ChatProvider chat;
    private final ConfigManager cfg;

    public TryCommand(ChatProvider chat, ConfigManager cfg) {
        this.chat = chat;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!(s instanceof Player p) || a.length == 0) return true;

        boolean success = ThreadLocalRandom.current().nextBoolean();

        chat.sendGlobal(cfg.format(
                success ? "try-success" : "try-fail",
                Map.of(
                        "player", p.getName(),
                        "message", String.join(" ", a),
                        "try_success", cfg.text("try-success-text"),
                        "try_fail", cfg.text("try-fail-text")
                )
        ));
        return true;
    }
}
