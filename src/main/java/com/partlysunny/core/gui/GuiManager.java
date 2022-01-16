package com.partlysunny.core.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    private static final Map<UUID, SkyblockGui> activeGuis = new HashMap<>();

    public static void setInventory(UUID id, SkyblockGui inventory) {
        activeGuis.put(id, inventory);
    }

    public static SkyblockGui getActiveInventory(UUID id) {
        return activeGuis.get(id);
    }


}
