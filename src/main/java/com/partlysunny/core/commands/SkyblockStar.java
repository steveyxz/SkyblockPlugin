package com.partlysunny.core.commands;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SkyblockStar implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length < 0 || !s.equals("sbstar")) {
            return true;
        }
        String number;
        if (strings.length > 0) {
            number = strings[0];
        } else {
            number = "5";
        }
        int stars;
        try {
            stars = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "Invalid number provided.");
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
        sbi.setStars(stars);
        p.getInventory().setItemInMainHand(sbi.getSkyblockItem());
        p.sendMessage(ChatColor.GREEN + "Successfully added " + stars + " stars to your item.");
        return true;
    }
}
