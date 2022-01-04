package com.partlysunny.core.util;

import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.lore.LoreBuilder;
import com.partlysunny.core.items.name.NameBuilder;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DataUtils {

    public static boolean isSame(ItemStack a, ItemStack b) {
        NBTItem an = new NBTItem(a);
        NBTItem bn = new NBTItem(b);
        return an.getUUID("sb_unique_id") == bn.getUUID("sb_unique_id");
    }

    public static SkyblockItem createSkyblockItemFromVanilla(ItemStack s) {
        NBTItem nbti = new NBTItem(s);
        if (nbti.getBoolean("vanilla")) {
            SkyblockItem skyblockItem = new SkyblockItem(s.getType().toString().toLowerCase(), false, true) {
                @Override
                public Material getDefaultItem() {
                    return s.getType();
                }

                @Override
                public String getDisplayName() {
                    return TextUtils.capitalizeWord(s.getType().toString().toLowerCase().replace("_", " "));
                }

                @Override
                public AbilityList getAbilities() {
                    return null;
                }

                @Override
                public boolean isEnchanted() {
                    return false;
                }

                @Override
                public StatList getStats() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "";
                }

                @Override
                public Rarity getRarity() {
                    return Rarity.COMMON;
                }
            };
            skyblockItem.statAdditions().addAdditions(nbti.getCompound("statAdditions"));
            skyblockItem.abilityAdditions().addAdditions(nbti.getCompound("abilityAdditions"));
            skyblockItem.rarityAdditions().addAdditions(nbti.getCompound("rarityAdditions"));
            return skyblockItem;
        }
        return null;
    }

    public static SkyblockItem createSkyblockItemFromVanilla(Material s) {
        SkyblockItem skyblockItem = new SkyblockItem(s.toString().toLowerCase(), false, true) {
            @Override
            public Material getDefaultItem() {
                return s;
            }

            @Override
            public String getDisplayName() {
                return TextUtils.capitalizeWord(s.toString().toLowerCase().replace("_", " "));
            }

            @Override
            public AbilityList getAbilities() {
                return null;
            }

            @Override
            public boolean isEnchanted() {
                return false;
            }

            @Override
            public StatList getStats() {
                return null;
            }

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public Rarity getRarity() {
                return Rarity.COMMON;
            }
        };
        return skyblockItem;
    }

    public static ItemStack convertVanilla(ItemStack i) {
        ItemMeta m = i.getItemMeta();
        if (m == null) {
            return i;
        }
        m.setDisplayName(new NameBuilder().setName(TextUtils.capitalizeWord(i.getType().toString().toLowerCase().replace("_", " "))).setRarity(Rarity.COMMON).build());
        m.setLore(new LoreBuilder()
                .setDescription("")
                .setRarity(Rarity.COMMON)
                .setStats(readStats(i), null)
                .build()
        );
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON);
        m.setUnbreakable(true);
        i.setItemMeta(m);

        NBTItem nbti = new NBTItem(i);
        nbti.setString("sb_id", i.getType().toString().toLowerCase());
        nbti.setBoolean("sb_unique", false);
        nbti.setBoolean("vanilla", true);
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
