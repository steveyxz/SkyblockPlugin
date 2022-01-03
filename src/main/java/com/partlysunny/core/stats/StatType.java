package com.partlysunny.core.stats;

import org.bukkit.ChatColor;

public enum StatType {

    HEALTH("sb_health", "Health", ChatColor.RED, "❤", false, 8, true),
    STRENGTH("sb_strength", "Strength", ChatColor.RED, "❁", false, 2, false),
    DAMAGE("sb_damage", "Damage", ChatColor.RED, "❁", false, 1, false),
    DEFENSE("sb_defense", "Defense", ChatColor.GREEN, "❈", false, 9, true),
    TRUE_DEFENSE("sb_true_defense", "True Defense", ChatColor.WHITE, "❂", false, 10, true),
    SPEED("sb_speed", "Speed", ChatColor.WHITE, "✦", false, 12, true),
    INTELLIGENCE("sb_intelligence", "Intelligence", ChatColor.AQUA, "✎", false, 11, true),
    CRIT_CHANCE("sb_crit_chance", "Crit Chance", ChatColor.BLUE, "☣", true, 3, false),
    CRIT_DAMAGE("sb_crit_damage", "Crit Damage", ChatColor.BLUE, "☠", true, 4, false),
    ATTACK_SPEED("sb_attack_speed", "Bonus Attack Speed", ChatColor.YELLOW, "⚔", true, 5, false),
    FEROCITY("sb_ferocity", "Ferocity", ChatColor.RED, "⫽", false, 9, true),
    MAGIC_FIND("sb_magic_find", "Magic Find", ChatColor.AQUA, "✯", false, 6, false),
    PET_LUCK("sb_pet_luck", "Pet Luck", ChatColor.LIGHT_PURPLE, "♣", false, 16, true),
    SEA_CREATURE_CHANCE("sb_pet_luck", "Sea Creature Chance", ChatColor.DARK_AQUA, "α", true, 7, false),
    ABILITY_DAMAGE("sb_pet_luck", "Ability Damage", ChatColor.RED, "๑", true, 19, true),
    MINING_SPEED("sb_pet_luck", "Mining Speed", ChatColor.GOLD, "⸕", false, 13, true),
    PRISTINE("sb_pet_luck", "Pristine", ChatColor.DARK_PURPLE, "✧", false, 15, true),
    MINING_FORTUNE("sb_pet_luck", "Mining Fortune", ChatColor.GOLD, "☘", false, 14, true),
    FARMING_FORTUNE("sb_pet_luck", "Farming Fortune", ChatColor.GOLD, "☘", false, 17, true),
    FORAGING_FORTUNE("sb_pet_luck", "Foraging Fortune", ChatColor.GOLD, "☘", false, 18, true),

    ;

    private final String id;
    private final String displayName;
    private final ChatColor color;
    private final String symbol;
    private final boolean percent;
    //lower the higher it is on the lore
    private final int level;
    //Green section or red section
    private final boolean isGreen;

    StatType(String id, String displayName, ChatColor color, String symbol, boolean percent, int level, boolean isGreen) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
        this.symbol = symbol;
        this.percent = percent;
        this.level = level;
        this.isGreen = isGreen;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public int level() {
        return level;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public String symbol() {
        return symbol;
    }

    public ChatColor color() {
        return color;
    }

    public boolean percent() {
        return percent;
    }
}