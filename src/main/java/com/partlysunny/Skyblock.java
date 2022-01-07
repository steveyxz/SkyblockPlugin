package com.partlysunny;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.listeners.ItemUpdater;
import com.partlysunny.testing.TestListener;
import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.plugin.java.JavaPlugin;

import static com.partlysunny.abilities.AbilityRegister.registerAbilities;
import static com.partlysunny.additions.AdditionRegister.registerAdditions;
import static com.partlysunny.additions.AdditionRegister.registerReforges;
import static com.partlysunny.items.ItemRegister.registerItems;

public final class Skyblock extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerItems();
        registerAbilities();
        registerAdditions();
        registerReforges();
        NBTInjector.inject();
        ConsoleLogger.console("Loaded Skyblock plugin on version " + getDescription().getVersion() + "...");
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ItemUpdater(), this);
        this.getServer().getPluginManager().registerEvents(new TestListener(), this);
    }

    @Override
    public void onDisable() {
        ConsoleLogger.console("Shutting down Skyblock plugin...");
    }

}
