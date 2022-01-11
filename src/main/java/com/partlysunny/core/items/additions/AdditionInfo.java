package com.partlysunny.core.items.additions;

import com.partlysunny.core.enums.BracketType;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.SkyblockItem;
import org.bukkit.ChatColor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
    protected final ItemType[] appliableTypes;

    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, ItemType... appliableTypes) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        this.appliableTypes = appliableTypes;
        AdditionManager.addAddition(this);
    }

    //Only for stat additions
    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, int shownLevel, ChatColor color, BracketType bt, ItemType... appliableTypes) {
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
        this.appliableTypes = appliableTypes;
        AdditionManager.addAddition(this);
    }

    public AdditionInfo(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl, AdditionInfo depends, ItemType... appliableTypes) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
        this.depends = depends;
        this.appliableTypes = appliableTypes;
        AdditionManager.addAddition(this);
    }

    public ItemType[] appliableTypes() {
        return appliableTypes;
    }

    public boolean cannotApply(SkyblockItem i) {
        return !Arrays.asList(appliableTypes).contains(i.type());
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

    public Addition getNewInstance(SkyblockItem parent) {
        try {
            return cl.getDeclaredConstructor(SkyblockItem.class).newInstance(parent);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdditionInfo addObj)) {
            return false;
        }
        return id.equals(addObj.id);
    }
}
