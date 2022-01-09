package com.partlysunny.items;

import com.partlysunny.abilities.rightClick.SmiteAbility;
import com.partlysunny.core.StatList;
import com.partlysunny.core.StatType;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.stats.ItemStat;
import org.bukkit.Material;

public class UltraBlade extends SkyblockItem {
    public UltraBlade() {
        super("ultrablade", true, ItemType.SWORD);
    }

    @Override
    public Material getDefaultItem() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Blade Of Doom";
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList(new SmiteAbility());
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new ItemStat(StatType.DAMAGE, 480),
                new ItemStat(StatType.CRIT_CHANCE, 40),
                new ItemStat(StatType.CRIT_DAMAGE, 300),
                new ItemStat(StatType.ATTACK_SPEED, 20),
                new ItemStat(StatType.FEROCITY, 20),
                new ItemStat(StatType.MAGIC_FIND, 10),
                new ItemStat(StatType.INTELLIGENCE, 280),
                new ItemStat(StatType.STRENGTH, 120)
        );
    }

    @Override
    public String getDescription() {
        return "An ancient blade forged in the depths of the abyss. It is said to be created by the precursors as a last attempt to defeat their sworn enemy...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
