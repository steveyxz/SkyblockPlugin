package com.partlysunny.core.util;

import com.partlysunny.core.items.ItemUpdater;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.additions.IAbilityAddition;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class AbilityUtils {

    public static boolean hasAbility(ItemStack i, String type) {
        if (i == null || i.getType() == Material.AIR) {
            return false;
        }
        NBTItem nbti = new NBTItem(i);
        if (!nbti.getBoolean("sb_unique")) {
            return false;
        }
        UUID sb_unique_id = nbti.getUUID("sb_unique_id");
        if (!ItemUpdater.items.containsKey(sb_unique_id)) {
            ItemUpdater.registerItem(i);
        }
        SkyblockItem sbi = ItemUpdater.items.get(sb_unique_id);
        if (sbi.getAbilities() != null) {
            for (Ability a : sbi.getAbilities().asList()) {
                if (Objects.equals(a.id(), type)) {
                    return true;
                }
            }
        }
        for (IAbilityAddition a : sbi.abilityAdditions().asAbilityList()) {
            if (Objects.equals(a.getAbilities().id(), type)) {
                return true;
            }
        }
        return false;
    }

}
