package com.partlysunny.listeners;

import com.partlysunny.items.custom.SkyblockItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemTracker implements Listener {

    public static final Map<UUID, SkyblockItem> items = new HashMap<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        int count = 0;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (!nbti.hasKey("sb_unique_id")) {
                    ItemStack ided = addID(i);
                    e.getInventory().setItem(count, ided);
                    if (!nbti.getBoolean("vanilla")) {
                        items.put(nbti.getUUID("sb_unique_id"), SkyblockItem.getItemFrom(ided));
                    }
                }
                count++;
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        int count = 0;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (!nbti.hasKey("sb_unique_id")) {
                    ItemStack ided = addID(i);
                    e.getInventory().setItem(count, ided);
                    if (!nbti.getBoolean("vanilla")) {
                        items.put(nbti.getUUID("sb_unique_id"), SkyblockItem.getItemFrom(ided));
                    }
                }
                count++;
            }
        }
    }

    private ItemStack addID(ItemStack item) {
        NBTItem i = new NBTItem(item);
        i.setUUID("sb_unique_id", UUID.randomUUID());
        return i.getItem();
    }

}
