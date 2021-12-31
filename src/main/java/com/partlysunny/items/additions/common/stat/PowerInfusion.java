package com.partlysunny.items.additions.common.stat;

import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionType;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;

public class PowerInfusion extends Addition implements IStatAddition {
    public PowerInfusion() {
        super(AdditionType.POWER_INFUSION);
    }

    @Override
    public StatList getStats() {
        return new StatList(
                new ItemStat(StatType.DAMAGE, amount() * 100),
                new ItemStat(StatType.INTELLIGENCE, amount() * 100),
                new ItemStat(StatType.DEFENSE, amount() * 100),
                new ItemStat(StatType.STRENGTH, amount() * 100),
                new ItemStat(StatType.MAGIC_FIND, amount() * 10),
                new ItemStat(StatType.CRIT_DAMAGE, amount() * 100)
        );
    }
}
