package com.partlysunny;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.entities.DamageManager;
import com.partlysunny.core.entities.EntityUpdater;
import com.partlysunny.core.items.ItemUpdater;
import com.partlysunny.testing.TestListener;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import static com.partlysunny.abilities.AbilityRegister.registerAbilities;
import static com.partlysunny.additions.AdditionRegister.registerAdditions;
import static com.partlysunny.additions.AdditionRegister.registerReforges;
import static com.partlysunny.entities.EntityRegister.registerEntityInfos;
import static com.partlysunny.items.ItemRegister.registerItems;

public final class Skyblock extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerItems();
        registerAbilities();
        registerAdditions();
        registerReforges();
        registerEntityInfos();
        updateEverything();
        ConsoleLogger.console("Loaded Skyblock plugin on version " + getDescription().getVersion() + "...");
    }

    private void updateEverything() {
        for (Player p : getServer().getOnlinePlayers()) {
            Inventory inventory = p.getInventory();
            ItemUpdater.updateVanilla(inventory);
            ItemUpdater.idify(inventory);
            ItemUpdater.updateInventory(inventory);
        }
        for (World w : getServer().getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (!e.getType().isAlive() || e.getType() == EntityType.ARMOR_STAND || e.getType() == EntityType.PLAYER) {
                    continue;
                }
                EntityUpdater.updateStats(e);
                EntityUpdater.updateName(e);
            }
        }
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ItemUpdater(), this);
        this.getServer().getPluginManager().registerEvents(new EntityUpdater(getServer()), this);
        this.getServer().getPluginManager().registerEvents(new DamageManager(), this);
        this.getServer().getPluginManager().registerEvents(new TestListener(), this);
    }

    @Override
    public void onDisable() {
        ConsoleLogger.console("Shutting down Skyblock plugin...");
    }

}
