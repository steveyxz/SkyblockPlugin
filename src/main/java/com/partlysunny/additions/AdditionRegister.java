package com.partlysunny.additions;

import com.partlysunny.additions.ability.Glow;
import com.partlysunny.additions.reforge.WitheredReforge;
import com.partlysunny.additions.stat.Infusion;

public class AdditionRegister {

    public static void registerAdditions() {
        new Infusion();
        new Glow();
    }

    public static void registerReforges() {
        new WitheredReforge();
    }

}
