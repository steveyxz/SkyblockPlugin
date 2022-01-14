package com.partlysunny.core.items.additions.reforges;

import com.partlysunny.Skyblock;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.StatList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Reforge implements Listener {

    private static final ArrayList<String> registered = new ArrayList<>();
    private final String id;
    private final String displayName;
    private final String bonusDesc;
    private final HashMap<Rarity, StatList> statBonuses;
    private final ItemType[] canApply;
    private SkyblockItem parent;

    public Reforge(String id, String displayName, String bonusDesc, HashMap<Rarity, StatList> statBonuses, ItemType... canApply) {
        this(id, displayName, bonusDesc, statBonuses, null, canApply);
    }

    public Reforge(String id, String displayName, String bonusDesc, HashMap<Rarity, StatList> statBonuses, SkyblockItem parent, ItemType... canApply) {
        this.parent = parent;
        this.id = id;
        this.displayName = displayName;
        this.bonusDesc = bonusDesc;
        this.statBonuses = statBonuses;
        this.canApply = canApply;
        ReforgeManager.addReforge(this);
        if (!registered.contains(id)) {
            Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            registered.add(id);
        }
    }

    public ItemType[] canApply() {
        return canApply;
    }

    public boolean canApply(SkyblockItem i) {
        return List.of(canApply()).contains(i.type());
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
