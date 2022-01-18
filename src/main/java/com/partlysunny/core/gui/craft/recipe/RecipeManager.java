package com.partlysunny.core.gui.craft.recipe;

import com.partlysunny.core.gui.craft.CraftingGrid;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {

    private static final Map<String, ShapedRecipe> recipes = new HashMap<>();

    public static void registerRecipe(ShapedRecipe recipe) {
        recipes.put(recipe.id(), recipe);
    }

    public static ShapedRecipe getRecipe(String id) {
        return recipes.get(id);
    }

    public static ShapedRecipe getRecipe(CraftingGrid grid) {
        for (ShapedRecipe r : recipes.values()) {
            if (r.matches(grid)) {
                return r;
            }
        }
        return null;
    }

}
