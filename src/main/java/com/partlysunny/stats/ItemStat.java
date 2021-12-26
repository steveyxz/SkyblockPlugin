package com.partlysunny.stats;

public class ItemStat {

    private final StatType type;
    private double value;

    public ItemStat(StatType type, double value) {
        this.type = type;
        this.value = value;
    }

    public StatType type() {
        return type;
    }

    public double value() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
