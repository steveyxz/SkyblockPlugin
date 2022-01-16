package com.partlysunny.additions;

import com.partlysunny.additions.ability.Glow;
import com.partlysunny.additions.enchant.SharpnessEnchant;
import com.partlysunny.additions.enchant.SmiteEnchant;
import com.partlysunny.additions.reforge.WitheredReforge;
import com.partlysunny.additions.stat.Infusion;
import com.partlysunny.additions.stat.ZombieProof;

public class AdditionRegister {

    public static void registerAdditions() {
        new Infusion();
        new ZombieProof();
        new Glow();
    }

    public static void registerReforges() {
        new WitheredReforge();
    }

    public static void registerEnchants() {
        new SharpnessEnchant();
        new SmiteEnchant();
    }

}
