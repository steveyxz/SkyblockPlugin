package com.partlysunny.core.items;

public record ItemInfo(String id,
                       Class<? extends SkyblockItem> itemType) {
}
