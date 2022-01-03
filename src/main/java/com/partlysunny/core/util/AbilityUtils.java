package com.partlysunny.core.util;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.listeners.ItemUpdater;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AbilityUtils {

    public static boolean hasAbility(ItemStack i, AbilityType type) {
        if (i == null || i.getType() == Material.AIR) {
            return false;
        }
        NBTItem nbti = new NBTItem(i);
        if (nbti.getBoolean("vanilla") || !nbti.hasKey("sb_unique_id")) {
            return false;
        }
        UUID sb_unique_id = nbti.getUUID("sb_unique_id");
        if (ItemUpdater.items.containsKey(sb_unique_id)) {
            SkyblockItem sbi = ItemUpdater.items.get(sb_unique_id);
            return sbi.getCombinedAbilities().contains(type);
        } else {
            ItemUpdater.registerItem(i);
            SkyblockItem sbi = ItemUpdater.items.get(sb_unique_id);
            return sbi.getCombinedAbilities().contains(type);
        }
    }

}
