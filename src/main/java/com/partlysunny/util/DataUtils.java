package com.partlysunny.util;

import com.partlysunny.enums.Rarity;
import com.partlysunny.items.lore.LoreBuilder;
import com.partlysunny.items.name.NameBuilder;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class DataUtils {

    public static ItemStack convertVanilla(ItemStack i) {
        ItemMeta m = i.getItemMeta();
        if (m == null) {
            return i;
        }
        m.setDisplayName(new NameBuilder().setName(i.getItemMeta().getDisplayName()).setRarity(Rarity.COMMON).build());
        m.setLore(new LoreBuilder()
                .setDescription("")
                .setRarity(Rarity.COMMON)
                .setStats(readStats(i).asList())
                .build()
        );
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON);
        m.setUnbreakable(true);
        i.setItemMeta(m);

        NBTItem nbti = new NBTItem(i);
        nbti.setString("sb_id", i.getType().toString().toLowerCase());
        nbti.setBoolean("vanilla", true);
        if (nbti.getUUID("sb_unique_id") == null) {
            nbti.setUUID("sb_unique_id", UUID.randomUUID());
        }
        i = nbti.getItem();
        return i;

    }

    public static StatList readStats(ItemStack stack) {
        NBTItem i = new NBTItem(stack);
        StatList r = new StatList();
        for (StatType s : StatType.values()) {
            if (i.hasKey(s.id())) {
                r.addStat(new ItemStat(s, i.getInteger(s.id())));
            }
        }
        return r;
    }

}
