package com.partlysunny.core.player;

import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static com.partlysunny.Skyblock.configManager;

public class BaseStatManager {

    private static final YamlConfiguration baseStats = configManager.getConfig("baseStats");
    public static final Map<UUID, Boolean> hasInitializedChangableStats = new HashMap<>();
    private static final HashMap<String, Double> defaultStats = new HashMap<>() {{
        put(StatType.HEALTH.toString(), 100D);
        put(StatType.DAMAGE_MULTIPLIER.toString(), 1D);
        put(StatType.HEALTH_REGEN_SPEED.toString(), 1.5D);
        put(StatType.MANA_REGEN_SPEED.toString(), 2D);
        put(StatType.MAX_HEALTH.toString(), 100D);
        put(StatType.DAMAGE.toString(), 1D);
        put(StatType.SPEED.toString(), 100D);
        put(StatType.MANA.toString(), 100D);
        put(StatType.INTELLIGENCE.toString(), 100D);
        put(StatType.CRIT_CHANCE.toString(), 30D);
        put(StatType.CRIT_DAMAGE.toString(), 50D);
        put(StatType.SEA_CREATURE_CHANCE.toString(), 20D);
    }};

    //cuz im too lazy to write them all down in the constructor
    public static void repairDefaultStats() {
        for (StatType s : StatType.values()) {
            if (!defaultStats.containsKey(s.toString())) {
                defaultStats.put(s.toString(), 0D);
            }
        }
    }

    public static void initializeBaseStats(JavaPlugin plugin) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            initializeStats(p);
        }
    }

    public static void initializeStats(Player p) {
        UUID id = p.getUniqueId();
        hasInitializedChangableStats.put(id, false);
        PlayerStatManager.playerStats.put(id, new StatList(getStatsOf(id).toArray(new Stat[0])));
    }

    public static void removeStats(Player p) {
        PlayerStatManager.playerStats.remove(p.getUniqueId());
    }

    public static List<Stat> getStatsOf(UUID id) {
        List<Stat> list = new ArrayList<>();
        if (!baseStats.contains(id.toString())) {
            addDefaultPlayer(id);
        }
        for (StatType t : StatType.values()) {
            double amount = baseStats.getDouble(id + "." + t.toString());
            if (!baseStats.contains(id + "." + t)) {
                baseStats.set(id + "." + t, defaultStats.get(t.toString()));
                configManager.saveConfig("baseStats", baseStats);
                amount = defaultStats.get(t.toString());
            }
            list.add(new Stat(t, amount));
        }
        return list;
    }

    public static StatList getStatListOf(UUID id) {
        StatList list = new StatList();
        if (!baseStats.contains(id.toString())) {
            addDefaultPlayer(id);
        }
        for (StatType t : StatType.values()) {
            double amount = baseStats.getDouble(id + "." + t.toString());
            list.addStat(new Stat(t, amount));
        }
        return list;
    }

    public static void addDefaultPlayer(UUID id) {
        for (String s : defaultStats.keySet()) {
            baseStats.set(id + "." + s, defaultStats.get(s));
        }
        configManager.saveConfig("baseStats", baseStats);
    }


}
