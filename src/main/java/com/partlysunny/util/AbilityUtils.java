package com.partlysunny.util;

import com.partlysunny.items.abilities.AbilityType;
import com.partlysunny.items.custom.SkyblockItem;
import com.partlysunny.listeners.ItemTracker;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import static com.partlysunny.listeners.ItemTracker.items;

public class AbilityUtils {

    public static boolean hasAbility(ItemStack i, AbilityType type) {
        NBTItem nbti = new NBTItem(i);
        if (nbti.getBoolean("vanilla") || !nbti.hasKey("sb_unique_id")) {
            return false;
        }
        if (items.containsKey(nbti.getUUID("sb_unique_id"))) {
            SkyblockItem sbi = items.get(nbti.getUUID("sb_unique_id"));
            return sbi.getCombinedAbilities().contains(type);
        } else {
            ItemTracker.registerItem(i);
            SkyblockItem sbi = items.get(nbti.getUUID("sb_unique_id"));
            return sbi.getCombinedAbilities().contains(type);
        }
    }

}
