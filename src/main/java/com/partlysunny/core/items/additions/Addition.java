package com.partlysunny.core.items.additions;

import com.partlysunny.core.items.SkyblockItem;

public abstract class Addition {

    protected final AdditionInfo type;
    protected SkyblockItem parent = null;
    protected int amount = 1;

    public Addition(AdditionInfo type) {
        this.type = type;
    }

    public Addition(AdditionInfo type, SkyblockItem parent) {
        this.type = type;
        this.parent = parent;
    }

    public SkyblockItem parent() {
        return parent;
    }

    public AdditionInfo type() {
        return type;
    }

    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
