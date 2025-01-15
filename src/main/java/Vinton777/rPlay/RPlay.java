package Vinton777.rPlay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

public class RPlay extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();

        this.getCommand("me").setExecutor(new MeCommand());
        this.getCommand("gme").setExecutor(new GMeCommand());
        this.getCommand("do").setExecutor(new DoCommand());
        this.getCommand("roll").setExecutor(new RollCommand());
        this.getCommand("try").setExecutor(new TryCommand());
        this.getCommand("rplay").setExecutor(new ReloadCommand());
    }

    private String translateColorCodes(String message) {
        return message.replace("&", "§");
    }

    private String applyFormat(String key, Player player, String message, int result, boolean success) {
        String lang = config.getString("language", "ru");
        String format = translateColorCodes(config.getString("messages." + lang + "." + key, ""));
        return format.replace("{player}", player.getName())
                .replace("{message}", message != null ? message : "")
                .replace("{result}", String.valueOf(result))
                .replace("{success}", success ? "Удачно!" : "Неудачно!");
    }

    class MeCommand implements CommandExecutor {
        private static final double RADIUS = 200.0;

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String message = String.join(" ", args);
                Location playerLocation = player.getLocation();
                String format = translateColorCodes(config.getString("local-prefix", "&6| &r{player}&8: &f{message}"));
                String formattedMessage = applyFormat("me", player, message, 0, false);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getWorld().equals(player.getWorld()) && onlinePlayer.getLocation().distance(playerLocation) <= RADIUS) {
                        onlinePlayer.sendMessage(format.replace("{player}", player.getName()).replace("{message}", message));
                    }
                }
                return true;
            }
            return false;
        }
    }

    class GMeCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String message = String.join(" ", args);
                String format = translateColorCodes(config.getString("global-prefix", "&6| &r{player}&8: &f{message}"));
                String formattedMessage = applyFormat("gme", player, message, 0, false);

                Bukkit.broadcastMessage(format.replace("{player}", player.getName()).replace("{message}", message));
                return true;
            }
            return false;
        }
    }

    class DoCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String message = String.join(" ", args);
                String formattedMessage = applyFormat("do", player, message, 0, false);

                player.getServer().broadcastMessage(formattedMessage);
                return true;
            }
            return false;
        }
    }

    class RollCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Random random = new Random();
                int result = random.nextInt(100) + 1;
                String formattedMessage = applyFormat("roll", player, null, result, false);

                player.getServer().broadcastMessage(formattedMessage);
                return true;
            }
            return false;
        }
    }

    class TryCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String message = String.join(" ", args);
                Random random = new Random();
                boolean success = random.nextBoolean();
                String formattedMessage = applyFormat(success ? "try-success" : "try-fail", player, message, 0, success);

                player.getServer().broadcastMessage(formattedMessage);
                return true;
            }
            return false;
        }
    }

    class ReloadCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.hasPermission("rplay.reload")) {
                reloadConfig();
                config = getConfig();
                sender.sendMessage(ChatColor.GREEN + "Конфигурация плагина RPlay успешно перезагружена!");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "У вас нет прав для выполнения этой команды.");
                return true;
            }
        }
    }
}
