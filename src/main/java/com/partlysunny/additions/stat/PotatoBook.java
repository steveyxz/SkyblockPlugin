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

public class PotatoBook extends Addition implements IStatAddition {
    public PotatoBook() {
        super(new AdditionInfo("potatobook", 10, ModifierType.STAT, PotatoBook.class, 0, ChatColor.YELLOW, BracketType.REGULAR));
    }

    @Override
    public StatList getStats() {
        return new StatList(new ItemStat(StatType.DAMAGE, amount * 2), new ItemStat(StatType.STRENGTH, amount * 2));
    }
}
