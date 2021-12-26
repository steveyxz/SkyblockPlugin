package com.partlysunny.items.lore.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Ability implements Listener {

    protected final String name;
    protected final AbilityType type;

    public Ability(String name, AbilityType type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public AbilityType type() {
        return type;
    }

    public abstract void trigger(Player player, ItemStack parent);
}
