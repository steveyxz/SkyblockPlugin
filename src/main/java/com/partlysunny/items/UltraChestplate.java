package com.partlysunny.items;

import com.partlysunny.core.StatList;
import com.partlysunny.core.StatType;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.stats.ItemStat;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UltraChestplate extends SkyblockItem {
    public UltraChestplate(Player p) {
        super("ultrachestplate", true, ItemType.CHESTPLATE, p);
    }

    public UltraChestplate() {
        super("ultrachestplate", true, ItemType.CHESTPLATE, null, new String[] {null, "ultrachestplate", "ultraleggings", null});
    }

    @Override
    public Material getDefaultItem() {
        return Material.NETHERITE_CHESTPLATE;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Chestplate";
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList();
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new ItemStat(StatType.CRIT_CHANCE, 40),
                new ItemStat(StatType.CRIT_DAMAGE, 300),
                new ItemStat(StatType.ATTACK_SPEED, 20),
                new ItemStat(StatType.MAGIC_FIND, 10),
                new ItemStat(StatType.INTELLIGENCE, 280),
                new ItemStat(StatType.STRENGTH, 120),
                new ItemStat(StatType.DEFENSE, 420),
                new ItemStat(StatType.HEALTH, 620)
        );
    }

    @Override
    public String getDescription() {
        return "A very ultra chestplate :)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
