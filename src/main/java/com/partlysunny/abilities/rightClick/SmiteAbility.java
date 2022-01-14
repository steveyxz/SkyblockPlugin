package com.partlysunny.abilities.rightClick;

import com.partlysunny.core.entities.DamageManager;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.player.PlayerStatManager;
import com.partlysunny.core.stats.StatType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SmiteAbility extends Ability {

    public SmiteAbility() {
        this(null);
    }

    public SmiteAbility(SkyblockItem parent) {
        super("smite", "Smite", "Strike down enemies around you, dealing @@5@@ times your Strength", 160, 0, 1, AbilityType.RIGHT_CLICK, parent, AppliableTypeDefaults.meleeWeapons);
    }

    @Override
    protected void trigger(Player player, ItemStack parent) {
        double damage = PlayerStatManager.getStat(player.getUniqueId(), StatType.STRENGTH) * 5;
        for (Entity e : player.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity) {
                e.getWorld().strikeLightningEffect(e.getLocation());
                DamageManager.dealDamage((LivingEntity) e, damage, false, true);
            }
        }
    }
}
