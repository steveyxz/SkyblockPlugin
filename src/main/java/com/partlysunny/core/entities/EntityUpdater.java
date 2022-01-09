package com.partlysunny.core.entities;

import com.partlysunny.Skyblock;
import com.partlysunny.core.util.EntityUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityUpdater implements Listener {

    public EntityUpdater(Server s) {
        new ConstantUpdater(s).runTaskTimer(JavaPlugin.getPlugin(Skyblock.class), 0, 20);
    }

    public static void updateStats(Entity e) {
        EntityUtils.repairEntity(e);
    }

    public static void updateName(Entity e) {
        e.setCustomName(EntityUtils.getDisplayName(e));
        e.setCustomNameVisible(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (!entity.getType().isAlive() || entity.getType() == EntityType.ARMOR_STAND || entity.getType() == EntityType.PLAYER) {
            return;
        }
        updateStats(entity);
        updateName(entity);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity e : event.getChunk().getEntities()) {
            if (!e.getType().isAlive() || e.getType() == EntityType.ARMOR_STAND || e.getType() == EntityType.PLAYER) {
                return;
            }
            updateStats(e);
            updateName(e);
        }
    }

}

class ConstantUpdater extends BukkitRunnable {

    private final Server s;

    public ConstantUpdater(Server s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (World w : s.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (!(!e.getType().isAlive() || e.getType() == EntityType.ARMOR_STAND || e.getType() == EntityType.PLAYER)) {
                    EntityUpdater.updateStats(e);
                    EntityUpdater.updateName(e);
                }
            }
        }
    }
}
