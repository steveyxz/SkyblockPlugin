package com.partlysunny.core.listeners;

import com.partlysunny.additions.stat.Infusion;
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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
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
            updateVanilla(((Player) e.getEntity()).getInventory());
            for (Integer i : idify(((Player) e.getEntity()).getInventory())) {
                //TODO add delete items without triggering assertionerror: trap
                e.setCancelled(true);
            }
            updateInventory(((Player) e.getEntity()).getInventory());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClick(PlayerInteractEvent e) {
        updateVanilla(e.getPlayer().getInventory());
        for (Integer i : idify(e.getPlayer().getInventory())) {
            //TODO add delete items without triggering assertionerror: trap
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
        ItemStack[] updated = new ItemStack[inv.getSize()];
        for (ItemStack i : inv.getContents()) {
            updated[count] = i;
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(i);
                if (nbtItem.hasKey("vanilla") && nbtItem.getBoolean("vanilla")) {
                    //TODO update vanilla items
                }
                if (!nbtItem.hasKey("sb_id")) {
                    System.out.println("item vanillafied: " + i.getType());
                    ItemStack transformed = transformNBT(i);
                    if (transformed != null) {
                        updated[count] = transformed;
                    }
                }
            }
            count++;
        }
        inv.setContents(updated);
    }

    private List<Integer> idify(Inventory inventory) {
        int count = 0;
        List<Integer> toDelete = new ArrayList<>();
        ItemStack[] updated = new ItemStack[inventory.getSize()];
        for (ItemStack s : inventory.getContents()) {
            updated[count] = s;
            if (s != null && s.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(s);
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
                        toDelete.add(count);
                        count++;
                        continue;
                    }
                    registerItem(withid);
                }
            }
            count++;
        }
        inventory.setContents(updated);
        return toDelete;
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
                if (items.containsKey(nbti.getUUID("sb_unique_id")) && !nbti.getBoolean("vanilla")) {
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

    @EventHandler
    public void temp2(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
        if (itemInMainHand.getAmount() == 1) {
            NBTItem i = new NBTItem(itemInMainHand);
            if (!i.getBoolean("vanilla")) {
                SkyblockItem sbi = SkyblockItem.getItemFrom(itemInMainHand);
                sbi.statAdditions().addAddition(new Infusion());
                p.getInventory().setItem(EquipmentSlot.HAND, sbi.getSkyblockItem());
            }
        }
    }


}
