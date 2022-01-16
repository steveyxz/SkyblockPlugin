package com.partlysunny.items.necron;

import com.partlysunny.abilities.passive.fullSet.WitherbornAbility;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NecronHelmet extends SkyblockItem {

    public NecronHelmet() {
        this(null);
    }

    public NecronHelmet(@Nullable Player owner) {
        super("necron_helmet", true, ItemType.HELMET, owner, new String[] {"necron_helmet", "necron_chestplate", "necron_leggings", "necron_boots"});
    }

    @Override
    public Material getDefaultItem() {
        return Material.PLAYER_HEAD;
    }

    @Override
    public String getDisplayName() {
        return "Necron's Helmet";
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
    public StatList getStats() {
        return new StatList(
                new Stat(StatType.STRENGTH, 40),
                new Stat(StatType.CRIT_DAMAGE, 30),
                new Stat(StatType.MAX_HEALTH, 180),
                new Stat(StatType.DEFENSE, 100),
                new Stat(StatType.INTELLIGENCE, 30)
        );
    }

    @Override
    public UUID skullId() {
        return UUID.fromString("1697f861-3c0d-4629-bdd7-3cccdcfe2210");
    }

    @Override
    public String skullValue() {
        return "ewogICJ0aW1lc3RhbXAiIDogMTYwNTYyMzI0OTc5MywKICAicHJvZmlsZUlkIiA6ICIwNjEzY2I1Y2QxYjg0M2JjYjI4OTk1NWU4N2QzMGEyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJicmVhZGxvYWZzcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYmJiMmZhN2E2Y2EwODcyODBlYTBjYjU2NGI0MWVmMWFlNDA0YTE5ZjdhODEyOGQzZDI4YzUxOWE4NWUwNjNmIgogICAgfQogIH0KfQ";
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
