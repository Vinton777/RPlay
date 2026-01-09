package org.example.command;

import org.example.chat.ChatProvider;
import org.example.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RollCommand implements CommandExecutor {

    private final ChatProvider chat;
    private final ConfigManager cfg;

    public RollCommand(ChatProvider chat, ConfigManager cfg) {
        this.chat = chat;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!(s instanceof Player p)) return true;

        int max = 100;
        if (a.length > 0) {
            try {
                max = Integer.parseInt(a[0]);
            } catch (NumberFormatException e) {
                p.sendMessage(cfg.message("usage-roll"));
                return true;
            }
        }

        int result = ThreadLocalRandom.current().nextInt(1, max + 1);

        Component msg = cfg.format("roll", Map.of(
                "player", p.getName(),
                "result", String.valueOf(result),
                "roll_text", cfg.text("roll-text")
        ));

        if (cfg.rollMode().equalsIgnoreCase("local")) {
            chat.sendLocal(p, cfg.rollLocalRadius(), msg);
        } else {
            chat.sendGlobal(msg);
        }
        return true;
    }
}
