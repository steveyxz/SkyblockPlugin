package com.partlysunny.core.gui.components;

import com.partlysunny.core.gui.SkyblockGui;
import com.partlysunny.core.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ToggleableComponent extends GuiComponent {

    private boolean toggleOn = false;
    private final ItemStack onItem;
    private final ItemStack offItem;
    private final UUID uniqueID = UUID.randomUUID();

    public ToggleableComponent(ItemStack off, ItemStack on, SkyblockGui parent) {
        super("toggleable_gui", off, parent);
        ItemUtils.setUniqueId(off, uniqueID);
        ItemUtils.setUniqueId(on, uniqueID);
        this.onItem = on;
        this.offItem = off;
    }

    public void toggle() {
        toggleOn = !toggleOn;
    }

    @EventHandler
    public void onToggle(InventoryClickEvent e) {
        if (ItemUtils.getId(e.getCurrentItem()).equals(id()) && Objects.equals(ItemUtils.getUniqueId(e.getCurrentItem()), uniqueID)) {
            toggle();
            if (e.getClickedInventory() != null) {
                if (toggleOn) {
                    e.getClickedInventory().setItem(e.getSlot(), onItem);
                } else {
                    e.getClickedInventory().setItem(e.getSlot(), offItem);
                }
            }
            e.setCancelled(true);
        }
    }
}
