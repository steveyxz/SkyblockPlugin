package com.partlysunny.abilities.rightClick;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SmiteAbility extends Ability {

    public SmiteAbility() {
        super("smite", "Smite", "Strike down enemies around you with the power infused inside the ultra blade", AbilityType.RIGHT_CLICK, null, AppliableTypeDefaults.meleeWeapons);
    }

    public SmiteAbility(SkyblockItem parent) {
        super("smite", "Smite", "Strike down enemies around you with the power infused inside the ultra blade", AbilityType.RIGHT_CLICK, parent, AppliableTypeDefaults.meleeWeapons);
    }

    @Override
    protected void trigger(Player player, ItemStack parent) {
        for (Entity e : player.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity) {
                e.getWorld().strikeLightningEffect(e.getLocation());
                ((LivingEntity) e).damage(10, player);
            }
        }
    }
}
