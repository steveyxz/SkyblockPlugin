package com.partlysunny.core.gui.craft;

import com.partlysunny.core.gui.craft.recipe.ShapedRecipe;
import com.partlysunny.core.util.classes.Pair;
import org.bukkit.inventory.ItemStack;

public class CraftingGrid {

    private final ItemStack[] items = new ItemStack[9];

    public CraftingGrid(ItemStack... items) {
        int count = 0;
        for (ItemStack i : items) {
            if (count > 8) {
                break;
            }
            this.items[count] = i;
            count++;
        }
    }

    public CraftingGrid() {
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setSlot(int slot, ItemStack i) {
        if (slot > 8) {
            return;
        }
        items[slot] = i;
    }

    public ItemStack getSlot(int slot) {
        if (slot > 8) {
            return null;
        }
        return items[slot];
    }

    public void removeRecipeIngredients(ShapedRecipe recipe) {
        Pair<String, Integer>[] ingredients = recipe.getIngredients();
        for (int i = 0; i < 9; i++) {
            ItemStack item = items[i];
            item.setAmount(item.getAmount() - ingredients[i].b());
        }
    }

    public int getFirstOpenSlot() {
        for (int i = 0; i < 9; i++) {
            if (items[i] != null) {
                return i;
            }
        }
        return -1;
    }
}
