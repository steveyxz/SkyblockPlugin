package com.partlysunny.core.util;

import com.partlysunny.core.entities.EntityInfo;
import com.partlysunny.core.entities.EntityManager;
import com.partlysunny.core.entities.stats.EntityStatType;
import com.partlysunny.core.enums.VanillaEntityDamageAttributes;
import com.partlysunny.core.enums.VanillaEntityHealthAttributes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import static com.partlysunny.core.entities.stats.EntityStatType.*;
import static com.partlysunny.core.util.TextUtils.getHealthText;

public class EntityUtils {
    //This option will make the entity health display as one number instead of x/x (0=true, 1=false)
    public static void setBoss(byte b, Entity e) {
        DataUtils.setData("sb_boss", b, PersistentDataType.BYTE, e);
    }

    public static Boolean isBoss(Entity e) {
        Byte boss = (Byte) DataUtils.getData("sb_boss", PersistentDataType.BYTE, e);
        return boss != null ? boss == 0 : null;
    }

    public static void setId(String s, Entity e) {
        DataUtils.setData("sb_id", s, PersistentDataType.STRING, e);
    }

    public static String getId(Entity e) {
        return (String) DataUtils.getData("sb_id", PersistentDataType.STRING, e);
    }

    public static void setHealth(double s, Entity e) {
        DataUtils.setData("sb_health", s, PersistentDataType.DOUBLE, e);
    }

    public static Double getHealth(Entity e) {
        return (Double) DataUtils.getData("sb_health", PersistentDataType.DOUBLE, e);
    }

    public static void spawnEntity(net.minecraft.world.entity.Entity entity) {
        entity.level.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public static void setVanilla(Entity e) {
        setBoss((byte) 1, e);
        setId(e.getType().toString().toLowerCase(), e);
        for (EntityStatType s : EntityStatType.values()) {
            switch (s) {
                case SPEED -> setStat(e, s, 100);
                case DAMAGE -> {
                    try {
                        VanillaEntityDamageAttributes damage = VanillaEntityDamageAttributes.valueOf(e.getType().toString());
                        setStat(e, s, damage.getValue());
                    } catch (IllegalArgumentException error) {
                        setStat(e, s, 0);
                    }
                }
                case MAX_HEALTH -> {
                    try {
                        VanillaEntityHealthAttributes health = VanillaEntityHealthAttributes.valueOf(e.getType().toString());
                        setStat(e, s, health.getValue());
                        setHealth(health.getValue(), e);
                    } catch (IllegalArgumentException error) {
                        setStat(e, s, 0);
                    }
                }
                case DEFENSE -> setStat(e, s, 0);
            }
        }
    }

    public static void repairEntity(Entity e) {
        for (EntityStatType s : EntityStatType.values()) {
            if (getStat(e, s) == null) {
                switch (s) {
                    case SPEED -> setStat(e, s, 100);
                    case DAMAGE -> {
                        try {
                            VanillaEntityDamageAttributes damage = VanillaEntityDamageAttributes.valueOf(e.getType().toString());
                            setStat(e, s, damage.getValue() * 5);
                        } catch (IllegalArgumentException error) {
                            setStat(e, s, 0);
                        }
                    }
                    case MAX_HEALTH -> {
                        try {
                            VanillaEntityHealthAttributes health = VanillaEntityHealthAttributes.valueOf(e.getType().toString());
                            setStat(e, s, health.getValue() * 5);
                        } catch (IllegalArgumentException error) {
                            setStat(e, s, 0);
                        }
                    }
                    case DEFENSE -> setStat(e, s, 0);
                }
            }
        }
        if (EntityUtils.getHealth(e) == null) {
            EntityUtils.setHealth(getStat(e, MAX_HEALTH), e);
        }
        if (EntityUtils.isBoss(e) == null) {
            EntityUtils.setBoss((byte) 1, e);
        }
        if (EntityUtils.getId(e) == null) {
            EntityUtils.setId(e.getType().toString().toLowerCase(), e);
        }
    }

    public static String getDisplayName(Entity e) {
        String id = getId(e);
        if (id != null) {
            EntityInfo info = EntityManager.getEntity(id);
            double hp = getHealth(e);
            if (info == null) {
                //Vanilla entity
                return ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Lv" + "1" /*TODO make this a real lvl for each vanilla mob*/ + ChatColor.DARK_GRAY + "] " + ChatColor.RED + TextUtils.capitalizeWord(e.getType().toString().toLowerCase().replace('_', ' ')) + " " + ChatColor.GREEN + (getHealthText(hp) + "/" + VanillaEntityHealthAttributes.valueOf(e.getType().toString()).getValue() * 5) + ChatColor.RED + "❤";
            } else {
                //Custom entity
                return ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Lv" + info.level() + ChatColor.DARK_GRAY + "] " + info.color() + info.displayName() + " " + ChatColor.GREEN + (info.isBoss() ? getHealthText(hp) : getHealthText(hp) + "/" + getHealthText(info.stats().maxHealth().value())) + ChatColor.RED + "❤";
            }
        }
        return "NULL";
    }

    public static String getName(Entity e) {
        String id = getId(e);
        EntityInfo info = EntityManager.getEntity(id);
        if (info == null) {
            return TextUtils.capitalizeWord(e.getType().toString().toLowerCase().replace('_', ' '));
        } else {
            return info.displayName();
        }
    }

    public static void setEntityInfo(Entity e, String id) {
        EntityInfo info = EntityManager.getEntity(id);
        if (info != null) {
            setId(id, e);
            setBoss(info.isBoss() ? (byte) 0 : (byte) 1, e);
            setHealth(info.stats().maxHealth().value(), e);
            info.stats().apply(e);
        }
    }

}
