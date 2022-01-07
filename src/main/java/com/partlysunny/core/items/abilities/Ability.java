package com.partlysunny.core.items.abilities;

import com.partlysunny.Skyblock;
import com.partlysunny.core.util.AbilityUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public abstract class Ability implements Listener {

    private static final ArrayList<String> registered = new ArrayList<>();
    protected final String name;
    protected final String description;
    protected final int manaCost;
    protected final int soulflowCost;
    protected final int cooldown;
    protected final AbilityType type;
    protected final String id;
    protected boolean onCooldown = false;
    private String cooldownMessage = ChatColor.RED + "This ability is on cooldown!";
    private int cooldownRemaining = -1;

    public Ability(String id, String name, String description, int manaCost, int soulflowCost, int cooldown, AbilityType type) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.manaCost = manaCost;
        this.soulflowCost = soulflowCost;
        this.cooldown = cooldown;
        this.type = type;
        if (!registered.contains(id)) {
            Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            registered.add(id);
        }
    }

    public Ability(String id, String name, String description, int manaCost, int soulflowCost, int cooldown, AbilityType type, String cooldownMessage) {
        this(id, name, description, manaCost, soulflowCost, cooldown, type);
        this.cooldownMessage = cooldownMessage;
    }

    public int soulflowCost() {
        return soulflowCost;
    }

    public int cooldown() {
        return cooldown;
    }

    public String id() {
        return id;
    }

    public String cooldownMessage() {
        return cooldownMessage;
    }

    public int cooldownRemaining() {
        return cooldownRemaining;
    }

    public int manaCost() {
        return manaCost;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public AbilityType type() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case PASSIVE -> {
                return "";
            }
            case LEFT_CLICK -> {
                return "LEFT CLICK";
            }
            case RIGHT_CLICK -> {
                return "RIGHT CLICK";
            }
            case SHIFT_LEFT_CLICK -> {
                return "SNEAK LEFT CLICK";
            }
            case SHIFT_RIGHT_CLICK -> {
                return "SNEAK RIGHT CLICK";
            }
        }
        return "";
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (AbilityUtils.hasAbility(e.getPlayer().getInventory().getItemInMainHand(), this.type)) {
            if (onCooldown()) {
                e.getPlayer().sendMessage(cooldownMessage);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
                return;
            }
            if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
                if (e.getPlayer().isSneaking() && type == AbilityType.SHIFT_LEFT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                    startCooldown();
                } else if (type == AbilityType.LEFT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                    startCooldown();
                }
            } else if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
                if (e.getPlayer().isSneaking() && type == AbilityType.SHIFT_RIGHT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                    startCooldown();
                } else if (type == AbilityType.RIGHT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                    startCooldown();
                }
            }
        }
    }

    protected boolean onCooldown() {
        return onCooldown;
    }

    protected void startCooldown() {
        onCooldown = true;
        cooldownRemaining = cooldown;
        new BukkitRunnable() {
            @Override
            public void run() {
                cooldownRemaining--;
                if (cooldownRemaining == -1) {
                    onCooldown = false;
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Skyblock.class), 0, 20);
    }

    protected abstract void trigger(Player player, ItemStack parent);
}
