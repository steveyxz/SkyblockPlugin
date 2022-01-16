package com.partlysunny.core.util;

import com.partlysunny.core.stats.Stat;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

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

    public static ItemStack getSkullItem(String id, String value) {
        NBTItem nbti = new NBTItem(new ItemStack(Material.PLAYER_HEAD));
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setUUID("Id", UUID.fromString(id));

        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value", value);
        return nbti.getItem();
    }

    public static ItemStack getLeatherArmorItem(Color color, Material material) {
        ItemStack item = new ItemStack(material);
        if (item.getItemMeta() instanceof LeatherArmorMeta m) {
            m.setColor(color);
            item.setItemMeta(m);
        }
        return item;
    }

}
