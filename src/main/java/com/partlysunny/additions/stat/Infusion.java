package com.partlysunny.additions.stat;

import com.partlysunny.core.enums.BracketType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.IStatAddition;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.ChatColor;

public class Infusion extends Addition implements IStatAddition {
    public Infusion() {
        super(new AdditionInfo("infusion", 5, ModifierType.STAT, Infusion.class, 1, ChatColor.GOLD, BracketType.SQUARE));
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new ItemStat(StatType.DAMAGE, amount * 5),
                new ItemStat(StatType.HEALTH, amount * 5)
        );
    }
}
