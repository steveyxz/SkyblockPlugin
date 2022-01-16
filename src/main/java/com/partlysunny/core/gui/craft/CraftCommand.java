package com.partlysunny.core.gui.craft;

import com.partlysunny.core.gui.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class CraftCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equals("craft") && commandSender instanceof Player p) {
            GuiManager.setInventory(p.getUniqueId(), new CraftingInterface(p));
        }
        return true;
    }
}
