package com.partlysunny.items;

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

public class UltraChestplate extends SkyblockItem {
    public UltraChestplate(Player p) {
        super("ultrachestplate", true, ItemType.CHESTPLATE, p);
    }

    public UltraChestplate() {
        super("ultrachestplate", true, ItemType.CHESTPLATE, null, new String[]{"ultrahelmet", "ultrachestplate", "ultraleggings", "ultraboots"});
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
    public Material getDefaultItem() {
        return Material.LEATHER_CHESTPLATE;
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
    public Color getColor() {
        return Color.fromRGB(10, 10, 10);
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new Stat(StatType.CRIT_CHANCE, 40),
                new Stat(StatType.CRIT_DAMAGE, 300),
                new Stat(StatType.ATTACK_SPEED, 20),
                new Stat(StatType.MAGIC_FIND, 10),
                new Stat(StatType.INTELLIGENCE, 280),
                new Stat(StatType.STRENGTH, 120),
                new Stat(StatType.SPEED, 120),
                new Stat(StatType.DEFENSE, 420),
                new Stat(StatType.MAX_HEALTH, 620)
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
