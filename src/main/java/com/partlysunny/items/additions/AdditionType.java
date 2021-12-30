package com.partlysunny.items.additions;

import com.partlysunny.items.ModifierType;
import com.partlysunny.items.additions.common.ability.Implode;
import com.partlysunny.items.additions.common.stat.PotatoBook;

import java.util.Objects;

public enum AdditionType {


    POTATO_BOOK("potato_book", 10, ModifierType.STAT, PotatoBook.class),
    IMPLODE("implode", 1, ModifierType.ABILITY, Implode.class);

    private final Class<? extends Addition> cl;
    private String id;
    private int maxAdditions;
    private ModifierType type;
    private AdditionType depends = null;

    AdditionType(String id, int maxAdditions, ModifierType type, Class<? extends Addition> cl) {
        this.id = id;
        this.maxAdditions = maxAdditions;
        this.type = type;
        this.cl = cl;
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
