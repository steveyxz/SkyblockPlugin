package com.partlysunny.core.items.additions;

import com.partlysunny.core.StatList;

public interface IStatAddition {

    //CALCULATE THE FINAL STATS, NOT JUST FOR ONE ADDITION (include amount)
    //TODO make stat additions be able to be shown in lore (like hyperion increases your stats depending on your cata lvl)
    StatList getStats();

}
