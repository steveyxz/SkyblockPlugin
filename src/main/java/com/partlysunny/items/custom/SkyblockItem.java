package com.partlysunny.items.custom;

import com.partlysunny.items.lore.abilities.Ability;
import com.partlysunny.stats.StatList;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.CustomItem;

import java.util.List;

public abstract class SkyblockItem implements Listener {

    private final String id;

    protected SkyblockItem(String id) {
        this.id = id;
    }

    public abstract Material getDefaultItem();
    public abstract String getDisplayName();
    public abstract List<Ability> getAbilities();
    public abstract boolean isEnchanted();
    public abstract StatList getStats();

    public void getSkyblockItem() {
        ItemStack i = new ItemStack(getDefaultItem());

    }

    public String id() {
        return id;
    }
}
