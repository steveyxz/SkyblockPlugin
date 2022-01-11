package com.partlysunny.core.items.abilities;

public enum AbilityType {
    PASSIVE(false),
    LEFT_CLICK(true),
    RIGHT_CLICK(true),
    SHIFT_RIGHT_CLICK(true),
    SHIFT_LEFT_CLICK(true),
    PIECE_BONUS(false),
    FULL_SET_BONUS(false);

    private final boolean weapon;

    AbilityType(boolean weapon) {
        this.weapon = weapon;
    }

    public boolean weapon() {
        return weapon;
    }
}
