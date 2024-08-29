package org.github.komnazsk.kmmoveassist;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * This class handles the configuration loading for the KMMoveAssist plugin.
 */
public class ConfigLoader {
    private static final String CONFIG_KEY_MOVE_ITEM_STRING = "move-item";
    private static final String DEFAULT_MOVE_ITEM_STRING = "CLOCK";
    private static final Material DEFAULT_MOVE_ITEM_MATERIAL = Material.CLOCK;

    /**
     * Saves the default configuration settings for the plugin.
     *
     * @param plugin The plugin instance for which the configuration is being saved.
     */
    public static void saveDefaultConfig(Plugin plugin) {
        // Loads the plugin configuration
        FileConfiguration config = plugin.getConfig();
        config.addDefault(CONFIG_KEY_MOVE_ITEM_STRING, DEFAULT_MOVE_ITEM_STRING);
        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }

    /**
     * Retrieves the move item material from the plugin's configuration.
     * If the configuration value is invalid or not set, the default material (CLOCK) is returned.
     *
     * @param plugin The plugin instance from which the configuration is being loaded.
     * @return The Material representing the move item configured in the plugin's config file.
     */
    public static Material getMoveItemMaterial(Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        // Get move item name
        String moveItemName = config.getString(CONFIG_KEY_MOVE_ITEM_STRING, DEFAULT_MOVE_ITEM_STRING);
        // Get move item material
        Material moveItemMaterial = Material.matchMaterial(moveItemName.toUpperCase());
        // If the material is invalid, the default item (CLOCK) is used.
        if ((moveItemMaterial == null) || !moveItemMaterial.isItem()) {
            plugin.getLogger().warning(CONFIG_KEY_MOVE_ITEM_STRING +
                    " is invalid. set default item: " + DEFAULT_MOVE_ITEM_STRING);
            moveItemMaterial = DEFAULT_MOVE_ITEM_MATERIAL;
        }
        return moveItemMaterial;
    }
}
