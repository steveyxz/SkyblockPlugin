package com.partlysunny.common;

import org.bukkit.ChatColor;

public enum Rarity {

    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.LIGHT_PURPLE),
    DIVINE(ChatColor.AQUA),
    SUPREME(ChatColor.DARK_RED),
    SPECIAL(ChatColor.RED),
    VERY_SPECIAL(ChatColor.RED);

    private final ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

}
