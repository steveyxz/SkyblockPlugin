package com.partlysunny.core.util;

import com.partlysunny.core.stats.Stat;
import de.tr7zw.nbtapi.NBTItem;

public class ItemUtils {

    public static NBTItem setItemStats(NBTItem item, Stat... stats) {
        for (Stat s : stats) {
            if (s.value() == 0) {
                item.removeKey(s.type().id());
                continue;
            }
            item.setDouble(s.type().id(), s.value());
        }
        return item;
    }

}
