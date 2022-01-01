package com.partlysunny.core.items.additions;

import com.partlysunny.core.enums.BracketType;
import com.partlysunny.core.items.ModifierType;
import org.bukkit.ChatColor;

public class AdditionInfo {

    private final Class<? extends Addition> cl;
    private String id;
    private int maxAdditions;
    private ModifierType type;
    private AdditionInfo depends = null;
    //Lower equals shown first
    private int shownLevel;
    private ChatColor color;
    private BracketType bt;

    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        AdditionManager.addAddition(this);
    }

    //Only for stat additions
    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, int shownLevel, ChatColor color, BracketType bt) {
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
        AdditionManager.addAddition(this);
    }

    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, AdditionInfo depends) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        this.depends = depends;
        AdditionManager.addAddition(this);
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

    public AdditionInfo depends() {
        return depends;
    }

    public void setDepends(AdditionInfo depends) {
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
