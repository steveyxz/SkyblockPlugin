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

public class NecronChestplate extends SkyblockItem {

    public NecronChestplate() {
        this(null);
    }

    public NecronChestplate(@Nullable Player owner) {
        super("necron_chestplate", true, ItemType.CHESTPLATE, owner, new String[] {"necron_helmet", "necron_chestplate", "necron_leggings", "necron_boots"});
    }

    @Override
    public Material getDefaultItem() {
        return Material.LEATHER_CHESTPLATE;
    }

    @Override
    public String getDisplayName() {
        return "Necron's Chestplate";
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
                new Stat(StatType.MAX_HEALTH, 260),
                new Stat(StatType.DEFENSE, 140),
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
