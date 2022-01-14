package com.partlysunny.additions.ability;

import com.partlysunny.abilities.passive.fullSet.GlowAbility;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.items.additions.IAbilityAddition;

public class Glow extends Addition implements IAbilityAddition {

    private final GlowAbility glow = new GlowAbility(parent);

    public Glow() {
        this(null);
    }

    public Glow(SkyblockItem parent) {
        super(new AdditionInfo("glow", 1, ModifierType.ABILITY, Glow.class, AppliableTypeDefaults.armor), parent);
    }

    @Override
    public Ability getAbilities() {
        return glow;
    }
}
