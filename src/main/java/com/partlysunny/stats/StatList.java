package com.partlysunny.stats;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatList {

    private Map<StatType, ItemStat> statList = new HashMap<>();

    public void addStat(ItemStat stat) {
        statList.put(stat.type(), stat);
    }

    public void removeStat(StatType type) {
        statList.remove(type);
    }

    public void removeStat(ItemStat stat) {
        statList.remove(stat.type());
    }

    public NBTItem applyStats(NBTItem item) {
        return ItemStats.setItemStats(item, asList());
    }

    public ItemStat[] asList() {
        ItemStat[] returned = new ItemStat[statList.size()];
        int count = 0;
        for (ItemStat s : statList.values()) {
            returned[count] = s;
            count++;
        }
        return returned;
    }

}
