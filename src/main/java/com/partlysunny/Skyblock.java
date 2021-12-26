package com.partlysunny;

import com.partlysunny.listeners.ItemModifier;
import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.plugin.java.JavaPlugin;

import static com.partlysunny.items.ItemInit.initItems;

public final class Skyblock extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        initItems();
        NBTInjector.inject();
        ConsoleLogger.console("Loaded Skyblock plugin on version " + getDescription().getVersion() + "...");
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ItemModifier(), this);
    }

    @Override
    public void onDisable() {
        ConsoleLogger.console("Shutting down Skyblock plugin...");
    }
}
