package org.example.platform;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlatformDetector {

    public static Platform detect(JavaPlugin plugin) {
        if (Bukkit.getServer().getName().equalsIgnoreCase("Folia")) {
            return new FoliaPlatform(plugin);
        }
        return new PaperPlatform(plugin);
    }
}
