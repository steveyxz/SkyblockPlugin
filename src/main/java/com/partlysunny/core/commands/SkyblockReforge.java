package com.partlysunny.core.commands;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.reforges.Reforge;
import com.partlysunny.core.items.additions.reforges.ReforgeManager;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SkyblockReforge implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length < 1 || !s.equals("sbreforge")) {
            return true;
        }
        String reforgeId = strings[0];
        Reforge info = ReforgeManager.getReforge(reforgeId);
        if (info == null) {
            p.sendMessage(ChatColor.RED + "Invalid reforge type: " + reforgeId);
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
        if (!sbi.type().reforgable()) {
            p.sendMessage(ChatColor.RED + "Item on your hand is not reforgable!");
            return true;
        }
        if (!info.canApply(sbi)) {
            p.sendMessage(ChatColor.RED + "Item on your hand is not compatible with this reforge!");
            return true;
        }
        sbi.setReforge(info.id());
        p.getInventory().setItemInMainHand(sbi.getSkyblockItem());
        p.sendMessage(ChatColor.GREEN + "Successfully applied the reforge " + info.id() + " to your held item");
        return true;
    }
}
