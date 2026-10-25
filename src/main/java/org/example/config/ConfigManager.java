package org.example.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /* ===== Language ===== */

    public String lang() {
        return plugin.getConfig().getString("language", "en");
    }

    /* ===== Formats ===== */

    public Component format(String key, Map<String, String> values) {
        String raw = plugin.getConfig().getString("formats." + key, "");
        TagResolver.Builder placeholders = TagResolver.builder();
        for (var e : values.entrySet()) {
            String tag = "rplay_" + e.getKey().replace('-', '_');
            raw = raw.replace("{" + e.getKey() + "}", "<" + tag + ">");
            placeholders.resolver(Placeholder.unparsed(tag, e.getValue()));
        }
        return mm.deserialize(raw, placeholders.build());
    }

    /* ===== Messages ===== */

    public Component message(String key) {
        String raw = plugin.getConfig().getString(
                "messages." + lang() + "." + key, ""
        );
        return mm.deserialize(raw);
    }

    public String text(String key) {
        return plugin.getConfig().getString(
                "messages." + lang() + "." + key, ""
        );
    }

    /* ===== Settings ===== */

    public double localRadius() {
        return plugin.getConfig().getDouble("radius.local", 200);
    }

    public String rollMode() {
        return plugin.getConfig().getString("command-setting.roll", "global");
    }

    public double rollLocalRadius() {
        return plugin.getConfig().getDouble("command-setting.roll-local-radius", 200);
    }
}
