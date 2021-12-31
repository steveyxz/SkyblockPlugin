package com.partlysunny.items.abilities.common.rightClick;

import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.AbilityType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DestroyAbility extends Ability {
    public DestroyAbility() {
        super("Destroy", "Destroy and smite all enemies around you...", 10, 10, 5, AbilityType.RIGHT_CLICK);
    }

    @Override
    public void trigger(Player player, ItemStack parent) {
        super.trigger(player, parent);
        System.out.println("test");
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            player.getWorld().strikeLightning(entity.getLocation());
            player.damage(20, entity);
        }
        player.sendMessage(ChatColor.GOLD + "ZZZZZZSSSSPPP");
    }
}
