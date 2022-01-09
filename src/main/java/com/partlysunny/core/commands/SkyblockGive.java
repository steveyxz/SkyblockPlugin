package com.partlysunny.core.commands;

import com.partlysunny.core.items.ItemInfo;
import com.partlysunny.core.items.ItemManager;
import com.partlysunny.core.items.SkyblockItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkyblockGive implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length < 1 || !s.equals("sbgive")) {
            return true;
        }
        String itemId = strings[0];
        ItemInfo infoFromId = ItemManager.getInfoFromId(itemId);
        if (infoFromId == null) {
            p.sendMessage(ChatColor.RED + "Invalid item type: " + strings[0]);
            return true;
        }
        SkyblockItem instance = ItemManager.getInstance(infoFromId);
        if (instance != null) {
            if (strings.length > 1) {
                try {
                    instance.setStackCount(Integer.parseInt(strings[1]));
                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.RED + "Invalid number provided. Giving one item instead.");
                }
            }
            p.getInventory().addItem(instance.getSkyblockItem());
            p.sendMessage(ChatColor.GREEN + "Gave you " + instance.stackCount() + " " + instance.getDisplayName());
        } else {
            p.sendMessage(ChatColor.RED + "Invalid item type: " + strings[0]);
        }
        return true;
    }
}
