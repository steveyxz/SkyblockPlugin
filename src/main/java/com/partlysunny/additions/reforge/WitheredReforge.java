package com.partlysunny.additions.reforge;

import com.partlysunny.core.StatList;
import com.partlysunny.core.StatType;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.additions.reforges.Reforge;
import com.partlysunny.core.items.stats.ItemStat;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.core.util.classes.Pair;

public class WitheredReforge extends Reforge {
    public WitheredReforge() {
        super("withered", "Withered", "Increases your Crit Damage by @@2@@ for every @@Catacombs@@ level", DataUtils.getStatModifiersFrom(
                new Pair<>(Rarity.COMMON, new StatList(new ItemStat(StatType.STRENGTH, 10))),
                new Pair<>(Rarity.UNCOMMON, new StatList(new ItemStat(StatType.STRENGTH, 20))),
                new Pair<>(Rarity.RARE, new StatList(new ItemStat(StatType.STRENGTH, 50))),
                new Pair<>(Rarity.EPIC, new StatList(new ItemStat(StatType.STRENGTH, 100))),
                new Pair<>(Rarity.LEGENDARY, new StatList(new ItemStat(StatType.STRENGTH, 200))),
                new Pair<>(Rarity.MYTHIC, new StatList(new ItemStat(StatType.STRENGTH, 275)))
        ));
    }
}
