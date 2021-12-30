package com.partlysunny.items.custom;

import com.partlysunny.enums.Rarity;
import com.partlysunny.items.abilities.AbilityList;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;
import org.bukkit.Material;

public class Hyperion extends SkyblockItem {

    public Hyperion() {
        super("hyperion");
    }

    @Override
    public Material getDefaultItem() {
        return Material.IRON_SWORD;
    }

    @Override
    public String getDisplayName() {
        return "Hyperion";
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
                new ItemStat(StatType.DAMAGE, 10000),
                new ItemStat(StatType.INTELLIGENCE, 10000),
                new ItemStat(StatType.CRIT_CHANCE, 100),
                new ItemStat(StatType.CRIT_DAMAGE, 10000),
                new ItemStat(StatType.MAGIC_FIND, 1000)
        );
    }

    @Override
    public String getDescription() {
        return "An epic gamer sword forged in the depths of epic gamer hell made just for epic gamers like me :)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.MYTHIC;
    }
}
