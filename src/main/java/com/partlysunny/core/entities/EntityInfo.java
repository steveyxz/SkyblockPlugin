package com.partlysunny.core.entities;

import com.partlysunny.core.entities.stats.EntityStatSet;
import org.bukkit.ChatColor;

public record EntityInfo(String id, String displayName, ChatColor color, int level, boolean isBoss,
                         EntityStatSet stats) {
}
