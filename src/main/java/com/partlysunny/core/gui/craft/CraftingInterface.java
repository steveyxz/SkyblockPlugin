package com.partlysunny.core.gui.craft;

import com.partlysunny.Skyblock;
import com.partlysunny.core.gui.SkyblockGui;
import com.partlysunny.core.util.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CraftingInterface implements Listener, SkyblockGui {

    private static boolean registered = false;
    private static final Integer[] craftingSlots = new Integer[] {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final Integer resultSlot = 23;
    private static final Integer backButton = 49;
    private static final Integer[] quickCraftSlots = new Integer[] {16, 25, 34};
    private static final ItemStack resultDefault = new ItemStack(Material.BARRIER);
    private static final ItemStack backDefault = new ItemStack(Material.ARROW);
    private static final ItemStack quickCraft = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    private static final ItemStack emptyDefault = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    @SuppressWarnings("all")
    public CraftingInterface(Player p) {
        //TODO finish crafting
        Inventory craft = Bukkit.createInventory(p, 54, "Craft Item");
        ItemMeta m = resultDefault.getItemMeta();
        m.setDisplayName(ChatColor.RED + "Recipe Required");
        m.setLore(TextUtils.wrap("Add the items for a valid recipe in the crafting grid on the left!", 30));
        resultDefault.setItemMeta(m);
        m = backDefault.getItemMeta();
        m.setDisplayName(ChatColor.GREEN + "Go Back");
        m.setLore(TextUtils.wrap("To Skyblock Menu", 30));
        backDefault.setItemMeta(m);
        m = quickCraft.getItemMeta();
        m.setDisplayName(ChatColor.RED + "COMING SOON");
        m.setLore(TextUtils.wrap("Quick craft!", 30));
        quickCraft.setItemMeta(m);
        m = emptyDefault.getItemMeta();
        m.setDisplayName(" ");
        emptyDefault.setItemMeta(m);
        for (int i = 0; i < 54; i++) {
            if (List.of(craftingSlots).contains(i)) {
            } else if (i == resultSlot) {
                craft.setItem(i, resultDefault);
            } else if (i == backButton) {
                craft.setItem(i, backDefault);
            } else if (List.of(quickCraftSlots).contains(i)) {
                craft.setItem(i, quickCraft);
            } else {
                craft.setItem(i, emptyDefault);
            }
        }
        p.openInventory(craft);
        if (!registered) {
            Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            registered = true;
        }
    }

    @EventHandler
    public void onAddItem(InventoryMoveItemEvent e) {
        Inventory destination = e.getDestination();
        Inventory from = e.getSource();
        //Check if correct inventory
        if (true) {

        }
    }



}
