package com.partlysunny.listeners;

import com.partlysunny.items.custom.SkyblockItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemTracker implements Listener {

    public static final Map<UUID, SkyblockItem> items = new HashMap<>();

    public static void registerItem(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.getBoolean("vanilla") && !items.containsKey(nbti.getUUID("sb_unique_id"))) {
            items.put(nbti.getUUID("sb_unique_id"), SkyblockItem.getItemFrom(i));
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        idify(e.getInventory());
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        idify(e.getInventory());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        idify(e.getPlayer().getInventory());
    }

    private void idify(Inventory inv) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                ItemStack ided = addID(i);
                NBTItem nbtided = new NBTItem(ided);
                if (!nbti.hasKey("sb_unique_id")) {
                    inv.setItem(count, ided);
                }
                if (!nbti.getBoolean("vanilla") && !items.containsKey(nbtided.getUUID("sb_unique_id"))) {
                    items.put(nbtided.getUUID("sb_unique_id"), SkyblockItem.getItemFrom(ided));
                }
                count++;
            }
        }
    }

    private ItemStack addID(ItemStack item) {
        NBTItem i = new NBTItem(item);
        if (!i.hasKey("sb_unique_id")) {
            i.setUUID("sb_unique_id", UUID.randomUUID());
        }
        return i.getItem();
    }

}
