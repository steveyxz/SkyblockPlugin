package com.partlysunny;

import com.partlysunny.core.ConsoleLogger;
import com.partlysunny.core.listeners.ItemUpdater;
import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.plugin.java.JavaPlugin;

import static com.partlysunny.core.registers.AbilityRegister.registerAbilities;
import static com.partlysunny.core.registers.ItemRegister.registerItems;

public final class Skyblock extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerItems();
        registerAbilities();
        NBTInjector.inject();
        ConsoleLogger.console("Loaded Skyblock plugin on version " + getDescription().getVersion() + "...");
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ItemUpdater(), this);
    }

    @Override
    public void onDisable() {
        ConsoleLogger.console("Shutting down Skyblock plugin...");
    }

}
