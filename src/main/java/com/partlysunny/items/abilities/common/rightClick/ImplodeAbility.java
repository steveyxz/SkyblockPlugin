package com.partlysunny.items.abilities.common.rightClick;

import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.AbilityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ImplodeAbility extends Ability {
    public ImplodeAbility() {
        super("Implode", "Implode, somehow causing you to launch like 50 feet into the air and die of fall damage lmao :)", 10, 1, 5, AbilityType.RIGHT_CLICK);
    }

    @Override
    public void trigger(Player player, ItemStack parent) {
        player.setVelocity(new Vector(0f, 10f, 0f));
    }
}
