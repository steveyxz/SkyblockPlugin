package com.partlysunny.core.util;

import com.partlysunny.Skyblock;
import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.enums.VanillaArmorAttributes;
import com.partlysunny.core.enums.VanillaDamageAttributes;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.additions.reforges.Reforge;
import com.partlysunny.core.items.additions.reforges.ReforgeManager;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.classes.Pair;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.HashMap;

import static com.partlysunny.core.items.ItemUpdater.items;
import static com.partlysunny.core.items.ItemUpdater.registerItem;

public class DataUtils {

    public static boolean isSame(ItemStack a, ItemStack b) {
        NBTItem an = new NBTItem(a);
        NBTItem bn = new NBTItem(b);
        return an.getUUID("sb_unique_id") == bn.getUUID("sb_unique_id");
    }

    public static ItemType getTypeOfItem(Material s) {
        final String typeNameString = s.name();
        ItemType type = ItemType.ITEM;
        if (typeNameString.endsWith("_HELMET")) {
            type = ItemType.HELMET;
        } else if (typeNameString.endsWith("_CHESTPLATE")) {
            type = ItemType.CHESTPLATE;
        } else if (typeNameString.endsWith("_LEGGINGS")) {
            type = ItemType.LEGGINGS;
        } else if (typeNameString.endsWith("_BOOTS")) {
            type = ItemType.BOOTS;
        } else if (typeNameString.endsWith("_SWORD")) {
            type = ItemType.SWORD;
        } else if (typeNameString.endsWith("_PICKAXE")) {
            type = ItemType.PICKAXE;
        } else if (typeNameString.endsWith("_SHOVEL")) {
            type = ItemType.SHOVEL;
        } else if (typeNameString.endsWith("_HOE")) {
            type = ItemType.HOE;
        } else if (typeNameString.endsWith("_AXE")) {
            type = ItemType.AXE;
        } else if (typeNameString.endsWith("BOW")) {
            type = ItemType.BOW;
        }
        return type;
    }

    public static SkyblockItem getSkyblockItem(ItemStack stack, Player p) {
        if (stack == null || stack.getType() == Material.AIR) {
            return null;
        }
        NBTItem i = new NBTItem(stack);
        if (i.getBoolean("sb_unique")) {
            if (!items.containsKey(i.getUUID("sb_unique_id"))) {
                registerItem(stack, p);
            }
            return items.get(i.getUUID("sb_unique_id"));
        } else {
            if (i.getBoolean("vanilla")) {
                return createSkyblockItemFromVanilla(stack, p);
            }
        }
        return null;
    }

    public static SkyblockItem getSkyblockItem(ItemStack stack) {
        return getSkyblockItem(stack, null);
    }

    public static SkyblockItem createSkyblockItemFromVanilla(ItemStack s, @Nullable Player player) {
        NBTItem nbti = new NBTItem(s);
        if (nbti.getBoolean("vanilla")) {
            SkyblockItem skyblockItem = new SkyblockItem(s.getType().toString().toLowerCase(), false, getTypeOfItem(s.getType()), player, true) {
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
                    return getVanillaStats(s.getType());
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
            skyblockItem.setStackCount(s.getAmount());
            skyblockItem.statAdditions().addAdditions(nbti.getCompound("statAdditions"));
            skyblockItem.abilityAdditions().addAdditions(nbti.getCompound("abilityAdditions"));
            skyblockItem.rarityAdditions().addAdditions(nbti.getCompound("rarityAdditions"));
            return skyblockItem;
        }
        return null;
    }

    public static ItemStack setVanillaStats(ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        try {
            ItemUtils.setItemStats(nbti, new Stat(StatType.DAMAGE, VanillaDamageAttributes.valueOf(i.getType().toString()).getDamage() * 5));
        } catch (Exception ignored) {
        }
        try {
            ItemUtils.setItemStats(nbti, new Stat(StatType.DEFENSE, VanillaArmorAttributes.valueOf(i.getType().toString()).getDefense()));
        } catch (Exception ignored) {
        }
        return nbti.getItem();
    }

    public static StatList getVanillaStats(Material i) {
        StatList stl = new StatList();
        try {
            stl.addStat(new Stat(StatType.DAMAGE, VanillaDamageAttributes.valueOf(i.toString()).getDamage() * 5));
        } catch (Exception ignored) {
        }
        try {
            stl.addStat(new Stat(StatType.DEFENSE, VanillaArmorAttributes.valueOf(i.toString()).getDefense()));
        } catch (Exception ignored) {
        }
        return stl;
    }

    public static SkyblockItem createSkyblockItemFromVanilla(Material s, @Nullable Player player) {
        return new SkyblockItem(s.toString().toLowerCase(), false, getTypeOfItem(s), player, true) {
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
                return getVanillaStats(s);
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
    }

    public static StatList getStatsOfBest(String reforge, Rarity ra) {
        StatList addition;
        Reforge r = ReforgeManager.getReforge(reforge);
        if (r == null) {
            ConsoleLogger.console("Illegal reforge " + reforge + " found on item");
            return null;
        }
        if (r.statBonuses().containsKey(ra)) {
            addition = r.statBonuses().get(ra);
        } else {
            Rarity bestRarity = Rarity.COMMON;
            for (Rarity rarity : r.statBonuses().keySet()) {
                if (rarity.level() > bestRarity.level() && rarity.level() <= ra.level()) {
                    bestRarity = rarity;
                }
            }
            addition = r.statBonuses().get(bestRarity);
        }
        return addition;
    }

    public static StatList readStats(ItemStack stack) {
        NBTItem i = new NBTItem(stack);
        StatList r = new StatList();
        for (StatType s : StatType.values()) {
            if (i.hasKey(s.id())) {
                r.addStat(new Stat(s, i.getInteger(s.id())));
            }
        }
        return r;
    }

    @SafeVarargs
    public static HashMap<Rarity, StatList> getStatModifiersFrom(Pair<Rarity, StatList>... stats) {
        HashMap<Rarity, StatList> r = new HashMap<>();
        for (Pair<Rarity, StatList> s : stats) {
            r.put(s.a(), s.b());
        }
        return r;
    }

    @SuppressWarnings("all")
    public static Object getData(String key, PersistentDataType type, Entity e) {
        return e.getPersistentDataContainer().get(new NamespacedKey(JavaPlugin.getPlugin(Skyblock.class), key), type);
    }

    @SuppressWarnings("all")
    public static void setData(String key, Object value, PersistentDataType type, Entity e) {
        e.getPersistentDataContainer().set(new NamespacedKey(JavaPlugin.getPlugin(Skyblock.class), key), type, value);
    }

}
