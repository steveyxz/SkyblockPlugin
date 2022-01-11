package com.partlysunny.items;

import com.partlysunny.core.StatList;
import com.partlysunny.core.StatType;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.stats.ItemStat;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UltraLeggings extends SkyblockItem {
    public UltraLeggings(Player p) {
        super("ultraleggings", true, ItemType.LEGGINGS, p);
    }

    public UltraLeggings() {
        super("ultraleggings", true, ItemType.LEGGINGS, null, new String[] {null, "ultrachestplate", "ultraleggings", null});
    }

    @Override
    public Material getDefaultItem() {
        return Material.NETHERITE_LEGGINGS;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Leggings";
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
                new ItemStat(StatType.CRIT_CHANCE, 30),
                new ItemStat(StatType.CRIT_DAMAGE, 240),
                new ItemStat(StatType.ATTACK_SPEED, 15),
                new ItemStat(StatType.INTELLIGENCE, 350),
                new ItemStat(StatType.STRENGTH, 20),
                new ItemStat(StatType.DEFENSE, 500),
                new ItemStat(StatType.HEALTH, 530)
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
