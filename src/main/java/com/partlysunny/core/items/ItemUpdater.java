package com.partlysunny.core.items;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class ItemUpdater implements Listener {

    public static final Map<UUID, SkyblockItem> items = new HashMap<>();

    public static void registerItem(ItemStack i, Player player) {
        NBTItem nbti = new NBTItem(i);
        if (nbti.hasKey("sb_unique_id")) {
            UUID sb_unique_id = nbti.getUUID("sb_unique_id");
            if (nbti.getBoolean("sb_unique") && !items.containsKey(sb_unique_id)) {
                items.put(sb_unique_id, SkyblockItem.getItemFrom(i, player));
            }
        }
    }

    public static void registerItem(ItemStack i) {
        registerItem(i, null);
    }

    public static void updateVanilla(Inventory inv, Player p) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                ItemStack vanilla = getVanilla(i, p);
                if (vanilla != i) {
                    inv.setItem(count, vanilla);
                }
            }
            count++;
        }
    }

    public static ItemStack getVanilla(ItemStack s, Player player) {
        NBTItem nbtItem = new NBTItem(s);
        if (nbtItem.hasKey("vanilla") && nbtItem.getBoolean("vanilla")) {
            SkyblockItem i = SkyblockItem.getItemFrom(s, player);
            if (i == null) {
                ConsoleLogger.console("Null vanilla item obtained?");
                return s;
            }
            i.updateSkyblockItem();
            return i.getSkyblockItem();
        }
        if (!nbtItem.hasKey("sb_id")) {
            SkyblockItem transformed = DataUtils.createSkyblockItemFromVanilla(s, player);
            if (transformed != null) {
                return transformed.getSkyblockItem();
            }
        }
        return s;
    }

    public static List<Integer> idify(Inventory inventory, Player player) {
        int count = 0;
        List<Integer> toDelete = new ArrayList<>();
        for (ItemStack s : inventory.getContents()) {
            if (s != null && s.getType() != Material.AIR) {
                ItemStack ided = getIded(s, player);
                if (!Objects.equals(ided, s)) {
                    inventory.setItem(count, ided);
                }
            }
            count++;
        }
        return toDelete;
    }

    public static ItemStack getIded(ItemStack s, Player player) {
        NBTItem nbti = new NBTItem(s);
        if (!nbti.hasKey("sb_unique") || !nbti.hasKey("sb_id") || !nbti.hasKey("vanilla")) {
            ConsoleLogger.console("Item has been found with missing information");
            return s;
        }
        if (!nbti.getBoolean("sb_unique")) {
            if (!nbti.getBoolean("vanilla")) {
                SkyblockItem i = SkyblockItem.getItemFrom(s, player);
                if (i == null) {
                    ConsoleLogger.console("Null skyblock item obtained?");
                    return s;
                }
                i.updateSkyblockItem();
                return i.getSkyblockItem();
            }
        } else {
            ItemStack withid = addId(s);
            if (withid.getAmount() > 1) {
                return null;
            }
            registerItem(withid);
        }
        return s;
    }

    public static ItemStack addId(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.hasKey("sb_unique_id") && nbti.getBoolean("sb_unique")) {
            nbti.setUUID("sb_unique_id", UUID.randomUUID());
        }
        return nbti.getItem();
    }

    public static void updateInventory(Inventory inventory, Player p) {
        int count = 0;
        for (ItemStack s : inventory.getContents()) {
            if (s != null && s.getType() != Material.AIR) {
                ItemStack updated = getUpdated(s, p);
                if (!updated.equals(s)) {
                    inventory.setItem(count, updated);
                }
            }
            count++;
        }
    }

    public static ItemStack getUpdated(ItemStack i, Player entity) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.getBoolean("sb_unique")) {
            return i;
        }
        if (items.containsKey(nbti.getUUID("sb_unique_id")) && !nbti.getBoolean("vanilla")) {
            SkyblockItem sbi = items.get(nbti.getUUID("sb_unique_id"));
            sbi.setOwner(entity);
            sbi.updateSkyblockItem();
            ItemStack skyblockItem = sbi.getSkyblockItem();
            if (skyblockItem != null && skyblockItem.getItemMeta() != null && i.getItemMeta() != null) {
                if (
                        !new NBTItem(skyblockItem).equals(nbti)
                                || skyblockItem.getType() != i.getType()
                                || !Objects.equals(skyblockItem.getItemMeta().getLore(), i.getItemMeta().getLore())
                                || !skyblockItem.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())
                ) {
                    return skyblockItem;
                }
            }
        }
        return i;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent e) {
        updateVanilla(e.getInventory(), (Player) e.getPlayer());
        for (Integer i : idify(e.getInventory(), (Player) e.getPlayer())) {
            e.setCancelled(true);
        }
        updateInventory(e.getInventory(), (Player) e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            ItemStack i = e.getItem().getItemStack();
            i = getVanilla(i, (Player) e.getEntity());
            i = getIded(i, (Player) e.getEntity());
            i = getUpdated(i, (Player) e.getEntity());
            e.setCancelled(true);
            e.getItem().remove();
            ((Player) e.getEntity()).getInventory().addItem(i);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        updateVanilla(e.getPlayer().getInventory(), e.getPlayer());
        for (Integer i : idify(e.getPlayer().getInventory(), e.getPlayer())) {
            e.setCancelled(true);
        }
        updateInventory(e.getPlayer().getInventory(), e.getPlayer());
    }


}
