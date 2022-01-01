package com.partlysunny.abilities.rightClick;

import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestAbility extends Ability {

    public TestAbility() {
        super("test", "Test", "test test test", 10, 10, 5, AbilityType.RIGHT_CLICK);
    }

    @Override
    public void trigger(Player player, ItemStack parent) {
        player.sendMessage("test");
    }
}
