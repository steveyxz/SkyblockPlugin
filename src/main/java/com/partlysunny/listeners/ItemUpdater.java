package com.partlysunny.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.partlysunny.listeners.ItemTracker.items;

public class ItemUpdater implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent e) {
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent e) {
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        updateInventory(e.getPlayer().getInventory());
    }

    private void updateInventory(Inventory inv) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (!nbti.hasKey("sb_unique_id")) {
                    continue;
                }
                UUID sb_unique_id = nbti.getUUID("sb_unique_id");
                if (sb_unique_id == null) {
                    continue;
                }
                if (!items.containsKey(sb_unique_id)) {
                    ItemTracker.registerItem(i);
                    continue;
                }
                ItemStack skyblockItem = items.get(sb_unique_id).getSkyblockItem();
                if (!new NBTItem(skyblockItem).equals(new NBTItem(i))) {
                    inv.setItem(count, skyblockItem);
                }
            }
            count++;
        }
    }


}
