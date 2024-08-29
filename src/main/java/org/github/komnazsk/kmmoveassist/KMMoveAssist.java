package org.github.komnazsk.kmmoveassist;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class for the KMMoveAssist plugin.
 */
public final class KMMoveAssist extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     * This method sets up the necessary configurations, permissions, and event listeners for the plugin.
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        // Setup confog
        ConfigLoader.saveDefaultConfig(this);
        Material moveItem = ConfigLoader.getMoveItemMaterial(this);
        // Setup move permission
        MovePermission.setupPermission(this);
        // Register Listener
        MoveClickListener moveClickListener = new MoveClickListener(moveItem);
        getServer().getPluginManager().registerEvents(moveClickListener, this);
    }

    /**
     * Called when the plugin is disabled. Any necessary cleanup should be performed here.
     */
    @Override
    public void onDisable() {
        // No operation
    }

}
