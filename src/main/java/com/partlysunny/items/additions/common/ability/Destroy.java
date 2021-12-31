package com.partlysunny.items.additions.common.ability;

import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.common.rightClick.DestroyAbility;
import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionType;

public class Destroy extends Addition implements IAbilityAddition {
    public Destroy() {
        super(AdditionType.DESTROY);
    }

    @Override
    public Ability getAbilities() {
        return new DestroyAbility();
    }
}
