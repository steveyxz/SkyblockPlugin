package com.partlysunny.items.additions;

import com.partlysunny.enums.BracketType;
import com.partlysunny.items.ModifierType;
import com.partlysunny.items.additions.common.ability.Destroy;
import com.partlysunny.items.additions.common.stat.PowerInfusion;
import org.bukkit.ChatColor;

import java.util.Objects;

public enum AdditionType {

    DESTROY("destroy", 1, ModifierType.ABILITY, Destroy.class),
    POWER_INFUSION("power_infusion", 10, ModifierType.STAT, PowerInfusion.class, 1, ChatColor.AQUA, BracketType.SQUARE)
    ;

    private final Class<? extends Addition> cl;
    private String id;
    private int maxAdditions;
    private ModifierType type;
    private AdditionType depends = null;
    //Lower equals shown first
    private int shownLevel;
    private ChatColor color;
    private BracketType bt;

    AdditionType(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
    }

    //Only for stat additions
    AdditionType(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, int shownLevel, ChatColor color, BracketType bt) {
        if (type != ModifierType.STAT) {
            throw new IllegalArgumentException("This constructor only accepts stat modifier additions");
        }
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        this.color = color;
        this.shownLevel = shownLevel;
        this.bt = bt;
    }

    AdditionType(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, AdditionType depends) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        this.depends = depends;
    }

    public static AdditionType getTypeFromId(String id) {
        for (AdditionType t : values()) {
            if (Objects.equals(id, t.id)) {
                return t;
            }
        }
        return null;
    }

    public Integer shownLevel() {
        return shownLevel;
    }

    public ChatColor color() {
        return color;
    }

    public BracketType bt() {
        return bt;
    }

    public Class<? extends Addition> cl() {
        return cl;
    }

    public AdditionType depends() {
        return depends;
    }

    public void setDepends(AdditionType depends) {
        this.depends = depends;
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int maxAdditions() {
        return maxAdditions;
    }

    public void setMaxAdditions(int maxAdditions) {
        this.maxAdditions = maxAdditions;
    }

    public ModifierType type() {
        return type;
    }

    public void setType(ModifierType type) {
        this.type = type;
    }

}
