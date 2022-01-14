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

public class UltraLeggings extends SkyblockItem {
    public UltraLeggings(Player p) {
        super("ultraleggings", true, ItemType.LEGGINGS, p);
    }

    public UltraLeggings() {
        super("ultraleggings", true, ItemType.LEGGINGS, null, new String[]{"ultrahelmet", "ultrachestplate", "ultraleggings", "ultraboots"});
    }

    @Override
    public Material getDefaultItem() {
        return Material.LEATHER_LEGGINGS;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Leggings";
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(20, 20, 20);
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList();
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
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new Stat(StatType.CRIT_CHANCE, 30),
                new Stat(StatType.CRIT_DAMAGE, 240),
                new Stat(StatType.ATTACK_SPEED, 15),
                new Stat(StatType.INTELLIGENCE, 350),
                new Stat(StatType.SPEED, 120),
                new Stat(StatType.STRENGTH, 20),
                new Stat(StatType.DEFENSE, 500),
                new Stat(StatType.MAX_HEALTH, 530)
        );
    }

    @Override
    public String getDescription() {
        return "Very ultra leggings :)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
