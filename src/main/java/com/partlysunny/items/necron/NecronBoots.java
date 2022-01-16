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

public class NecronBoots extends SkyblockItem {

    public NecronBoots() {
        this(null);
    }

    public NecronBoots(@Nullable Player owner) {
        super("necron_boots", true, ItemType.BOOTS, owner, new String[]{"necron_helmet", "necron_chestplate", "necron_leggings", "necron_boots"});
    }

    @Override
    public Material getDefaultItem() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public String getDisplayName() {
        return "Necron's Boots";
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
                new Stat(StatType.MAX_HEALTH, 145),
                new Stat(StatType.DEFENSE, 85),
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
