package com.partlysunny.items;

import com.partlysunny.items.necron.NecronBoots;
import com.partlysunny.items.necron.NecronChestplate;
import com.partlysunny.items.necron.NecronHelmet;
import com.partlysunny.items.necron.NecronLeggings;
import com.partlysunny.items.ultraset.*;

public class ItemRegister {

    public static void registerItems() {
        new UltraChestplate();
        new UltraLeggings();
        new UltraHelmet();
        new UltraKatana();
        new UltraBoots();
        new NecronChestplate();
        new NecronBoots();
        new NecronLeggings();
        new NecronHelmet();
    }

}
