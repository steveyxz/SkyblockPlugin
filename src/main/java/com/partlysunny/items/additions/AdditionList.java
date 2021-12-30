package com.partlysunny.items.additions;

import com.partlysunny.items.ModifierType;
import com.partlysunny.items.additions.common.ability.IAbilityAddition;
import com.partlysunny.items.additions.common.rarity.IRarityAddition;
import com.partlysunny.items.additions.common.stat.IStatAddition;
import com.partlysunny.items.custom.SkyblockItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AdditionList {

    private final Map<AdditionType, Addition> additionList = new HashMap<>();
    private ModifierType accepting = ModifierType.ANY;
    private SkyblockItem parent;

    public AdditionList() {
    }

    public AdditionList(ModifierType accepting) {
        this.accepting = accepting;
    }

    public AdditionList(ModifierType accepting, SkyblockItem parent) {
        this.accepting = accepting;
        this.parent = parent;
    }

    public void addAdditions(AdditionType addition, int count) {
        if (accepting != ModifierType.ANY && addition.type() != accepting) {
            throw new IllegalArgumentException("This list does not accept type " + addition.type());
        }
        Addition target = additionList.get(addition);
        if (target == null) {
            try {
                additionList.put(addition, addition.cl().getDeclaredConstructor().newInstance());
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        }
        target.setAmount(target.amount() + count);
        if (checkZero(target)) {
            additionList.remove(addition);
        }
        if (parent != null) {
            parent.updateSkyblockItem();
        }
    }

    public void setList(AdditionList newList) {
        if (newList.accepting() != accepting()) {
            throw new IllegalArgumentException("Lists do not match types");
        }
        additionList.clear();
        additionList.putAll(newList.additionList);
    }

    public void addAddition(AdditionType type) {
        addAdditions(type, 1);
    }

    public void removeAdditions(AdditionType addition, int count) {
        if (accepting != ModifierType.ANY && addition.type() != accepting) {
            throw new IllegalArgumentException("This list does not contain type " + addition.type());
        }
        Addition target = additionList.get(addition);
        if (target == null) {
            return;
        }
        target.setAmount(target.amount() - count);
        if (checkZero(target)) {
            additionList.remove(addition);
        }
        if (parent != null) {
            parent.updateSkyblockItem();
        }
    }

    public void removeAddition(AdditionType type) {
        removeAdditions(type, 1);
    }

    public void completelyRemove(AdditionType addition) {
        if (accepting != ModifierType.ANY && addition.type() != accepting) {
            throw new IllegalArgumentException("This list does not contain type " + addition.type());
        }
        additionList.remove(addition);
        if (parent != null) {
            parent.updateSkyblockItem();
        }
    }

    public NBTItem applyAdditions(NBTItem item) {
        NBTCompound c;
        if (accepting == ModifierType.ANY) {
            c = item.addCompound("additions");
        } else if (accepting == ModifierType.STAT) {
            c = item.addCompound("additions");
        } else if (accepting == ModifierType.ABILITY) {
            c = item.addCompound("abilityAdditions");
        } else if (accepting == ModifierType.RARITY) {
            c = item.addCompound("rarityAdditions");
        } else {
            c = item.addCompound("additions");
        }
        for (Addition a : additionList.values()) {
            c.setInteger(a.type().id(), (c.getInteger(a.type.id()) == null ? 0 : c.getInteger(a.type.id())) + a.amount());
        }
        return item;
    }

    public Addition[] asList() {
        Addition[] returned = new Addition[additionList.size()];
        int count = 0;
        for (Addition s : additionList.values()) {
            returned[count] = s;
            count++;
        }
        return returned;
    }

    public IStatAddition[] asStatList() {
        if (accepting != ModifierType.STAT) {
            throw new IllegalArgumentException("This list cannot be converted to a StatList");
        }
        IStatAddition[] returned = new IStatAddition[additionList.size()];
        int count = 0;
        for (Addition s : additionList.values()) {
            returned[count] = (IStatAddition) s;
            count++;
        }
        return returned;
    }

    public IAbilityAddition[] asAbilityList() {
        if (accepting != ModifierType.ABILITY) {
            throw new IllegalArgumentException("This list cannot be converted to a StatList");
        }
        IAbilityAddition[] returned = new IAbilityAddition[additionList.size()];
        int count = 0;
        for (Addition s : additionList.values()) {
            returned[count] = (IAbilityAddition) s;
            count++;
        }
        return returned;
    }

    public IRarityAddition[] asRarityList() {
        if (accepting != ModifierType.RARITY) {
            throw new IllegalArgumentException("This list cannot be converted to a StatList");
        }
        IRarityAddition[] returned = new IRarityAddition[additionList.size()];
        int count = 0;
        for (Addition s : additionList.values()) {
            returned[count] = (IRarityAddition) s;
            count++;
        }
        return returned;
    }


    public ModifierType accepting() {
        return accepting;
    }

    public boolean checkZero(Addition a) {
        return a.amount < 1;
    }

}
