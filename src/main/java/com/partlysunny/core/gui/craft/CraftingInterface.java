package com.partlysunny.core.gui.craft;

import com.partlysunny.core.gui.SkyblockGui;
import com.partlysunny.core.gui.craft.recipe.RecipeManager;
import com.partlysunny.core.gui.craft.recipe.ShapedRecipe;
import com.partlysunny.core.items.ItemInfo;
import com.partlysunny.core.items.ItemManager;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.core.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CraftingInterface extends SkyblockGui implements Listener {

    private static final Integer[] CRAFTING_SLOTS = new Integer[]{10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final Integer RESULT_SLOT = 23;
    private static final Integer BACK_BUTTON = 49;
    private static final Integer[] QUICK_CRAFT_SLOTS = new Integer[]{16, 25, 34};
    private static final String INVENTORY_NAME = ChatColor.GRAY + "Craft Item";

    private final CraftingGrid grid = new CraftingGrid();
    private ShapedRecipe currentRecipe = null;
    private Inventory openInventory;

    @SuppressWarnings("all")
    public CraftingInterface(Player p) {
        //TODO finish crafting
        super("crafting", 54, INVENTORY_NAME);

    }

    private static boolean isCraftingSlot(int slot) {
        return List.of(CRAFTING_SLOTS).contains(slot);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAddItem(InventoryClickEvent e) {
        HumanEntity whoClicked = e.getWhoClicked();
        if (whoClicked instanceof Player) {
            Inventory inventory = e.getClickedInventory();
            if (inventory == null) {
                return;
            }
            InventoryView view = e.getView();
            //If clicking the actual crafting grid
            if (inventory.equals(openInventory)) {
                if (
                        e.getAction() == InventoryAction.PLACE_ALL ||
                                e.getAction() == InventoryAction.PLACE_ONE ||
                                e.getAction() == InventoryAction.PLACE_SOME ||
                                e.getAction() == InventoryAction.SWAP_WITH_CURSOR ||
                                e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD ||
                                e.getAction() == InventoryAction.HOTBAR_SWAP
                ) {
                    if (isCraftingSlot(e.getSlot())) {
                        updateGrid(openInventory, (Player) whoClicked);
                    } else {
                        e.setCancelled(true);
                    }
                }
            } else if (view.getType() == InventoryType.PLAYER) {
                //Shift clicking items into the grid
                if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && openInventory.getViewers().contains(whoClicked)) {
                    int firstOpenSlot = grid.getFirstOpenSlot();
                    Inventory clickedInventory = e.getClickedInventory();
                    ItemStack item = clickedInventory.getItem(e.getSlot());
                    if (firstOpenSlot == -1) {
                        clickedInventory.removeItem(item);
                        clickedInventory.addItem(item);
                        e.setCancelled(true);
                        return;
                    }
                    grid.setSlot(firstOpenSlot, item);
                    updateGrid(openInventory, (Player) whoClicked);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onRemoveItem(InventoryClickEvent e) {
        HumanEntity whoClicked = e.getWhoClicked();
        if (whoClicked instanceof Player) {
            Inventory inventory = e.getClickedInventory();
            if (inventory == null) {
                return;
            }
            if (inventory.equals(openInventory)) {
                if (
                        e.getAction() == InventoryAction.PICKUP_ALL ||
                                e.getAction() == InventoryAction.PICKUP_HALF ||
                                e.getAction() == InventoryAction.PICKUP_ONE ||
                                e.getAction() == InventoryAction.PICKUP_SOME ||
                                e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                                e.getAction() == InventoryAction.DROP_ALL_SLOT ||
                                e.getAction() == InventoryAction.DROP_ONE_SLOT
                ) {
                    if (isCraftingSlot(e.getSlot())) {
                        updateGrid(openInventory, (Player) whoClicked);
                    } else if (e.getSlot() == RESULT_SLOT) {
                        //Check if the result can be taken
                        if (currentRecipe != null) {
                            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                                grid.removeRecipeIngredients(currentRecipe);
                                updateGrid(openInventory, (Player) whoClicked);
                                whoClicked.getWorld().playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 3, 1);
                            } else if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                                do {
                                    grid.removeRecipeIngredients(currentRecipe);
                                } while (currentRecipe.matches(grid));
                                updateGrid(openInventory, (Player) whoClicked);
                                whoClicked.getWorld().playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 3, 1);
                            } else {
                                e.setCancelled(true);
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onCraftingClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Inventory inventory = e.getInventory();
            if (inventory.equals(openInventory)) {
                for (ItemStack i : grid.getItems()) {
                    ItemUtils.addItem((Player) e.getPlayer(), i);
                }
                HandlerList.unregisterAll(this);
            }
        }
    }

    private void updateGrid(Inventory craftingInventory, Player player) {
        ItemStack[] contents = craftingInventory.getContents();
        int count = 0;
        for (int i : CRAFTING_SLOTS) {
            ItemStack stack = contents[i];
            grid.setSlot(count, stack);
            count++;
        }
        updateResult(craftingInventory, player);
    }

    private void updateResult(Inventory craftingInventory, Player player) {
        ShapedRecipe recipe = RecipeManager.getRecipe(grid);
        if (recipe == null) {
            craftingInventory.setItem(RESULT_SLOT, null);
            currentRecipe = null;
            return;
        }
        ItemInfo itemInfo = ItemManager.getInfoFromId(recipe.getResult().a());
        if (itemInfo == null) {
            ItemStack item = DataUtils.createSkyblockItemFromVanilla(Material.valueOf(recipe.getResult().a().toUpperCase()), player).getSkyblockItem();
            item.setAmount(recipe.getResult().b());
            craftingInventory.setItem(RESULT_SLOT, item);
            currentRecipe = recipe;
            return;
        }
        ItemStack item = ItemManager.getInstance(itemInfo).getSkyblockItem();
        item.setAmount(recipe.getResult().b());
        craftingInventory.setItem(RESULT_SLOT, item);
        currentRecipe = recipe;
    }


    @Override
    protected void buildGui() {
        
    }
}
