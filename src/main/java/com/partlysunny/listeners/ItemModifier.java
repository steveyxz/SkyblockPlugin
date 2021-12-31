package com.partlysunny.listeners;

import com.partlysunny.enums.VanillaArmorAttributes;
import com.partlysunny.enums.VanillaDamageAttributes;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.ItemStats;
import com.partlysunny.stats.StatType;
import com.partlysunny.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ItemModifier implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent e) {
        int count = 0;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i != null && !(i.getType() == Material.AIR)) {
                if (new NBTItem(i).getString("sb_id") == null || Objects.equals(new NBTItem(i).getString("sb_id"), "")) {
                    e.getInventory().setItem(count, transformNBT(i));
                }
            }
            count++;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryInteract(InventoryClickEvent e) {
        int count = 0;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i != null && !(i.getType() == Material.AIR)) {
                if (new NBTItem(i).getString("sb_id") == null || Objects.equals(new NBTItem(i).getString("sb_id"), "")) {
                    e.getInventory().setItem(count, transformNBT(i));
                }
            }
            count++;
        }
    }

    @EventHandler
    public void temp(PlayerJoinEvent e) {

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

}
