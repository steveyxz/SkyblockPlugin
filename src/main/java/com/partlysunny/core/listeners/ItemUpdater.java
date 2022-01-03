package com.partlysunny.core.listeners;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.enums.VanillaArmorAttributes;
import com.partlysunny.core.enums.VanillaDamageAttributes;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.ItemStats;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.items.Blood;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ItemUpdater implements Listener {

    public static final Map<UUID, SkyblockItem> items = new HashMap<>();

    public static void registerItem(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (nbti.hasKey("sb_unique_id")) {
            UUID sb_unique_id = nbti.getUUID("sb_unique_id");
            if (!nbti.getBoolean("vanilla") && !items.containsKey(sb_unique_id)) {
                items.put(sb_unique_id, SkyblockItem.getItemFrom(i));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent e) {
        updateVanilla(e.getInventory());
        if (idify(e.getInventory())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "The inventory you opened contained illegal items, so they were removed :)");
        }
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent e) {
        updateVanilla(e.getInventory());
        if (idify(e.getInventory())) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "The inventory you opened contained illegal items, so they were removed :)");
        }
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        updateVanilla(e.getPlayer().getInventory());
        if (idify(e.getPlayer().getInventory())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Your inventory contained illegal items, so they were removed :)");
        }
        updateInventory(e.getPlayer().getInventory());
    }

    private ItemStack transformNBT(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        try {
            ItemStats.setItemStats(nbti, new ItemStat(StatType.DAMAGE, VanillaDamageAttributes.valueOf(i.getType().toString()).getDamage()));
        } catch (Exception ignored) {
        }
        try {
            ItemStats.setItemStats(nbti, new ItemStat(StatType.DEFENSE, VanillaArmorAttributes.valueOf(i.getType().toString()).getDefense()));
        } catch (Exception ignored) {
        }
        return DataUtils.convertVanilla(nbti.getItem());
    }

    private void updateVanilla(Inventory inv) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(i);
                if (nbtItem.hasKey("vanilla") && nbtItem.getBoolean("vanilla")) {
                    //TODO update vanilla items
                }
                if (!nbtItem.hasKey("sb_id")) {
                    System.out.println("item vanillafied: " + i.getType());
                    inv.setItem(count, transformNBT(i));
                }
            }
            count++;
        }
    }

    private boolean idify(Inventory inventory) {
        int count = 0;
        boolean shouldCancel = false;
        ItemStack[] updated = new ItemStack[inventory.getSize()];
        for (ItemStack s : inventory.getContents()) {
            updated[count] = s;
            if (s != null && s.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(s, true);
                if (!nbti.hasKey("sb_unique") || !nbti.hasKey("sb_id") || !nbti.hasKey("vanilla")) {
                    ConsoleLogger.console("Item has been found with missing information");
                    count++;
                    continue;
                }
                if (!nbti.getBoolean("sb_unique")) {
                    if (!nbti.getBoolean("vanilla")) {
                        SkyblockItem i = SkyblockItem.getItemFrom(s);
                        if (i == null) {
                            ConsoleLogger.console("Null skyblock item obtained?");
                            count++;
                            continue;
                        }
                        i.updateSkyblockItem();
                        updated[count] = i.getSkyblockItem();
                    }
                } else {
                    ItemStack withid = addId(s);
                    if (withid.getAmount() > 1) {
                        updated[count] = null;
                        shouldCancel = true;
                        count++;
                        continue;
                    }
                    registerItem(withid);
                }
            }
            count++;
        }
        inventory.setContents(updated);
        return shouldCancel;
    }

    private ItemStack addId(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.hasKey("sb_unique_id") && nbti.getBoolean("sb_unique")) {
            nbti.setUUID("sb_unique_id", UUID.randomUUID());
        }
        return nbti.getItem();
    }

    private void updateInventory(Inventory inventory) {
        int count = 0;
        ItemStack[] updated = new ItemStack[inventory.getSize()];
        for (ItemStack s : inventory.getContents()) {
            updated[count] = s;
            if (s != null && s.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(s);
                if (!nbti.getBoolean("unique")) {
                    count++;
                    continue;
                }
                if (items.containsKey(nbti.getUUID("sb_unique_id"))) {
                    SkyblockItem i = items.get(nbti.getUUID("sb_unique_id"));
                    ItemStack skyblockItem = i.getSkyblockItem();
                    if (!skyblockItem.isSimilar(s)) {
                        updated[count] = skyblockItem;
                    }
                }
            }
        }
        inventory.setContents(updated);
    }

    @EventHandler
    public void temp(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            ((Player) e.getDamager()).getInventory().addItem(new Blood().getSkyblockItem());
        }
    }


}
