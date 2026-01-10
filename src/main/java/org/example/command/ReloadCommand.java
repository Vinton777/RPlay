package org.example.command;

import org.example.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final ConfigManager cfg;

    public ReloadCommand(JavaPlugin plugin, ConfigManager cfg) {
        this.plugin = plugin;
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(cfg.message("usage-rplay"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("rplay.reload")) {
                sender.sendMessage(cfg.message("no-permission"));
                return true;
            }

            plugin.reloadConfig();
            sender.sendMessage(cfg.message("reload"));
            return true;
        }

        sender.sendMessage(cfg.message("usage-rplay"));
        return true;
    }
}
