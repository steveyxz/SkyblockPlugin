package com.partlysunny.items.abilities;

import com.partlysunny.util.AbilityUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Ability implements Listener {

    protected final String name;
    protected final String description;
    protected final int manaCost;
    protected final int soulflowCost;
    protected final int cooldown;
    protected final AbilityType type;

    public Ability(String name, String description, int manaCost, int soulflowCost, int cooldown, AbilityType type) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.soulflowCost = soulflowCost;
        this.cooldown = cooldown;
        this.type = type;
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
        if (AbilityUtils.hasAbility(e.getPlayer().getInventory().getItemInMainHand(), this.type)) {
            if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
                if (e.getPlayer().isSneaking() && type == AbilityType.SHIFT_LEFT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                } else if (type == AbilityType.LEFT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                }
            }
            if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
                if (e.getPlayer().isSneaking() && type == AbilityType.SHIFT_RIGHT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                } else if (type == AbilityType.RIGHT_CLICK) {
                    trigger(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
                }
            }
        }
    }

    public abstract void trigger(Player player, ItemStack parent);
}
