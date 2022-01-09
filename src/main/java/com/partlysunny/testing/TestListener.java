package com.partlysunny.testing;

import com.partlysunny.Skyblock;
import com.partlysunny.entities.type.SuperZombie;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestListener implements Listener {
    @EventHandler
    public void temp(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            for (Player p : JavaPlugin.getPlugin(Skyblock.class).getServer().getOnlinePlayers()) {
                SuperZombie superZombie = new SuperZombie(((CraftWorld) p.getWorld()).getHandle());
                superZombie.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
                //EntityUtils.spawnEntity(superZombie);
            }
        }

    }
}
