package com.partlysunny.core.listeners;

import com.partlysunny.core.enums.VanillaArmorAttributes;
import com.partlysunny.core.enums.VanillaDamageAttributes;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.ItemStats;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.items.Blood;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class ItemUpdater implements Listener {

    public static final Map<UUID, SkyblockItem> items = new HashMap<>();

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent e) {
        idify(e.getInventory());
        updateVanilla(e.getInventory());
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent e) {
        idify(e.getInventory());
        updateVanilla(e.getInventory());
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND) {
            idify(e.getPlayer().getInventory());
            updateVanilla(e.getPlayer().getInventory());
            updateInventory(e.getPlayer().getInventory());
        }
    }

    @EventHandler
    public void temp(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            ((Player) e.getDamager()).getInventory().addItem(new Blood().getSkyblockItem());
        }
    }

    private void updateUniques(Inventory inv) {
        int count = 0;
        ItemStack[] newInv = new ItemStack[inv.getSize()];
        for (ItemStack i : inv.getContents()) {
            newInv[count] = i;
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (nbti.getBoolean("sb_unique")) {
                    SkyblockItem sbi = SkyblockItem.getItemFrom(i);
                    if (sbi == null) {
                        System.out.println("sbi is null wth");
                        count++;
                        continue;
                    }
                    sbi.updateSkyblockItem();
                    newInv[count] = sbi.getSkyblockItem();
                }
            }
            count++;
        }
        inv.setContents(newInv);
    }

    private void updateInventory(Inventory inv) {
        updateUniques(inv);
        int count = 0;
        ItemStack[] newInv = new ItemStack[inv.getSize()];
        for (ItemStack i : inv.getContents()) {
            newInv[count] = i;
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (!nbti.hasKey("sb_unique_id")) {
                    count++;
                    continue;
                }
                ItemStack skyblockItem;
                UUID sb_unique_id;
                sb_unique_id = nbti.getUUID("sb_unique_id");
                if (!items.containsKey(sb_unique_id)) {
                    registerItem(i);
                    count++;
                    continue;
                }
                skyblockItem = items.get(sb_unique_id).getSkyblockItem();
                skyblockItem.setAmount(i.getAmount());
                if (skyblockItem.getItemMeta() == null || i.getItemMeta() == null) {
                    count++;
                    continue;
                }
                if (
                        !new NBTItem(skyblockItem).equals(new NBTItem(i))
                                || skyblockItem.getType() != i.getType()
                                || !Objects.equals(skyblockItem.getItemMeta().getLore(), i.getItemMeta().getLore())
                                || !skyblockItem.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())
                ) {
                    newInv[count] = skyblockItem;
                }
            }
            count++;
        }
        inv.setContents(newInv);
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
            if (i != null && !(i.getType() == Material.AIR)) {
                if (new NBTItem(i).getString("sb_id") == null || Objects.equals(new NBTItem(i).getString("sb_id"), "")) {
                    inv.setItem(count, transformNBT(i));
                }
            }
            count++;
        }
    }

    private void idify(Inventory inv) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                if (nbti.getBoolean("sb_unique")) {
                    count++;
                    continue;
                }
                ItemStack ided = addID(i);
                NBTItem nbtided = new NBTItem(ided);
                if (!nbti.hasKey("sb_unique_id")) {
                    inv.setItem(count, ided);
                }
                if (!nbti.getBoolean("vanilla") && !items.containsKey(nbtided.getUUID("sb_unique_id"))) {
                    SkyblockItem itemFrom = SkyblockItem.getItemFrom(ided);
                    items.put(nbtided.getUUID("sb_unique_id"), itemFrom);
                }
                count++;
            }
        }
    }

    private ItemStack addID(ItemStack item) {
        NBTItem i = new NBTItem(item);
        if (!i.hasKey("sb_unique_id") && !i.getBoolean("sb_unique")) {
            i.setUUID("sb_unique_id", UUID.randomUUID());
        }
        return i.getItem();
    }

    public static void registerItem(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        UUID sb_unique_id = nbti.getUUID("sb_unique_id");
        if (!nbti.getBoolean("vanilla") && !items.containsKey(sb_unique_id)) {
            items.put(sb_unique_id, SkyblockItem.getItemFrom(i));
        }
    }


}
