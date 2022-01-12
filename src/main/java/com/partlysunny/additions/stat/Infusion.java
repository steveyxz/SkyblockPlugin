package com.partlysunny.additions.stat;

import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.enums.BracketType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.items.additions.IStatAddition;
import com.partlysunny.core.stats.Stat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Infusion extends Addition implements IStatAddition {

    public Infusion() {
        this(null);
    }

    public Infusion(SkyblockItem parent) {
        super(new AdditionInfo("infusion", 1, ModifierType.STAT, Infusion.class, 1, ChatColor.GOLD, BracketType.SQUARE, AppliableTypeDefaults.armor), parent);
    }

    @Nullable
    @Override
    public String getLore(Player player) {
        return "Grants your Crit Damage and Health multiplied by your %%vanilla health%%!";
    }

    @Override
    public StatList getStats(Player player) {
        double health;
        if (player == null) {
            health = 10;
        } else {
            health = player.getHealth();
        }
        return new StatList(
                new Stat(StatType.DAMAGE, health * 5),
                new Stat(StatType.MAX_HEALTH, health * 5)
        );
    }
}
