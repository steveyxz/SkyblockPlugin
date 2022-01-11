package com.partlysunny.core.items.additions;

import com.partlysunny.core.items.ItemType;

public class AppliableTypeDefaults {
    public static final ItemType[] rangedWeapons = new ItemType[]{ItemType.BOW, ItemType.DUNGEON_BOW};
    public static final ItemType[] meleeWeapons = new ItemType[]{ItemType.SWORD, ItemType.DUNGEON_SWORD};
    public static final ItemType[] armor = new ItemType[]{ItemType.HELMET, ItemType.DUNGEON_HELMET, ItemType.CHESTPLATE, ItemType.DUNGEON_CHESTPLATE, ItemType.BOOTS, ItemType.DUNGEON_BOOTS, ItemType.LEGGINGS, ItemType.DUNGEON_LEGGINGS, ItemType.GAUNTLET};
    public static final ItemType[] accessories = new ItemType[]{ItemType.HATCCESORY, ItemType.ACCESSORY, ItemType.DUNGEON_ACCESSORY};
    public static final ItemType[] tools = new ItemType[]{ItemType.AXE, ItemType.PICKAXE, ItemType.SHOVEL, ItemType.HOE, ItemType.DRILL, ItemType.GAUNTLET};
    public static final ItemType[] all = ItemType.values();
}
