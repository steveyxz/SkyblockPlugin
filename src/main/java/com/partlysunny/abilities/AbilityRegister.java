package com.partlysunny.abilities;

import com.partlysunny.abilities.passive.fullSet.WitherbornAbility;
import com.partlysunny.abilities.rightClick.SmiteAbility;

public class AbilityRegister {

    public static void registerAbilities() {
        new SmiteAbility();
        new WitherbornAbility();
    }

}
