package com.partlysunny.core.items.additions;

import com.partlysunny.core.stats.StatList;

public interface IStatAddition {

    //CALCULATE THE FINAL STATS, NOT JUST FOR ONE ADDITION (include amount)
    StatList getStats();

}
