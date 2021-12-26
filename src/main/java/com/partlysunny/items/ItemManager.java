package com.partlysunny.items;

import com.partlysunny.items.custom.SkyblockItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private final List<SkyblockItem> items = new ArrayList<>();

    public void addItem(SkyblockItem item) {
        items.add(item);
    }

    public void removeItem(String itemId) {
        SkyblockItem removed = null;
        for (SkyblockItem a : items) {
            if (a.id().equals(itemId)) {
                removed = a;
            }
        }
        items.remove(removed);
    }

    public SkyblockItem getItem(String itemId) {
        for (SkyblockItem a : items) {
            if (a.id().equals(itemId)) {
                return a;
            }
        }
        return null;
    }

    public ItemStack getItemOf(String itemId) {
        return getItem(itemId).getSkyblockItem();
    }

}
