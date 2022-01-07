package com.partlysunny.core.items;

public enum ItemType {
    ITEM("", false, false),
    DUNGEON_ITEM("dungeon item", false, true),
    SWORD("sword", true, false),
    DUNGEON_SWORD("dungeon sword", true, true),
    BOW("bow", true, false),
    DUNGEON_BOW("dungeon bow", true, true),
    HELMET("helmet", true, false),
    CHESTPLATE("chestplate", true, false),
    LEGGINGS("leggings", true, false),
    BOOTS("boots", true, false),
    DUNGEON_HELMET("dungeon helmet", true, true),
    DUNGEON_CHESTPLATE("dungeon chestplate", true, true),
    DUNGEON_LEGGINGS("dungeon leggings", true, true),
    DUNGEON_BOOTS("dungeon boots", true, true),
    AXE("axe", true, false),
    PICKAXE("pickaxe", true, false),
    HOE("hoe", true, false),
    SHOVEL("shovel", true, false),
    DRILL("drill", true, false),
    GAUNTLET("gauntlet", true, false),
    ACCESSORY("accessory", true, false),
    DUNGEON_ACCESSORY("dungeon accessory", true, true),
    HATCCESORY("hatccesory", true, false);

    private final String display;
    private final boolean reforgable;
    private final boolean isDungeon;

    ItemType(String display, boolean reforgable, boolean isDungeon) {
        this.display = display;
        this.reforgable = reforgable;
        this.isDungeon = isDungeon;
    }

    public String display() {
        return display;
    }

    public boolean reforgable() {
        return reforgable;
    }

    public boolean isDungeon() {
        return isDungeon;
    }
}
