package com.partlysunny.core.items.additions.reforges;

import com.partlysunny.Skyblock;
import com.partlysunny.core.StatList;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.SkyblockItem;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Reforge implements Listener {

    private static final ArrayList<String> registered = new ArrayList<>();
    private final String id;
    private final String displayName;
    private final String bonusDesc;
    private final HashMap<Rarity, StatList> statBonuses;
    private SkyblockItem parent;

    public Reforge(String id, String displayName, String bonusDesc, HashMap<Rarity, StatList> statBonuses) {
        this(id, displayName, bonusDesc, statBonuses, null);
    }

    public Reforge(String id, String displayName, String bonusDesc, HashMap<Rarity, StatList> statBonuses, SkyblockItem parent) {
        this.parent = parent;
        this.id = id;
        this.displayName = displayName;
        this.bonusDesc = bonusDesc;
        this.statBonuses = statBonuses;
        ReforgeManager.addReforge(this);
        if (!registered.contains(id)) {
            Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            registered.add(id);
        }
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public String bonusDesc() {
        return bonusDesc;
    }

    public HashMap<Rarity, StatList> statBonuses() {
        return statBonuses;
    }

    public SkyblockItem parent() {
        return parent;
    }

    public void setParent(SkyblockItem parent) {
        this.parent = parent;
    }
}
