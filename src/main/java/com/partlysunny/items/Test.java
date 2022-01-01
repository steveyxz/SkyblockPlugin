package com.partlysunny.items;

import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.StatList;
import org.bukkit.Material;

public class Test extends SkyblockItem {
    public Test() {
        super("test");
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
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList();
    }

    @Override
    public String getDescription() {
        return "You suck man";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
    }
}
