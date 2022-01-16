package com.partlysunny.abilities.passive.fullSet;

import com.partlysunny.Skyblock;
import com.partlysunny.core.entities.DamageManager;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.player.PlayerStatManager;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.EntityUtils;
import com.partlysunny.entities.type.WitherbornFakeWither;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static com.partlysunny.core.util.EntityUtils.getId;
import static com.partlysunny.core.util.EntityUtils.setId;

public class WitherbornAbility extends Ability {

    public WitherbornAbility() {
        this(null);
    }

    public WitherbornAbility(@Nullable SkyblockItem parent) {
        super("witherborn", "Witherborn", "Summon two withers dealing @@10@@ times your strength to mobs around you when you sneak.", 0, 0, 5, AbilityType.FULL_SET_BONUS, parent, AppliableTypeDefaults.armor);
    }

    @Override
    protected void trigger(Player player, ItemStack parent) {
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (active(e.getPlayer()) && !onCooldown(e.getPlayer())) {
            startCooldown(e.getPlayer());
            WitherbornFakeWither w = new WitherbornFakeWither(((CraftWorld) e.getPlayer().getWorld()).getHandle());
            w.noCulling = true;
            Location location = e.getPlayer().getLocation();
            w.setPos(location.getX(), location.getY(), location.getZ());
            EntityUtils.spawnEntity(w);
            Wither bukkitWither = (Wither) w.getBukkitEntity();
            setId("fakewither", bukkitWither);
            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> nearbyEntities = bukkitWither.getNearbyEntities(0, 0, 0);
                    if (nearbyEntities.size() > 0) {
                        boolean hasPlayer = false;
                        for (int i = 0; i < nearbyEntities.size(); i++) {
                            if (nearbyEntities.get(0).getType() == EntityType.PLAYER) {
                                hasPlayer = true;
                            }
                        }
                        if (!hasPlayer) {
                            bukkitWither.remove();
                            e.getPlayer().getWorld().createExplosion(w.getX(), w.getY(), w.getZ(), 0, false, false);
                            for (Entity entity : bukkitWither.getNearbyEntities(5, 5, 5)) {
                                if (!(entity.getType() == EntityType.PLAYER) && !(entity.getType() == EntityType.DROPPED_ITEM) && !(entity.getType() == EntityType.ARMOR_STAND) && !Objects.equals(getId(entity), "fakewither") && entity instanceof LivingEntity livingEntity) {
                                    DamageManager.dealDamage(livingEntity, PlayerStatManager.getStat(e.getPlayer().getUniqueId(), StatType.STRENGTH) * 10, false, true);
                                }
                            }
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(JavaPlugin.getPlugin(Skyblock.class), 0, 5);
        }
    }
}
