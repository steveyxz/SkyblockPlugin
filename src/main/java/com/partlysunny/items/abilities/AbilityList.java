package com.partlysunny.items.abilities;

import com.partlysunny.Skyblock;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class AbilityList {

    private final Map<AbilityType, Ability> abilityList = new HashMap<>();

    public void addAbility(Ability ability) {
        abilityList.put(ability.type(), ability);
        JavaPlugin.getPlugin(Skyblock.class).getServer().getPluginManager().registerEvents(ability, JavaPlugin.getPlugin(Skyblock.class));
    }

    public void removeAbility(AbilityType type) {
        abilityList.remove(type);
    }

    public void removeAbility(Ability ability) {
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

    public AbilityList merge(AbilityList other) {
        abilityList.putAll(other.abilityList);
        return this;
    }

}
