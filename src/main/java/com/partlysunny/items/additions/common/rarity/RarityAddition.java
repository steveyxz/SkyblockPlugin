package com.partlysunny.items.additions.common.rarity;

import com.partlysunny.common.Rarity;

public abstract class RarityAddition {

    private final int change;

    public RarityAddition(int change) {
        this.change = change;
    }

    public int change() {
        return change;
    }

    public Rarity findNew(Rarity current) {
        return current.add(current, change);
    }
}