package com.partlysunny.items.additions.common.ability;

import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.common.rightClick.ImplodeAbility;
import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionType;

public class Implode extends Addition implements IAbilityAddition {
    public Implode() {
        super(AdditionType.IMPLODE);
    }

    @Override
    public Ability getAbilities() {
        return new ImplodeAbility();
    }
}
