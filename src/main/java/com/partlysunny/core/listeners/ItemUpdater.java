package com.partlysunny.core.listeners;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.enums.VanillaArmorAttributes;
import com.partlysunny.core.enums.VanillaDamageAttributes;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.ItemStats;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;


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
        for (Integer i : idify(e.getInventory())) {
            e.setCancelled(true);
        }
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        updateVanilla(e.getClickedInventory());
        for (Integer i : idify(e.getInventory())) {
            e.setCancelled(true);
        }
        updateInventory(e.getClickedInventory());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            ItemStack i = e.getItem().getItemStack();
            i = getVanilla(i);
            i = getIded(i);
            i = getUpdated(i);
            e.setCancelled(true);
            e.getItem().remove();
            ((Player) e.getEntity()).getInventory().addItem(i);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        updateVanilla(e.getPlayer().getInventory());
        for (Integer i : idify(e.getPlayer().getInventory())) {
            e.setCancelled(true);
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
                ItemStack vanilla = getVanilla(i);
                if (vanilla != i) {
                    inv.setItem(count, vanilla);
                }
            }
            count++;
        }
    }

    private ItemStack getVanilla(ItemStack s) {
        NBTItem nbtItem = new NBTItem(s);
        if (nbtItem.hasKey("vanilla") && nbtItem.getBoolean("vanilla")) {
            //TODO update vanilla items
        }
        if (!nbtItem.hasKey("sb_id")) {
            ItemStack transformed = transformNBT(s);
            if (transformed != null) {
                return transformed;
            }
        }
        return s;
    }

    private List<Integer> idify(Inventory inventory) {
        int count = 0;
        List<Integer> toDelete = new ArrayList<>();
        for (ItemStack s : inventory.getContents()) {
            if (s != null && s.getType() != Material.AIR) {
                ItemStack ided = getIded(s);
                if (!Objects.equals(ided, s)) {
                    inventory.setItem(count, ided);
                }
            }
            count++;
        }
        return toDelete;
    }

    private ItemStack getIded(ItemStack s) {
        NBTItem nbti = new NBTItem(s);
        if (!nbti.hasKey("sb_unique") || !nbti.hasKey("sb_id") || !nbti.hasKey("vanilla")) {
            ConsoleLogger.console("Item has been found with missing information");
            return s;
        }
        if (!nbti.getBoolean("sb_unique")) {
            if (!nbti.getBoolean("vanilla")) {
                SkyblockItem i = SkyblockItem.getItemFrom(s);
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

    private ItemStack addId(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.hasKey("sb_unique_id") && nbti.getBoolean("sb_unique")) {
            nbti.setUUID("sb_unique_id", UUID.randomUUID());
        }
        return nbti.getItem();
    }

    private void updateInventory(Inventory inventory) {
        int count = 0;
        for (ItemStack s : inventory.getContents()) {
            if (s != null && s.getType() != Material.AIR) {
                ItemStack updated = getUpdated(s);
                if (!updated.equals(s)) {
                    inventory.setItem(count, updated);
                }
            }
            count++;
        }
    }

    private ItemStack getUpdated(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        if (!nbti.getBoolean("sb_unique")) {
            return i;
        }
        if (items.containsKey(nbti.getUUID("sb_unique_id")) && !nbti.getBoolean("vanilla")) {
            SkyblockItem sbi = items.get(nbti.getUUID("sb_unique_id"));
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


}
