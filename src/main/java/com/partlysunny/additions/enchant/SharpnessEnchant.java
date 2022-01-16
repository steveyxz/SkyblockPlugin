package com.partlysunny.additions.enchant;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.items.additions.enchants.Enchant;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class SharpnessEnchant extends Enchant {

    public SharpnessEnchant() {
        this(null, 1);
    }

    public SharpnessEnchant(SkyblockItem parent, Integer level) {
        super("sharpness", "Sharpness", 7, level, SharpnessEnchant.class, parent, false, AppliableTypeDefaults.meleeWeapons);
    }

    @Override
    public String description() {
        return "Increases melee damage dealt by %%" + (level() * 5) + "%";
    }

    @Override
    public StatList getBonusStats(@Nullable Player player, @Nullable Entity target) {
        return new StatList(new Stat(StatType.DAMAGE_MULTIPLIER, level() * 0.05));
    }
}
