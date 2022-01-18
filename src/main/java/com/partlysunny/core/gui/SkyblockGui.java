package com.partlysunny.core.gui;

import com.partlysunny.core.gui.components.GuiComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SkyblockGui {

    protected final String id;
    protected final int slots;
    protected final String guiName;
    protected final List<GuiComponent> contents = new ArrayList<>();
    private final Inventory inventory;
    private final UUID uniqueId = UUID.randomUUID();

    protected SkyblockGui(String id, int slots, String guiName) {
        this.id = id;
        this.slots = slots;
        this.guiName = guiName;
        buildGui();
        inventory = Bukkit.createInventory(null, slots, guiName);
        updateInventory();
    }

    public String id() {
        return id;
    }

    public int slots() {
        return slots;
    }

    public String guiName() {
        return guiName;
    }

    public GuiComponent getComponent(int index) {
        return contents.get(index);
    }

    public void setComponent(int index, GuiComponent newComponent) {
        contents.set(index, newComponent);
    }

    public void updateInventory() {
        for (int i = 0; i < slots; i++) {
            inventory.setItem(i, getComponent(i).shownItem());
        }
    }

    public void openFor(Player p) {
        p.openInventory(inventory);
    }

    //Called only at the beginning
    protected abstract void buildGui();

    protected List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }
}
