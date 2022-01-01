package com.partlysunny.core.items;

import com.partlysunny.Skyblock;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.additions.*;
import com.partlysunny.core.items.lore.LoreBuilder;
import com.partlysunny.core.items.name.NameBuilder;
import com.partlysunny.core.stats.StatList;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class SkyblockItem implements Listener {

    private final String id;
    private final AdditionList statAdditions = new AdditionList(ModifierType.STAT, this);
    private final AdditionList rarityAdditions = new AdditionList(ModifierType.RARITY, this);
    private final AdditionList abilityAdditions = new AdditionList(ModifierType.ABILITY, this);
    private ItemStack asSkyblockItem;

    protected SkyblockItem(String id) {
        this.id = id;
        Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        ItemManager.addItem(new ItemInfo(id, getClass()));
        updateSkyblockItem();
    }

    public ItemStack addGlow(ItemStack itemStack) {
        // adds protection to bows and infinity to every other item as infinity is only useful on bows and protection is only useful on armor
        itemStack.addUnsafeEnchantment((itemStack.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);
        // returns the new itemstack
        return itemStack;
    }

    @Nullable
    public static SkyblockItem getItemFrom(ItemStack s) {
        NBTItem nbti = new NBTItem(s);
        ItemInfo type = ItemManager.getInfoFromId(nbti.getString("sb_id"));
        if (type == null) {
            System.out.println("Bad Type: " + nbti.getString("sb_id"));
            return null;
        }
        SkyblockItem item = ItemManager.getInstance(type);
        if (item == null) {
            System.out.println("Bad Instance");
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
                item.abilityAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        if (statAdditions != null) {
            for (String key : statAdditions.getKeys()) {
                Integer amount = statAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.statAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        if (rarityAdditions != null) {
            for (String key : rarityAdditions.getKeys()) {
                Integer amount = rarityAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.rarityAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        return item;
    }

    public abstract Material getDefaultItem();

    public abstract String getDisplayName();

    public abstract AbilityList getAbilities();

    public abstract boolean isEnchanted();

    public abstract StatList getStats();

    public abstract String getDescription();

    public abstract Rarity getRarity();

    public StatList getCombinedStats() {
        StatList base = getStats();
        for (IStatAddition a : statAdditions.asStatList()) {
            base = base.merge(a.getStats());
        }
        return base;
    }

    public AbilityList getCombinedAbilities() {
        AbilityList base = getAbilities();
        for (IAbilityAddition a : abilityAdditions.asAbilityList()) {
            base.addAbility(a.getAbilities());
        }
        return base;
    }

    public Rarity getFinalRarity() {
        Rarity base = getRarity();
        for (IRarityAddition a : rarityAdditions.asRarityList()) {
            base = Rarity.add(base, a.getLevelChange());
        }
        return base;
    }


    public ItemStack getSkyblockItem() {
        return asSkyblockItem;
    }

    public void updateSkyblockItem() {
        ItemStack i = new ItemStack(getDefaultItem());
        if (isEnchanted()) {
            i = addGlow(i);
        }
        ItemMeta m = i.getItemMeta();
        if (m == null) {
            return;
        }
        //TODO add real item stats for these
        m.setDisplayName(new NameBuilder().setName(getDisplayName()).setReforge("Withered").setRarity(getRarity()).setFragged(true).setStars(9).build());
        m.setLore(new LoreBuilder()
                .setDescription(getDescription() != null ? getDescription() : "")
                .setRarity(getFinalRarity())
                .setStats(getCombinedStats(), statAdditions())
                .addAbilities(getCombinedAbilities())
                .build()
        );
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON);
        m.setUnbreakable(true);
        i.setItemMeta(m);

        NBTItem nbti = new NBTItem(i);
        nbti.setString("sb_id", id);
        nbti.setBoolean("vanilla", false);
        if (!nbti.hasKey("sb_unique_id")) {
            nbti.setUUID("sb_unique_id", UUID.randomUUID());
        }
        nbti = getStats().applyStats(nbti);
        nbti = statAdditions.applyAdditions(nbti);
        nbti = rarityAdditions.applyAdditions(nbti);
        nbti = abilityAdditions.applyAdditions(nbti);
        //System.out.println(nbti.toString());
        i = nbti.getItem();
        asSkyblockItem = i;
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
