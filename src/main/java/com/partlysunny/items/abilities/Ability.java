package com.partlysunny.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Ability implements Listener {

    protected final String name;
    protected final String description;
    protected final AbilityType type;

    public Ability(String name, String description, AbilityType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public AbilityType type() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case PASSIVE -> {
                return "";
            }
            case LEFT_CLICK -> {
                return "LEFT CLICK";
            }
            case RIGHT_CLICK -> {
                return "RIGHT CLICK";
            }
            case SHIFT_LEFT_CLICK -> {
                return "SNEAK LEFT CLICK";
            }
            case SHIFT_RIGHT_CLICK -> {
                return "SNEAK RIGHT CLICK";
            }
        }
        return "";
    }

    public abstract void trigger(Player player, ItemStack parent);
}
