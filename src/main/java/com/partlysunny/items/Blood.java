package com.partlysunny.items;

import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.StatList;
import org.bukkit.Material;

public class Blood extends SkyblockItem {
    public Blood() {
        super("blood", false);
    }

    @Override
    public Material getDefaultItem() {
        return Material.RED_DYE;
    }

    @Override
    public String getDisplayName() {
        return "Blood";
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList();
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }

    @Override
    public StatList getStats() {
        return new StatList();
    }

    @Override
    public String getDescription() {
        return "Mysterious red liquid...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
}
