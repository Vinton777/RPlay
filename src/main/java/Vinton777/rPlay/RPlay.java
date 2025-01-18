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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class RPlay extends JavaPlugin {

    private FileConfiguration config;
    private static final String CURRENT_VERSION = "1.1.0"; // Текущая версия вашего плагина
    private static final String UPDATE_URL = "https://api.github.com/repos/Vinton777/RPlay/releases/latest"; // URL для проверки обновлений

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

        getLogger().info("RPlay plugin enabled!");
        checkForUpdates(); // Вызов метода для проверки обновлений при включении плагина
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
                int max = 100; // По умолчанию до 100
                if (args.length > 0) {
                    try {
                        max = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number.");
                        return false;
                    }
                }
                Random random = new Random();
                int result = random.nextInt(max) + 1;
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

    private void checkForUpdates() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(UPDATE_URL).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            String latestVersion = parseVersion(content.toString());
            if (!CURRENT_VERSION.equals(latestVersion)) {
                getLogger().info("A new version of RPlay is available: " + latestVersion);
            } else {
                getLogger().info("RPlay is up to date.");
            }
        } catch (Exception e) {
            getLogger().severe("Failed to check for updates: " + e.getMessage());
        }
    }

    private String parseVersion(String jsonResponse) {
        String versionKey = "\"tag_name\":\"";
        int index = jsonResponse.indexOf(versionKey) + versionKey.length();
        return jsonResponse.substring(index, jsonResponse.indexOf("\"", index));
    }
}

