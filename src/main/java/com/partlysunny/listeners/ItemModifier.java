package com.partlysunny.listeners;

import com.partlysunny.common.Rarity;
import com.partlysunny.common.VanillaArmorAttributes;
import com.partlysunny.common.VanillaDamageAttributes;
import com.partlysunny.items.lore.LoreBuilder;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.ItemStats;
import com.partlysunny.stats.StatType;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.plugin.NBTAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.itemutils.ItemUtils;

import java.util.Objects;

public class ItemModifier implements Listener {

    @EventHandler
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

    @EventHandler
    public void temp(PlayerJoinEvent e) {
        e.getPlayer().getInventory().addItem(new ItemBuilder(Material.IRON_SWORD).setName(ChatColor.RESET + "Hyperion").addLore(new LoreBuilder().setDescription("Epik gamer sword").setStats(new ItemStat(StatType.DAMAGE, 1000000000), new ItemStat(StatType.INTELLIGENCE, 1000000000), new ItemStat(StatType.FEROCITY, 1000000000), new ItemStat(StatType.STRENGTH, 1000000000), new ItemStat(StatType.TRUE_DEFENSE, 1000000000)).setRarity(Rarity.DIVINE).build()).toItemStack());
    }

    private ItemStack transformNBT(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        nbti.setString("sb_id", i.getType().toString().toLowerCase());
        try { ItemStats.setItemStats(nbti, new ItemStat(StatType.DAMAGE, VanillaDamageAttributes.valueOf(i.getType().toString()).getDamage())); } catch (Exception ignored) {}
        try { ItemStats.setItemStats(nbti, new ItemStat(StatType.DEFENSE, VanillaArmorAttributes.valueOf(i.getType().toString()).getDefense())); } catch (Exception ignored) {}
        return nbti.getItem();
    }

}
