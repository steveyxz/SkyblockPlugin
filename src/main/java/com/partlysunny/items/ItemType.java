package com.partlysunny.items;

import com.partlysunny.items.custom.SkyblockItem;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public enum ItemType {


    ;

    private final Class<? extends SkyblockItem> itemType;
    private final String id;

    ItemType(Class<? extends SkyblockItem> itemType, String id) {
        this.itemType = itemType;
        this.id = id;
    }

    public Class<? extends SkyblockItem> itemType() {
        return itemType;
    }

    @Nullable
    public static SkyblockItem getInstance(ItemType type) {
        try {
            return type.itemType().getDeclaredConstructor(String.class).newInstance(type.id);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
