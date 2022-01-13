package com.partlysunny.items;

import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UltraHelmet extends SkyblockItem {
    public UltraHelmet(Player p) {
        super("ultrahelmet", true, ItemType.HELMET, p);
    }

    public UltraHelmet() {
        super("ultrahelmet", true, ItemType.HELMET, null, new String[]{"ultrahelmet", "ultrachestplate", "ultraleggings", null});
    }

    @Override
    public UUID skullId() {
        return UUID.fromString("c659cdd4-e436-4977-a6a7-d5518ebecfbb");
    }

    @Override
    public String skullValue() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ==";
    }

    @Override
    public Material getDefaultItem() {
        return Material.SKELETON_SKULL;
    }

    @Override
    public String getDisplayName() {
        return "Ultra Helmet";
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
                new Stat(StatType.CRIT_CHANCE, 20),
                new Stat(StatType.CRIT_DAMAGE, 250),
                new Stat(StatType.ATTACK_SPEED, 20),
                new Stat(StatType.MAGIC_FIND, 10),
                new Stat(StatType.INTELLIGENCE, 100),
                new Stat(StatType.STRENGTH, 320),
                new Stat(StatType.DEFENSE, 250),
                new Stat(StatType.MAX_HEALTH, 830)
        );
    }

    @Override
    public String getDescription() {
        return "A very ultra helmet :)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
}
