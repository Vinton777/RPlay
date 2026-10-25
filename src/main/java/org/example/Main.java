package org.example;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.chat.*;
import org.example.command.*;
import org.example.config.ConfigManager;
import org.example.platform.*;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Platform platform = PlatformDetector.detect(this);
        ConfigManager config = new ConfigManager(this);

        ChatProvider chat = new VanillaChatProvider(platform.scheduler());

        getCommand("me").setExecutor(new MeCommand(chat, config));
        getCommand("gme").setExecutor(new GMeCommand(chat, config));
        getCommand("do").setExecutor(new DoCommand(chat, config));
        getCommand("roll").setExecutor(new RollCommand(chat, config));
        getCommand("try").setExecutor(new TryCommand(chat, config));
        getCommand("rplay").setExecutor(new ReloadCommand(this, config));

        getLogger().info("RPlay enabled (" + platform.name() + ")");
    }
}
