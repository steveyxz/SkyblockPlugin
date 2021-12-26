package com.partlysunny.items.additions.common.ability;

import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionType;
import com.partlysunny.items.lore.abilities.Ability;

public abstract class AbilityAddition extends Addition {

    private final Ability ability;

    public AbilityAddition(AdditionType type, Ability ability) {
        super(type);
        this.ability = ability;
    }

    public Ability ability() {
        return ability;
    }
}
