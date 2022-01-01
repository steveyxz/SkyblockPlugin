package com.partlysunny.additions.ability;

import com.partlysunny.abilities.rightClick.TestAbility;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.IAbilityAddition;

public class Test extends Addition implements IAbilityAddition {

    private final TestAbility ability = new TestAbility();

    public Test() {
        super(new AdditionInfo("test", 4, ModifierType.ABILITY, Test.class));
    }


    @Override
    public Ability getAbilities() {
        return ability;
    }
}
