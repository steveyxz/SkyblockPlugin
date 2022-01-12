package com.partlysunny.core.player;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.util.DataUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatManager implements Listener {

    public static final Map<UUID, StatList> playerStats = new HashMap<>();

    public static void updatePlayer(Player player) {
        UUID id = player.getUniqueId();
        StatList newStats = BaseStatManager.getStatListOf(id);
        PlayerInventory inventory = player.getInventory();
        SkyblockItem helmet = DataUtils.getSkyblockItem(inventory.getHelmet(), player);
        SkyblockItem chestplate = DataUtils.getSkyblockItem(inventory.getChestplate(), player);
        SkyblockItem leggings = DataUtils.getSkyblockItem(inventory.getLeggings(), player);
        SkyblockItem boots = DataUtils.getSkyblockItem(inventory.getBoots(), player);
        SkyblockItem hand = DataUtils.getSkyblockItem(inventory.getItemInMainHand(), player);
        if (helmet != null) {
            newStats = newStats.merge(helmet.getCombinedStats());
        }
        if (chestplate != null) {
            newStats = newStats.merge(chestplate.getCombinedStats());
        }
        if (leggings != null) {
            newStats = newStats.merge(leggings.getCombinedStats());
        }
        if (boots != null) {
            newStats = newStats.merge(boots.getCombinedStats());
        }
        if (hand != null) {
            newStats = newStats.merge(hand.getCombinedStats());
        }
        playerStats.put(id, newStats);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        BaseStatManager.initializeStats(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        BaseStatManager.removeStats(event.getPlayer());
    }

}
