package com.partlysunny.items.necron;

import com.partlysunny.abilities.passive.fullSet.WitherbornAbility;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class NecronLeggings extends SkyblockItem {

    public NecronLeggings() {
        this(null);
    }

    public NecronLeggings(@Nullable Player owner) {
        super("necron_leggings", true, ItemType.LEGGINGS, owner, new String[]{"necron_helmet", "necron_chestplate", "necron_leggings", "necron_boots"});
    }

    @Override
    public Material getDefaultItem() {
        return Material.LEATHER_LEGGINGS;
    }

    @Override
    public String getDisplayName() {
        return "Necron's Leggings";
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList(new WitherbornAbility(this));
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(166, 59, 51);
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new Stat(StatType.STRENGTH, 40),
                new Stat(StatType.CRIT_DAMAGE, 30),
                new Stat(StatType.MAX_HEALTH, 230),
                new Stat(StatType.DEFENSE, 125),
                new Stat(StatType.INTELLIGENCE, 10)
        );
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
