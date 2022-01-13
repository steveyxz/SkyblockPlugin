package com.partlysunny.core.entities;

import com.partlysunny.Skyblock;
import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.entities.stats.EntityStatType;
import com.partlysunny.core.player.BaseStatManager;
import com.partlysunny.core.player.PlayerStatManager;
import com.partlysunny.core.player.PlayerUpdater;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.EntityUtils;
import com.partlysunny.core.util.NumberUtils;
import com.partlysunny.core.util.TextUtils;
import com.partlysunny.core.util.classes.Pair;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

import static com.partlysunny.core.entities.stats.EntityStatType.getStat;

public class DamageManager implements Listener {

    public static void dealDamage(LivingEntity e, double damage, boolean isCritical, boolean showDamageIndicator) {
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
        if (showDamageIndicator)
            summonDamageIndicator(e.getLocation(), damage, isCritical, e.getHeight());
    }

    public static void summonDamageIndicator(Location central, double damage, boolean critical, double entityHeight) {
        Random r = new Random();
        double xOffset = (r.nextInt(200) / 100f) - 1;
        double yOffset = (entityHeight / 2 + ((r.nextInt((int) (entityHeight * 50)) / 100f) - entityHeight / 4)) - entityHeight / 2;
        double zOffset = (r.nextInt(200) / 100f) - 1;
        ArmorStand temp = new ArmorStand(((CraftWorld) central.getWorld()).getHandle(), central.getX() + xOffset, central.getY() - 1 + yOffset, central.getZ() + zOffset);
        temp.setInvisible(true);
        temp.setNoGravity(true);
        temp.noCulling = true;
        if (critical) {
            temp.setCustomName(new TextComponent(getCritText(TextUtils.getHealthText(damage))));
        } else {
            temp.setCustomName(new TextComponent(ChatColor.GRAY + TextUtils.getHealthText(damage)));
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
        ChatColor[] cycle = new ChatColor[]{
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

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p) {
            e.setCancelled(true);
            p.setFoodLevel(20);
            p.setSaturation(0);
        }
    }

    public static Pair<Double, Boolean> getHitDamage(Player p, boolean ignoreHand) {
        StatList stats = PlayerUpdater.getStats(p, ignoreHand);
        double damage = (5 + stats.getStat(StatType.DAMAGE)) * (1 + stats.getStat(StatType.STRENGTH) / 100);
        double multiplier = /*TODO combat level*/ stats.getStat(StatType.DAMAGE_MULTIPLIER);
        double critBonus = 1 + stats.getStat(StatType.CRIT_DAMAGE) / 100;
        boolean critical = new Random().nextInt(100) < stats.getStat(StatType.CRIT_CHANCE);
        double finalDamage = damage * multiplier * (critical ? critBonus : 1);
        if (finalDamage > 50) {
            finalDamage = finalDamage + (new Random().nextInt((int) Math.floor(finalDamage / 25)) - (int) Math.floor(finalDamage / 50));
        }
        return new Pair<>(finalDamage, critical);
    }

    public static double getHitDamageOn(Player p, double rawDamage, boolean trueDamage) {
        StatList stats = PlayerUpdater.getStats(p, false);
        if (trueDamage) {
            return rawDamage;
        }
        double defense = stats.getStat(StatType.DEFENSE);
        return rawDamage - ((defense / (defense + 100)) * rawDamage);
    }

    public static void damagePlayer(Player p, double damage) {
        UUID uniqueId = p.getUniqueId();
        PlayerStatManager.changeStat(uniqueId, StatType.HEALTH, -damage);
        double health = PlayerStatManager.getStat(uniqueId, StatType.HEALTH);
        double maxHealth = PlayerStatManager.getStat(uniqueId, StatType.MAX_HEALTH);
        updatePlayerHealthBar(p, health, maxHealth);
    }

    public static void updatePlayerHealthBar(Player p, double sbHp, double sbMaxHp) {
        if (sbHp < 1) {
            setHealth(p, 0);
        } else {
            double newHp = 20;
            if (sbMaxHp < 200) {
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
            } else {
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + NumberUtils.clamp((int) (sbMaxHp / 50f), 0, 20));
            }
            if (sbHp - 100 < 0) {
                newHp = sbHp / 5;
            } else {
                newHp += NumberUtils.clamp((int) (sbHp / 50f), 0, 20);
            }
            setHealth(p, newHp);
        }
    }

    public static void setHealth(Player p, double health) {
        net.minecraft.world.entity.player.Player player = ((CraftPlayer) p).getHandle();
        player.setHealth((float) health);
    }

    @EventHandler
    public void entityAttack(EntityDamageByEntityEvent e) {
        if (e.getDamage() <= 0) {
            return;
        }
        if (!(e.getDamager() instanceof LivingEntity damager) || e.getDamager() instanceof Player || !(e.getEntity() instanceof LivingEntity receiver) || e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SPECTRAL_ARROW) {
            return;
        }
        e.setDamage(0);
        if (!(damager instanceof Player)) {
            if (receiver instanceof Player) {
                damagePlayer((Player) receiver, getHitDamageOn((Player) receiver, EntityStatType.getStat(damager, EntityStatType.DAMAGE), false));
            } else {
                dealDamage(receiver, getStat(damager, EntityStatType.DAMAGE), false, false);
            }
        }
    }

    @EventHandler
    public void playerDie(PlayerRespawnEvent e) {
        BaseStatManager.initializeStats(e.getPlayer());
    }

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent e) {
        if (e.getDamage() <= 0) {
            return;
        }
        if (!(e.getDamager() instanceof Player p) || !(e.getEntity() instanceof LivingEntity receiver) || e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SPECTRAL_ARROW) {
            return;
        }
        e.setDamage(0);
        if (receiver instanceof Player) {
            e.setCancelled(true);
        } else {
            if ((p).getInventory().getItemInMainHand().getType() == Material.CROSSBOW || p.getInventory().getItemInMainHand().getType() == Material.BOW) {
                Pair<Double, Boolean> hitDamage = getHitDamage(p, true);
                dealDamage(receiver, hitDamage.a(), hitDamage.b(), true);
                return;
            }
            Pair<Double, Boolean> hitDamage = getHitDamage(p, false);
            dealDamage(receiver, hitDamage.a(), hitDamage.b(), true);
        }
    }

    @EventHandler
    public void entityArrowAttack(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof LivingEntity damager) || !(e.getHitEntity() instanceof LivingEntity receiver)) {
            return;
        }
        receiver.damage(0, damager);
        if (!(damager instanceof Player)) {
            if (receiver instanceof Player) {
                damagePlayer((Player) receiver, getHitDamageOn((Player) receiver, EntityStatType.getStat(damager, EntityStatType.DAMAGE), false));
            } else {
                dealDamage(receiver, getStat(damager, EntityStatType.DAMAGE), false, false);
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
        if (damager instanceof Player) {
            if (receiver instanceof Player) {
                e.setCancelled(true);
            } else {
                Pair<Double, Boolean> hitDamage = getHitDamage((Player) damager, false);
                dealDamage(receiver, hitDamage.a(), hitDamage.b(), true);
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
        ) {
            if (e.getEntity() instanceof Player) {
                damagePlayer((Player) e.getEntity(), getHitDamageOn((Player) e.getEntity(), e.getDamage() * 5, false));
            } else {
                dealDamage((LivingEntity) e.getEntity(), e.getDamage() * 5, false, true);
            }
            e.setDamage(0);
        }
    }

}
