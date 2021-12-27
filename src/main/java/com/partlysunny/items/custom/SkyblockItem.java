package com.partlysunny.items.custom;

import com.partlysunny.Skyblock;
import com.partlysunny.common.Rarity;
import com.partlysunny.items.additions.AdditionList;
import com.partlysunny.items.lore.LoreBuilder;
import com.partlysunny.items.lore.abilities.AbilityList;
import com.partlysunny.stats.StatList;
import de.tr7zw.nbtapi.NBTItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SkyblockItem implements Listener {

    private final String id;
    private final AdditionList statAdditions = new AdditionList();
    private final AdditionList rarityAdditions = new AdditionList();
    private final AdditionList abilityAdditions = new AdditionList();

    protected SkyblockItem(String id) {
        this.id = id;
        JavaPlugin.getPlugin(Skyblock.class).getServer().getPluginManager().registerEvents(this, JavaPlugin.getPlugin(Skyblock.class));
    }

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        assert tag != null;
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public abstract Material getDefaultItem();

    public abstract String getDisplayName();

    public abstract AbilityList getAbilities();

    public abstract boolean isEnchanted();

    public abstract StatList getStats();

    public abstract String getDescription();

    public abstract Rarity getRarity();

    public ItemStack getSkyblockItem() {
        ItemStack i = new ItemStack(getDefaultItem());
        if (isEnchanted()) {
            i = addGlow(i);
        }
        ItemMeta m = i.getItemMeta();
        if (m == null) {
            return i;
        }
        m.setDisplayName(getDisplayName());
        m.setLore(new LoreBuilder()
                .setDescription(getDescription() != null ? getDescription() : "")
                .setRarity(getRarity())
                .setStats(getStats().asList())
                .addAbilities(getAbilities().asList())
                .build()
        );
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON);
        m.setUnbreakable(true);
        i.setItemMeta(m);

        NBTItem nbti = new NBTItem(i);
        nbti = getStats().applyStats(nbti);
        nbti = statAdditions.applyAdditions(nbti);
        nbti = rarityAdditions.applyAdditions(nbti);
        nbti = abilityAdditions.applyAdditions(nbti);
        i = nbti.getItem();

        return i;

    }

    public AdditionList statAdditions() {
        return statAdditions;
    }

    public AdditionList rarityAdditions() {
        return rarityAdditions;
    }

    public AdditionList abilityAdditions() {
        return abilityAdditions;
    }

    public String id() {
        return id;
    }
}
