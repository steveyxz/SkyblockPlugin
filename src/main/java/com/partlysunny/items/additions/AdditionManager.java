package com.partlysunny.items.additions;

import java.util.ArrayList;
import java.util.List;

public class AdditionManager {

    private final List<Addition> additions = new ArrayList<>();

    public void addAddition(Addition addition) {
        additions.add(addition);
    }

    public void removeAddition(String additionId) {
        Addition removed = null;
        for (Addition a : additions) {
            if (a.type().id().equals(additionId)) {
                removed = a;
            }
        }
        additions.remove(removed);
    }

    public Addition getAddition(String additionId) {
        for (Addition a : additions) {
            if (a.type().id().equals(additionId)) {
                return a;
            }
        }
        return null;
    }

}
