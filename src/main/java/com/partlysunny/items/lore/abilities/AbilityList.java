package com.partlysunny.items.lore.abilities;

import java.util.HashMap;
import java.util.Map;

public class AbilityList {

    private final Map<AbilityType, Ability> abilityList = new HashMap<>();

    public void addStat(Ability ability) {
        abilityList.put(ability.type(), ability);
    }

    public void removeStat(AbilityType type) {
        abilityList.remove(type);
    }

    public void removeStat(Ability ability) {
        abilityList.remove(ability.type());
    }

    public Ability[] asList() {
        Ability[] returned = new Ability[abilityList.size()];
        int count = 0;
        for (Ability s : abilityList.values()) {
            returned[count] = s;
            count++;
        }
        return returned;
    }

}
