package com.partlysunny.data.recipes.crafting;

import com.partlysunny.core.gui.craft.recipe.ShapedRecipe;
import com.partlysunny.core.util.classes.Pair;

public class UltraBladeRecipe extends ShapedRecipe {
    public UltraBladeRecipe() {
        super("ultrabladerecipe");
    }

    @Override
    public Pair<String, Integer>[] getIngredients() {
        Pair<String, Integer>[] ingredients = new Pair[9];
        ingredients[0] = new Pair<>("stick", 5);
        ingredients[1] = new Pair<>("stick", 5);
        ingredients[2] = new Pair<>("stick", 5);
        ingredients[3] = new Pair<>("stick", 5);
        ingredients[4] = new Pair<>("iron_sword", 5);
        ingredients[5] = new Pair<>("stick", 5);
        ingredients[6] = new Pair<>("stick", 5);
        ingredients[7] = new Pair<>("stick", 5);
        ingredients[8] = new Pair<>("stick", 5);
        return ingredients;
    }

    @Override
    public Pair<String, Integer> getResult() {
        return new Pair<>("ultrakatana", 1);
    }
}
