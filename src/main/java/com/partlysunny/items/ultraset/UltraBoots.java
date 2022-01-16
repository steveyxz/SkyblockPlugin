package com.partlysunny.items.ultraset;

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

public class UltraBoots extends SkyblockItem {
    public UltraBoots(Player p) {
        super("ultraboots", true, ItemType.BOOTS, p);
    }

    public UltraBoots() {
        super("ultraboots", true, ItemType.BOOTS, null, new String[]{"ultrahelmet", "ultrachestplate", "ultraleggings", "ultraboots"});
    }

    @Override
    public boolean fraggable() {
        return true;
    }

    @Override
    public boolean starrable() {
        return true;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(10, 10, 10);
    }

    @Override
    public Material getDefaultItem() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Boots";
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
                new Stat(StatType.CRIT_CHANCE, 40),
                new Stat(StatType.CRIT_DAMAGE, 590),
                new Stat(StatType.ATTACK_SPEED, 20),
                new Stat(StatType.MAGIC_FIND, 10),
                new Stat(StatType.INTELLIGENCE, 100),
                new Stat(StatType.SPEED, 120),
                new Stat(StatType.STRENGTH, 320),
                new Stat(StatType.DEFENSE, 350),
                new Stat(StatType.MAX_HEALTH, 830)
        );
    }

    @Override
    public String getDescription() {
        return "A pair of very ultra boots :)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
