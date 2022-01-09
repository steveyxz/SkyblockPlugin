package com.partlysunny.core.items.stats;

import de.tr7zw.nbtapi.NBTItem;

public class ItemStats {

    public static NBTItem setItemStats(NBTItem item, ItemStat... stats) {
        for (ItemStat s : stats) {
            if (s.value() == 0) {
                item.removeKey(s.type().id());
                continue;
            }
            item.setDouble(s.type().id(), s.value());
        }
        return item;
    }

}
