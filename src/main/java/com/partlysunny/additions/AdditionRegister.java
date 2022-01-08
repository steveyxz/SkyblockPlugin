package com.partlysunny.additions;

import com.partlysunny.additions.reforge.WitheredReforge;
import com.partlysunny.additions.stat.Infusion;
import com.partlysunny.additions.stat.PotatoBook;

public class AdditionRegister {

    public static void registerAdditions() {
        new Infusion();
        new PotatoBook();
    }

    public static void registerReforges() {
        new WitheredReforge();
    }

}
