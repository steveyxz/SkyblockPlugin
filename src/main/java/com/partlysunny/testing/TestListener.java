package com.partlysunny.testing;

import com.partlysunny.core.items.additions.AdditionManager;
import com.partlysunny.items.UltraBlade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TestListener implements Listener {
    @EventHandler
    public void temp(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            UltraBlade ultraBlade = new UltraBlade();
            ultraBlade.setReforge("withered");
            ultraBlade.statAdditions().addAdditions(AdditionManager.getAddition("infusion"), 3);
            ultraBlade.statAdditions().addAdditions(AdditionManager.getAddition("potatobook"), 6);
            ((Player) e.getDamager()).getInventory().addItem(ultraBlade.getSkyblockItem());
        }
    }
}
