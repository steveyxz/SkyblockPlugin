package com.partlysunny.core.gui.components;

import com.partlysunny.core.gui.SkyblockGui;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class GuiComponent implements Listener {

    private final String id;
    @Nullable
    private ItemStack shownItem;
    protected final SkyblockGui parent;

    public GuiComponent(String id, ItemStack shownItem, SkyblockGui parent) {
        this.id = id;
        this.shownItem = shownItem;
        this.parent = parent;
    }

    public String id() {
        return id;
    }

    @Nullable
    public ItemStack shownItem() {
        return shownItem;
    }

    public void setShownItem(@Nullable ItemStack shownItem) {
        this.shownItem = shownItem;
    }
}
