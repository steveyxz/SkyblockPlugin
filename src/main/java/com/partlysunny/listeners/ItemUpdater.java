package com.partlysunny.listeners;

import com.partlysunny.items.ModifierType;
import com.partlysunny.items.additions.AdditionList;
import com.partlysunny.items.additions.AdditionType;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.partlysunny.listeners.ItemTracker.items;

public class ItemUpdater implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent e) {
        updateInventory(e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent e) {
        updateInventory(e.getInventory());
    }

    private void updateInventory(Inventory inv) {
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(i);
                UUID sb_unique_id = nbti.getUUID("sb_unique_id");
                if (sb_unique_id == null) {
                    return;
                }
                AdditionList statAdditions = update(items.get(sb_unique_id).getSkyblockItem(), i, "statAdditions", ModifierType.STAT);
                AdditionList abilityAdditions = update(items.get(sb_unique_id).getSkyblockItem(), i, "abilityAdditions", ModifierType.ABILITY);
                AdditionList rarityAdditions = update(items.get(sb_unique_id).getSkyblockItem(), i, "rarityAdditions", ModifierType.RARITY);
                if (statAdditions != null) {
                    items.get(sb_unique_id).statAdditions().setList(statAdditions);
                }
                if (abilityAdditions != null) {
                    items.get(sb_unique_id).statAdditions().setList(abilityAdditions);
                }
                if (rarityAdditions != null) {
                    items.get(sb_unique_id).statAdditions().setList(rarityAdditions);
                }
            }
        }
    }

    private AdditionList update(ItemStack cached, ItemStack real, String compoundKey, ModifierType listType) {
        if (cached == null || real == null) {
            return null;
        }
        NBTCompound cachedNItem = new NBTItem(real).getCompound(compoundKey);
        NBTCompound realNItem = new NBTItem(cached).getCompound(compoundKey);
        if (!cachedNItem.equals(realNItem)) {
            AdditionList l = new AdditionList(listType);
            for (String key : realNItem.getKeys()) {
                l.addAdditions(AdditionType.getTypeFromId(key), realNItem.getInteger(key));
            }
            return l;
        }
        return null;
    }




}
