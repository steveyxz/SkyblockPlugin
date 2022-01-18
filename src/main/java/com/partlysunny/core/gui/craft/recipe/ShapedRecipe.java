package com.partlysunny.core.gui.craft.recipe;

import com.partlysunny.core.gui.craft.CraftingGrid;
import com.partlysunny.core.util.ItemUtils;
import com.partlysunny.core.util.classes.Pair;
import org.bukkit.inventory.ItemStack;

public abstract class ShapedRecipe {

    private final String id;

    public ShapedRecipe(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public abstract Pair<String, Integer>[] getIngredients();
    public abstract Pair<String, Integer> getResult();

    public boolean matches(CraftingGrid grid) {
        Pair<String, Integer>[] ingredients = getIngredients();
        for (int i = 0; i < 9; i++) {
            ItemStack gridItem = grid.getSlot(i);
            Pair<String, Integer> gridItemConverted = new Pair<>(ItemUtils.getId(gridItem), gridItem.getAmount());
            Pair<String, Integer> ingredient = ingredients[i];
            if (!(gridItemConverted.b() > ingredient.b()) || !(gridItemConverted.a().equals(ingredient.a()))) {
                return false;
            }
        }
        return true;
    }
}
