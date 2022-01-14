package com.partlysunny.items;

import com.partlysunny.abilities.rightClick.SmiteAbility;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UltraKatana extends SkyblockItem {
    public UltraKatana(Player p) {
        super("ultrakatana", true, ItemType.SWORD, p);
    }

    public UltraKatana() {
        super("ultrakatana", true, ItemType.SWORD, null);
    }

    @Override
    public Material getDefaultItem() {
        return Material.IRON_SWORD;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Katana";
    }

    @Override
    public AbilityList getAbilities() {
        return new AbilityList(new SmiteAbility(this));
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new Stat(StatType.CRIT_CHANCE, 20),
                new Stat(StatType.CRIT_DAMAGE, 250),
                new Stat(StatType.DAMAGE, 370),
                new Stat(StatType.MAGIC_FIND, 10),
                new Stat(StatType.INTELLIGENCE, 100),
                new Stat(StatType.STRENGTH, 320),
                new Stat(StatType.FEROCITY, 20)
        );
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
