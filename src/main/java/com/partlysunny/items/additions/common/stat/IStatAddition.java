package com.partlysunny.items.additions.common.stat;

import com.partlysunny.stats.StatList;

public interface IStatAddition {

    //TODO make getStats only require calculations for one, rather than many (to avoid confusion)
    StatList getStats();

}
