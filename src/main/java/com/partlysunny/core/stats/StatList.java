package com.partlysunny.core.stats;

import com.partlysunny.core.util.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;

import java.util.HashMap;
import java.util.Map;

public class StatList {

    public final Map<StatType, Stat> statList = new HashMap<>();

    public StatList() {
    }

    public StatList(Stat... stats) {
        for (Stat stat : stats) {
            statList.put(stat.type(), stat);
        }
    }

    public void addStat(Stat stat) {
        statList.put(stat.type(), stat);
    }

    public void removeStat(StatType type) {
        statList.remove(type);
    }

    public void removeStat(Stat stat) {
        statList.remove(stat.type());
    }

    public NBTItem applyStats(NBTItem item) {
        return ItemUtils.setItemStats(item, asList());
    }

    public Stat[] asList() {
        Stat[] returned = new Stat[statList.size()];
        int count = 0;
        for (Stat s : statList.values()) {
            returned[count] = s;
            count++;
        }
        return returned;
    }

    public StatList merge(StatList list) {
        for (StatType t : list.statList.keySet()) {
            statList.merge(t, list.statList.get(t), (oldVal, newVal) -> {
                oldVal.setValue(oldVal.value() + newVal.value());
                return oldVal;
            });
        }
        return this;
    }
}
