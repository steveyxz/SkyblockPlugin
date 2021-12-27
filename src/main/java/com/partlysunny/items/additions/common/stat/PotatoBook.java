package com.partlysunny.items.additions.common.stat;

import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionType;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;

public class PotatoBook extends Addition implements IStatAddition {

    public PotatoBook() {
        super(AdditionType.POTATO_BOOK);
    }

    @Override
    public StatList getStats() {
        return new StatList(new ItemStat(StatType.DAMAGE, amount * 2), new ItemStat(StatType.STRENGTH, amount * 2));
    }
}
