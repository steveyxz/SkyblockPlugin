package com.partlysunny.items;

import com.partlysunny.items.custom.SkyblockItem;
import com.partlysunny.items.custom.UltraBlade;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public enum ItemType {

    ULTRA_BLADE("ultra_blade", UltraBlade.class)
    ;

    private final Class<? extends SkyblockItem> itemType;
    private final String id;

    ItemType(String id, Class<? extends SkyblockItem> itemType) {
        this.itemType = itemType;
        this.id = id;
    }

    @Nullable
    public static SkyblockItem getInstance(ItemType type) {
        try {
            return type.itemType().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemType getTypeFromId(String id) {
        for (ItemType t : values()) {
            if (Objects.equals(id, t.id)) {
                return t;
            }
        }
        return null;
    }

    public Class<? extends SkyblockItem> itemType() {
        return itemType;
    }

    public String id() {
        return id;
    }
}
