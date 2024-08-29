package org.github.komnazsk.kmmoveassist;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.HashMap;


/**
 * This class listens for player interaction events and handles the movement
 */
public class MoveClickListener implements Listener {
    private static final int MAX_MOVE_DISTANCE = 160;
    private static final int UNIT_MOVE_DISTANCE = 20;

    private Material moveItem;
    private HashMap<Player, Integer> playerMoveDistances = new HashMap<>();

    public MoveClickListener(Material moveItem) {
        this.moveItem = moveItem;
    }

    /**
     * Called when a player interacts
     * <p>
     * Handles right-click and left-click events to trigger movement or adjust movement distance.
     * </p>
     * @param event The player interaction event.
     */
    @EventHandler
    public void onPlayerInteact(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // For non-hand player interaction events (e.g. off-hand), do nothing
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        // If the player doesn't have a move item, do nothing
        if (player.getInventory().getItemInMainHand().getType() != this.moveItem) {
            return;
        }

        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
                // Fall Through
            case RIGHT_CLICK_BLOCK:
                // If the player doesn't have move permission, do nothing
                if (MovePermission.hasMovePermission(player)) {
                    event.setCancelled(true);
                    moveLocation(player);
                }
                break;
            case LEFT_CLICK_BLOCK:
                // Fall Through
            case LEFT_CLICK_AIR:
                if (MovePermission.hasSetDistancePermission(player)) {
                    event.setCancelled(true);
                    setDistance(player);
                }
                break;
            default:
                // No Operation
                break;
        }
    }

    /**
     * Called when a player quits the server
     * This method removes the player's move distance from the tracking map to clean up resources.
     *
     * @param event The PlayerQuitEvent that occurs when a player leaves the server.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerMoveDistances.remove(player);
    }

    /**
     * Teleports the player a specified distance in the direction they are facing.
     * <p>
     * If the destination is blocked, the player is moved to the nearest open space
     * above the blockage.
     * </p>
     * @param player The player to be teleported.
     */
    private void moveLocation(Player player) {
        int moveDistance = playerMoveDistances.getOrDefault(player, UNIT_MOVE_DISTANCE);
        // Obtain a unit vector (i.e., a one-block vector) in the player's line-of-sight direction.
        Vector direction = player.getEyeLocation().getDirection().normalize();
        // Scalar multiply the vector value by the moveDistance value.
        Vector destination = direction.multiply(moveDistance);
        Location startLocation = player.getLocation();
        Location targetLocation = startLocation.clone().add(destination);

        // If the destination is an abyss, set the minimum coordinates
        if (targetLocation.getY() < player.getWorld().getMinHeight()) {
            targetLocation.setY(player.getWorld().getMinHeight());
        }

        // If the destination is a block, check if the area directly above it is vacant.
        Block blockAtTarget = targetLocation.getBlock();
        while (!blockAtTarget.isPassable()) {
            // Add Y Axis
            targetLocation.add(0, 1, 0);
            blockAtTarget = targetLocation.getBlock();
        }
        // Teleport the player
        player.teleport(targetLocation);
    }

    /**
     * Adjusts the move distance by doubling it each time the player left-clicks.
     * <p>
     * If the distance exceeds the maximum allowed, it resets to the default unit value.
     * </p>
     * @param player The player whose move distance is being adjusted.
     */
    private void setDistance(Player player) {
        int moveDistance = playerMoveDistances.getOrDefault(player, UNIT_MOVE_DISTANCE);

        // double the current distance
        moveDistance *= 2;
        if (moveDistance > MAX_MOVE_DISTANCE) {
            // If the maximum value is exceeded, return to unit value
            moveDistance = UNIT_MOVE_DISTANCE;
        }
        playerMoveDistances.put(player, moveDistance);
        player.sendMessage("[KMMoveAssist] Move Distance: " + moveDistance + " [bLock]");
    }
}
