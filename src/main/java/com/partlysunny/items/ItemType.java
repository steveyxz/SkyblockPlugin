package com.partlysunny.items;

import com.partlysunny.items.custom.SkyblockItem;

public enum ItemType {


    ;

    private final Class<? extends SkyblockItem> itemType;

    ItemType(Class<? extends SkyblockItem> itemType) {
        this.itemType = itemType;
    }

    public Class<? extends SkyblockItem> itemType() {
        return itemType;
    }
}
