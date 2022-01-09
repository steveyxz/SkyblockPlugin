package com.partlysunny.core.entities;

import com.partlysunny.Skyblock;
import com.partlysunny.core.entities.stats.EntityStatType;
import com.partlysunny.core.util.EntityUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static com.partlysunny.core.entities.stats.EntityStatType.getStat;

public class DamageManager implements Listener {

    @EventHandler
    public void entityAttack(EntityDamageByEntityEvent e) {
        if (e.getDamage() < 1) {
            return;
        }
        e.setDamage(0);
        if (!(e.getDamager() instanceof LivingEntity damager) || !(e.getEntity() instanceof LivingEntity receiver) || e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SPECTRAL_ARROW) {
            return;
        }
        if (damager.getType() != EntityType.PLAYER) {
            if (receiver.getType() == EntityType.PLAYER) {
                //TODO player damaged, implement playerstats first
            } else {
                dealDamage(receiver, getStat(damager, EntityStatType.DAMAGE));
            }
        }
    }

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent e) {
        if (e.getDamage() < 1) {
            return;
        }
        e.setDamage(0);
        if (!(e.getDamager() instanceof LivingEntity damager) || !(e.getEntity() instanceof LivingEntity receiver) || e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SPECTRAL_ARROW) {
            return;
        }
        if (damager.getType() == EntityType.PLAYER) {
            if (receiver.getType() == EntityType.PLAYER) {
                e.setCancelled(true);
            } else {
                dealDamage(receiver, getHitDamage((Player) damager));
            }
        }
    }
    @EventHandler
    public void entityArrowAttack(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof LivingEntity damager) || !(e.getHitEntity() instanceof LivingEntity receiver)) {
            return;
        }
        receiver.damage(0, damager);
        if (damager.getType() != EntityType.PLAYER) {
            if (receiver.getType() == EntityType.PLAYER) {
                //TODO player damaged, implement playerstats first
            } else {
                dealDamage(receiver, getStat(damager, EntityStatType.DAMAGE));
            }
        }
        e.getEntity().remove();
        e.setCancelled(true);
    }

    @EventHandler
    public void playerArrowAttack(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof LivingEntity damager) || !(e.getHitEntity() instanceof LivingEntity receiver)) {
            return;
        }
        receiver.damage(0, damager);
        if (damager.getType() == EntityType.PLAYER) {
            if (receiver.getType() == EntityType.PLAYER) {
                //TODO player damaged, implement playerstats first
            } else {
                dealDamage(receiver, getHitDamage((Player) damager));
            }
        }
        e.getEntity().remove();
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void naturalDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity &&
                e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK &&
                e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE &&
                e.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK &&
                e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
        )
        {
            dealDamage((LivingEntity) e.getEntity(), e.getDamage() * 5);
            e.setDamage(0);
        }
    }

    public static void dealDamage(LivingEntity e, double damage) {
        if (e.getType() == EntityType.ARMOR_STAND || e.getType() == EntityType.PLAYER) {
            return;
        }
        if (EntityUtils.getHealth(e) == null) {
            EntityUtils.repairEntity(e);
        }
        EntityUtils.setHealth(EntityUtils.getHealth(e) - damage, e);
        if (EntityUtils.getHealth(e) < 1) {
            e.setHealth(0);
            EntityUtils.setHealth(0, e);
        }
        e.setCustomName(EntityUtils.getDisplayName(e));
        summonDamageIndicator(e.getLocation(), damage, true, e.getHeight());
    }

    public static void summonDamageIndicator(Location central, double damage, boolean critical, double entityHeight) {
        Random r = new Random();
        double xOffset = (r.nextInt(200) / 100f) - 1;
        double yOffset = (entityHeight / 2 + ((r.nextInt((int) (entityHeight*50))/100f)-entityHeight/4)) - entityHeight/2;
        double zOffset = (r.nextInt(200) / 100f) - 1;
        ArmorStand temp = new ArmorStand(((CraftWorld) central.getWorld()).getHandle(), central.getX() + xOffset, central.getY() - 1 + yOffset, central.getZ() + zOffset);
        temp.setInvisible(true);
        temp.setNoGravity(true);
        temp.noCulling = true;
        if (critical) {
            temp.setCustomName(new TextComponent(getCritText(EntityUtils.getHealthText(damage))));
        } else {
            temp.setCustomName(new TextComponent(EntityUtils.getHealthText(damage)));
        }
        temp.setCustomNameVisible(true);
        EntityUtils.spawnEntity(temp);
        new BukkitRunnable() {
            @Override
            public void run() {
                temp.kill();
            }
        }.runTaskLater(JavaPlugin.getPlugin(Skyblock.class), 40);
    }

    public static String getCritText(String before) {
        StringBuilder temp = new StringBuilder();
        temp.append(ChatColor.WHITE).append("✧");
        ChatColor[] cycle = new ChatColor[] {
                ChatColor.WHITE,
                ChatColor.YELLOW,
                ChatColor.GOLD,
                ChatColor.RED,
        };
        int count = 0;
        for (char c : before.toCharArray()) {
            if (before.length() > 2) {
                if (count > 3) {
                    count = 0;
                }
                temp.append(cycle[count]).append(c);
            } else {
                temp.append(cycle[3 - count]).append(c);
            }
            count++;
        }
        temp.append(ChatColor.WHITE).append("✧");
        return temp.toString();
    }

    public static double getHitDamage(Player p) {
        //TODO player stats + damage
        return 10;
    }

    public static double getHitDamageOn(Player p, double rawDamage) {
        return rawDamage;
    }

}
