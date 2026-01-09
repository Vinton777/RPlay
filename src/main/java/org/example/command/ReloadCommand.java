package org.example.command;

import org.example.config.ConfigManager;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final ConfigManager cfg;

    public ReloadCommand(JavaPlugin plugin, ConfigManager cfg) {
        this.plugin = plugin;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!s.hasPermission("rplay.reload")) {
            s.sendMessage(cfg.message("no-permission"));
            return true;
        }

        plugin.reloadConfig();
        s.sendMessage(cfg.message("reload"));
        return true;
    }
}
