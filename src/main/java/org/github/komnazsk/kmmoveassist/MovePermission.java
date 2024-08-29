package org.github.komnazsk.kmmoveassist;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class handles the permission system for the KMMoveAssist plugin.
 * It provides methods to set up the required permissions and to check
 * if a player has the necessary permission to use movement-related features.
 */
public class MovePermission {
    private static final String MOVE_PERMISSION_STRING = "kmmoveassist.move";
    private static final String SET_DISTANCE_PERMISSION_STRING = "kmmoveassist.setdistance";

    /**
     * Sets up the move permission for the plugin.
     * <p>
     * This method should be called during the plugin's initialization
     *  to register the necessary permission with the server.
     * </p>
     * @param plugin The instance of the JavaPlugin that is registering the permission.
     */
    public static void setupPermission(JavaPlugin plugin) {
        if (plugin != null) {
            Permission movePermission = new Permission(MOVE_PERMISSION_STRING);
            Permission changeDirectionPermission = new Permission(SET_DISTANCE_PERMISSION_STRING);

            movePermission.setDefault(org.bukkit.permissions.PermissionDefault.OP);
            changeDirectionPermission.setDefault(org.bukkit.permissions.PermissionDefault.OP);

            plugin.getServer().getPluginManager().addPermission(movePermission);
            plugin.getServer().getPluginManager().addPermission(changeDirectionPermission);
        } else {
            throw new RuntimeException("Cannot set permission: plugin object is null");
        }
    }

    /**
     * Checks if the specified player has the move permission.
     *
     * @param player The player whose permission is being checked.
     * @return true if the player has the move permission, false otherwise.
     */
    public static boolean hasMovePermission(Player player) {
        if (!player.hasPermission(MOVE_PERMISSION_STRING)) {
            player.sendMessage( player.getDisplayName() +
                    " does not have permission: " + MOVE_PERMISSION_STRING);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the specified player has the set distance permission.
     *
     * @param player The player whose permission is being checked.
     * @return true if the player has the set distance permission, false otherwise.
     */
    public static boolean hasSetDistancePermission(Player player) {
        if (!player.hasPermission(SET_DISTANCE_PERMISSION_STRING)) {
            player.sendMessage( player.getDisplayName() +
                    " does not have permission: " + SET_DISTANCE_PERMISSION_STRING);
            return false;
        } else {
            return true;
        }
    }
}
