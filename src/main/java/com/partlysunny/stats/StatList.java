package com.partlysunny.stats;

import de.tr7zw.nbtapi.NBTItem;

import java.util.HashMap;
import java.util.Map;

public class StatList {

    private final Map<StatType, ItemStat> statList = new HashMap<>();

    public StatList() {}

    public StatList(ItemStat... stats) {
        for (ItemStat stat : stats) {
            statList.put(stat.type(), stat);
        }
    }

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

    public StatList merge(StatList list) {
        statList.putAll(list.statList);
        return this;
    }
}
