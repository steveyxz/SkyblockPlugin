package com.partlysunny.core.items.additions.enchants;

import com.partlysunny.Skyblock;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.StatList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class Enchant implements Listener {

    private static final ArrayList<String> registered = new ArrayList<>();
    private final String id;
    private final int maxLevel;
    private final Class<? extends Enchant> cl;
    private final String displayName;
    private final ItemType[] canApply;
    private final boolean isUltimate;
    private int level;
    private SkyblockItem parent;

    public Enchant(String id, String displayName, int maxLevel, int level, Class<? extends Enchant> cl, boolean isUltimate, ItemType... canApply) {
        this(id, displayName, maxLevel, level, cl, null, isUltimate, canApply);
    }

    public Enchant(String id, String displayName, int maxLevel, int level, Class<? extends Enchant> cl, SkyblockItem parent, boolean isUltimate, ItemType... canApply) {
        this.parent = parent;
        this.id = id;
        this.displayName = displayName;
        this.canApply = canApply;
        this.maxLevel = maxLevel;
        this.level = level;
        this.cl = cl;
        this.isUltimate = isUltimate;
        EnchantManager.addEnchant(this);
        if (!registered.contains(id)) {
            Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            registered.add(id);
        }
    }

    public boolean isUltimate() {
        return isUltimate;
    }

    public boolean isConflicting(Enchant other) {
        return false;
    }

    public Class<? extends Enchant> cl() {
        return cl;
    }

    public boolean cannotApply(SkyblockItem item) {
        return !List.of(canApply).contains(item.type());
    }

    public int maxLevel() {
        return maxLevel;
    }

    public int level() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public abstract String description();

    public abstract StatList getBonusStats(@Nullable Player player, @Nullable Entity target);

    public SkyblockItem parent() {
        return parent;
    }

    public void setParent(SkyblockItem parent) {
        this.parent = parent;
    }
}
