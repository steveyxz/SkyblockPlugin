package com.partlysunny.abilities.passive.fullSet;

import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GlowAbility extends Ability {
    public GlowAbility(@Nullable SkyblockItem parent) {
        super("glow", "Glow", "Send glow particles around you!", AbilityType.FULL_SET_BONUS, parent, AppliableTypeDefaults.armor);
    }

    @Override
    protected void trigger(Player player, ItemStack parent) {
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (active(event.getPlayer())) {
            Random random = new Random();
            double xOffset = (random.nextInt(200) / 100f) - 1;
            double yOffset = (random.nextInt(100) / 100f) + 0.5;
            double zOffset = (random.nextInt(200) / 100f) - 1;
            Location location = event.getPlayer().getLocation();
            event.getPlayer().getWorld().spawnParticle(Particle.GLOW, new Location(event.getPlayer().getWorld(), location.getX() + xOffset, location.getY() + yOffset, location.getZ() + zOffset), 1);
        }
    }
}
