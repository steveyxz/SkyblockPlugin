package com.partlysunny.testing;

import com.partlysunny.items.UltraBlade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TestListener implements Listener {
    @EventHandler
    public void temp(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            ((Player) e.getDamager()).getInventory().addItem(new UltraBlade().getSkyblockItem());
        }
    }
}
