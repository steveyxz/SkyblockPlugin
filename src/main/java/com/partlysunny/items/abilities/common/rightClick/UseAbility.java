package com.partlysunny.items.abilities.common.rightClick;

import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.AbilityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UseAbility extends Ability {
    public UseAbility() {
        super("Add One", "Add one use to this item's nbt", 10, 1, 10, AbilityType.RIGHT_CLICK);
    }

    @Override
    public void trigger(Player player, ItemStack parent) {

    }
}
