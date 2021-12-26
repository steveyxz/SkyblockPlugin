package com.partlysunny.items.additions;

public abstract class Addition {

    protected final AdditionType type;
    protected int amount = 1;

    public Addition(AdditionType type) {
        this.type = type;
    }

    public AdditionType type() {
        return type;
    }

    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
