package com.partlysunny.core.commands;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.enchants.Enchant;
import com.partlysunny.core.items.additions.enchants.EnchantManager;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SkyblockEnchant implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length < 1 || !s.equals("sbenchant")) {
            return true;
        }
        String enchantId = strings[0];
        int level = 1;
        if (strings.length > 1) {
            try {
                level = Integer.parseInt(strings[1]);
            } catch (NumberFormatException ignored) {
            }
        }
        Enchant info = EnchantManager.getEnchant(enchantId);
        if (info == null) {
            p.sendMessage(ChatColor.RED + "Invalid enchant type: " + enchantId);
            return true;
        }
        ItemStack stack = p.getInventory().getItemInMainHand();
        SkyblockItem sbi;
        if (new NBTItem(stack).getBoolean("vanilla")) {
            sbi = SkyblockItem.getItemFrom(stack, p);
        } else {
            sbi = DataUtils.getSkyblockItem(stack, p);
        }
        if (sbi == null) {
            p.sendMessage(ChatColor.RED + "Item on your hand is not valid: " + stack.getType());
            return true;
        }
        if (!info.canApply(sbi)) {
            p.sendMessage(ChatColor.RED + "Item on your hand is not compatible with this enchant!");
            return true;
        }
        /*
        if (level > info.maxLevel() || (level == sbi.enchants().getLevelOf(enchantId) && level + 1 > info.maxLevel())) {
            p.sendMessage(ChatColor.RED + "The enchant level is too high!");
            return true;
        }
         */
        sbi.enchants().addEnchant(info.id(), level);
        p.getInventory().setItemInMainHand(sbi.getSkyblockItem());
        p.sendMessage(ChatColor.GREEN + "Successfully applied the enchant " + info.id() + " of level " + level + " to your held item");
        return true;
    }
}
