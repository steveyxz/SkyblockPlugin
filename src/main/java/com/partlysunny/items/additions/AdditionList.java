package com.partlysunny.items.additions;

import de.tr7zw.nbtapi.NBTItem;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AdditionList {

    private final Map<AdditionType, Addition> additionList = new HashMap<>();

    public void addAdditions(AdditionType addition, int count) {
        Addition target = additionList.get(addition);
        if (target == null) {
            try {
                additionList.put(addition, addition.cl().getDeclaredConstructor(addition.getDeclaringClass()).newInstance(addition));
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        }
        target.setAmount(target.amount() + count);
        if (checkZero(target)) {
            additionList.remove(addition);
        }
    }

    public void addAddition(AdditionType type) {
        addAdditions(type, 1);
    }

    public void removeAdditions(AdditionType addition, int count) {
        Addition target = additionList.get(addition);
        if (target == null) {
            return;
        }
        target.setAmount(target.amount() - count);
        if (checkZero(target)) {
            additionList.remove(addition);
        }
    }

    public void removeAddition(AdditionType type) {
        removeAdditions(type, 1);
    }

    public void completelyRemove(AdditionType addition) {
        additionList.remove(addition);
    }

    public NBTItem applyAdditions(NBTItem item) {
        for (Addition a : additionList.values()) {
            item.setInteger(a.type().id(), a.amount());
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

    public boolean checkZero(Addition a) {
        return a.amount < 1;
    }

}
