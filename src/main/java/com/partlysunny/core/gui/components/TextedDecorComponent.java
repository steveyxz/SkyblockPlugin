package com.partlysunny.core.gui.components;

import com.partlysunny.core.gui.SkyblockGui;
import com.partlysunny.core.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TextedDecorComponent extends GuiComponent {
    public TextedDecorComponent(ItemStack i, SkyblockGui parent) {
        super("texted_gui", i, parent);
        ItemUtils.setId(shownItem(), id());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (ItemUtils.getId(e.getCurrentItem()).equals(id())) {
            e.setCancelled(true);
        }
    }
}
