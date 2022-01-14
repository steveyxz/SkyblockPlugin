package com.partlysunny.entities;

import com.partlysunny.core.entities.EntityInfo;
import com.partlysunny.core.entities.EntityManager;
import com.partlysunny.core.entities.stats.EntityStat;
import com.partlysunny.core.entities.stats.EntityStatSet;
import com.partlysunny.core.entities.stats.EntityStatType;
import com.partlysunny.entities.type.SuperZombie;
import org.bukkit.ChatColor;

public class EntityRegister {

    public static void registerEntityInfos() {
        EntityManager.addEntity(new EntityInfo("super_zombie", "Super Zombie Defender", ChatColor.GOLD + "" + ChatColor.BOLD, 5, true, new EntityStatSet(
                new EntityStat(EntityStatType.MAX_HEALTH, 100000),
                new EntityStat(EntityStatType.DEFENSE, 300),
                new EntityStat(EntityStatType.SPEED, 200),
                new EntityStat(EntityStatType.DAMAGE, 300)), SuperZombie.class
        ));
    }

}
