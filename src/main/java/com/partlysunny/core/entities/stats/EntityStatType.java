package com.partlysunny.core.entities.stats;

import com.partlysunny.core.util.DataUtils;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

public enum EntityStatType {
    MAX_HEALTH("sb_max_health"),
    DEFENSE("sb_defense"),
    SPEED("sb_speed"),
    DAMAGE("sb_damage");

    private final String id;

    EntityStatType(String id) {
        this.id = id;
    }

    public static void setStat(Entity e, EntityStatType s, double v) {
        DataUtils.setData(s.id, v, PersistentDataType.DOUBLE, e);
    }

    public static Double getStat(Entity e, EntityStatType s) {
        return (Double) DataUtils.getData(s.id, PersistentDataType.DOUBLE, e);
    }

    public String id() {
        return id;
    }
}
