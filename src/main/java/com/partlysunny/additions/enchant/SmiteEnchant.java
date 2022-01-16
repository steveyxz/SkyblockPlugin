package com.partlysunny.additions.enchant;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.items.additions.enchants.Enchant;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmiteEnchant extends Enchant {

    public static final EntityType[] undeadCreatures = new EntityType[]{
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.SKELETON_HORSE,
            EntityType.SKELETON,
            EntityType.WITHER_SKELETON,
            EntityType.WITHER,
            EntityType.STRAY,
            EntityType.DROWNED,
            EntityType.ZOGLIN
    };

    public SmiteEnchant() {
        this(null, 1);
    }

    public SmiteEnchant(SkyblockItem parent, Integer level) {
        super("smite", "Smite", 7, level, SmiteEnchant.class, parent, false, AppliableTypeDefaults.meleeWeapons);
    }

    @Override
    public String description() {
        return "Increases damage dealt to Zombies, Zombie Pigmen, Withers, and Skeletons by %%" + (level() * 8) + "%";
    }

    @Override
    public StatList getBonusStats(@Nullable Player player, @Nullable Entity target) {
        if (target == null) {
            return new StatList();
        }
        if (List.of(undeadCreatures).contains(target.getType())) {
            return new StatList(new Stat(StatType.DAMAGE_MULTIPLIER, level() * 0.08));
        }
        return new StatList();
    }
}
