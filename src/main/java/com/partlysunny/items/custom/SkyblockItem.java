package com.partlysunny.items.custom;

import com.partlysunny.Skyblock;
import com.partlysunny.common.Rarity;
import com.partlysunny.items.ItemType;
import com.partlysunny.items.ModifierType;
import com.partlysunny.items.additions.AdditionList;
import com.partlysunny.items.additions.AdditionType;
import com.partlysunny.items.lore.LoreBuilder;
import com.partlysunny.items.lore.abilities.AbilityList;
import com.partlysunny.stats.StatList;
import de.tr7zw.nbtapi.NBTCompound;
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

import javax.annotation.Nullable;

public abstract class SkyblockItem implements Listener {

    private final String id;
    private final AdditionList statAdditions = new AdditionList(ModifierType.STAT);
    private final AdditionList rarityAdditions = new AdditionList(ModifierType.RARITY);
    private final AdditionList abilityAdditions = new AdditionList(ModifierType.ABILITY);

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
        nbti.setString("sb_id", id);
        nbti = getStats().applyStats(nbti);
        nbti = statAdditions.applyAdditions(nbti);
        nbti = rarityAdditions.applyAdditions(nbti);
        nbti = abilityAdditions.applyAdditions(nbti);
        System.out.println(nbti.toString());
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

    @Nullable
    public static SkyblockItem getItemFrom(ItemStack s) {
        NBTItem nbti = new NBTItem(s);
        ItemType type = ItemType.getTypeFromId(nbti.getString("sb_id"));
        if (type == null) {
            return null;
        }
        SkyblockItem item = ItemType.getInstance(type);
        if (item == null) {
            return null;
        }
        NBTCompound abilityAdditions = nbti.getCompound("abilityAdditions");
        NBTCompound statAdditions = nbti.getCompound("statAdditions");
        NBTCompound rarityAdditions = nbti.getCompound("rarityAdditions");
        if (abilityAdditions != null) {
            for (String key : abilityAdditions.getKeys()) {
                Integer amount = abilityAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.abilityAdditions.addAdditions(AdditionType.getTypeFromId(key), amount);
            }
        }
        if (statAdditions != null) {
            for (String key : statAdditions.getKeys()) {
                Integer amount = statAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.statAdditions.addAdditions(AdditionType.getTypeFromId(key), amount);
            }
        }
        if (rarityAdditions != null) {
            for (String key : rarityAdditions.getKeys()) {
                Integer amount = rarityAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.rarityAdditions.addAdditions(AdditionType.getTypeFromId(key), amount);
            }
        }
        return item;
    }
}
