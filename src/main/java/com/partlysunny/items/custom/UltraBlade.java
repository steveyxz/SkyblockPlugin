package com.partlysunny.items.custom;

import com.partlysunny.enums.Rarity;
import com.partlysunny.items.abilities.AbilityList;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;
import org.bukkit.Material;

public class UltraBlade extends SkyblockItem {
    public UltraBlade() {
        super("ultra_blade");
    }

    @Override
    public Material getDefaultItem() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Blade";
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
                new ItemStat(StatType.DAMAGE, 1000),
                new ItemStat(StatType.STRENGTH, 1000),
                new ItemStat(StatType.CRIT_DAMAGE, 1000),
                new ItemStat(StatType.CRIT_CHANCE, 1000),
                new ItemStat(StatType.INTELLIGENCE, 1000),
                new ItemStat(StatType.HEALTH, 1000)
        );
    }

    @Override
    public String getDescription() {
        return "An ancient blade forged in the depths of the abyss. It is said that a great explorer once brought it back, casting a curse on his village...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
