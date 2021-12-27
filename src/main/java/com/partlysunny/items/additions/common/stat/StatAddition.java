package com.partlysunny.items.additions.common.stat;

import com.partlysunny.stats.StatList;

public abstract class StatAddition {

    private final StatList list;

    public StatAddition(StatList list) {
        this.list = list;
    }

    public StatList addStats(StatList other) {
        return list.merge(list);
    }

    public StatList list() {
        return list;
    }
}
