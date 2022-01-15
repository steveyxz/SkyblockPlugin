package com.partlysunny.core.items;

import com.partlysunny.Skyblock;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.additions.*;
import com.partlysunny.core.items.additions.enchants.Enchant;
import com.partlysunny.core.items.additions.enchants.EnchantList;
import com.partlysunny.core.items.additions.reforges.ReforgeManager;
import com.partlysunny.core.items.lore.LoreBuilder;
import com.partlysunny.core.items.name.NameBuilder;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.util.DataUtils;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.inventory.EquipmentSlot.*;

public abstract class SkyblockItem implements Listener {

    private final String id;
    private final ItemType type;
    private final AdditionList statAdditions = new AdditionList(ModifierType.STAT, this);
    private final AdditionList rarityAdditions = new AdditionList(ModifierType.RARITY, this);
    private final AdditionList abilityAdditions = new AdditionList(ModifierType.ABILITY, this);
    private final EnchantList enchants = new EnchantList(this);
    private boolean fragged = false;
    private int stars = 0;
    private String reforge = null;
    private int stackCount = 1;
    private Material baseMaterial = getDefaultItem();
    //If this is true, it means it will be generated an uuid. Also if this is true the item cannot stack so yeah
    private boolean unique;
    private boolean vanilla = false;
    private UUID uniqueId;
    private ItemStack asSkyblockItem;
    private Player owner;
    private String[] fullSet;

    protected SkyblockItem(String id, boolean unique, ItemType type, @Nullable Player owner) {
        this.id = id;
        this.unique = unique;
        this.type = type;
        this.owner = owner;
        Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        ItemManager.addItem(new ItemInfo(id, getClass(), type));
        updateSkyblockItem();
    }

    protected SkyblockItem(String id, boolean unique, ItemType type, @Nullable Player owner, String[] fullSet) {
        this.id = id;
        this.unique = unique;
        this.type = type;
        this.owner = owner;
        this.fullSet = fullSet;
        Skyblock plugin = JavaPlugin.getPlugin(Skyblock.class);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        ItemManager.addItem(new ItemInfo(id, getClass(), type));
        updateSkyblockItem();
    }

    protected SkyblockItem(String id, boolean unique, ItemType type, @Nullable Player owner, boolean vanilla) {
        this(id, unique, type, owner);
        this.vanilla = vanilla;
    }

    @Nullable
    public static SkyblockItem getItemFrom(ItemStack s, @Nullable Player player) {
        NBTItem nbti = new NBTItem(s);
        ItemInfo type = ItemManager.getInfoFromId(nbti.getString("sb_id"));
        if (type == null) {
            if (nbti.getBoolean("vanilla")) {
                type = new ItemInfo(s.getType().toString().toLowerCase(), null, DataUtils.getTypeOfItem(s.getType()));
            } else {
                System.out.println("Bad Type: " + nbti.getString("sb_id"));
                return null;
            }
        }
        SkyblockItem item;
        if (nbti.getBoolean("vanilla")) {
            item = DataUtils.createSkyblockItemFromVanilla(s, player);
        } else {
            item = ItemManager.getInstance(type);
        }
        if (item == null) {
            System.out.println("Bad Instance");
            return null;
        }
        item.owner = player;
        item.baseMaterial = s.getType();
        if (nbti.hasKey("sb_unique_id")) {
            item.setUniqueId(nbti.getUUID("sb_unique_id"));
        }
        if (nbti.hasKey("sb_unique")) {
            item.setUnique(nbti.getBoolean("sb_unique"));
        }
        if (nbti.hasKey("sb_vanilla")) {
            item.setVanilla(nbti.getBoolean("sb_vanilla"));
        }
        if (nbti.hasKey("fragged")) {
            item.setFragged(nbti.getBoolean("fragged"));
        }
        if (nbti.hasKey("stars")) {
            item.setStars(nbti.getInteger("stars"));
        }
        if (nbti.hasKey("reforge")) {
            item.setReforge(nbti.getString("reforge"));
        }
        item.stackCount = s.getAmount();
        NBTCompound abilityAdditions = nbti.getCompound("abilityAdditions");
        NBTCompound statAdditions = nbti.getCompound("statAdditions");
        NBTCompound rarityAdditions = nbti.getCompound("rarityAdditions");
        NBTCompound enchants = nbti.getCompound("enchants");
        if (abilityAdditions != null) {
            for (String key : abilityAdditions.getKeys()) {
                if (!item.unique) {
                    item.unique = true;
                    item.uniqueId = UUID.randomUUID();
                }
                Integer amount = abilityAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.abilityAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        if (statAdditions != null) {
            for (String key : statAdditions.getKeys()) {
                if (!item.unique) {
                    item.unique = true;
                    item.uniqueId = UUID.randomUUID();
                }
                Integer amount = statAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.statAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        if (rarityAdditions != null) {
            for (String key : rarityAdditions.getKeys()) {
                if (!item.unique) {
                    item.unique = true;
                    item.uniqueId = UUID.randomUUID();
                }
                Integer amount = rarityAdditions.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.rarityAdditions.addAdditions(AdditionManager.getAddition(key), amount);
            }
        }
        if (enchants != null) {
            for (String key : enchants.getKeys()) {
                if (!item.unique) {
                    item.unique = true;
                    item.uniqueId = UUID.randomUUID();
                }
                Integer amount = enchants.getInteger(key);
                if (amount == null) {
                    continue;
                }
                item.enchants.addEnchant(key, amount);
            }
        }
        item.updateSkyblockItem();
        return item;
    }

    public boolean hasFullSet() {
        Player owner = owner();
        if (owner != null) {
            String[] fullSet = fullSet();
            if (fullSet != null) {
                return matches(HEAD, owner, fullSet[0]) && matches(CHEST, owner, fullSet[1]) && matches(LEGS, owner, fullSet[2]) && matches(FEET, owner, fullSet[3]);
            }
        }
        return false;
    }

    public boolean pieceBonusActive() {
        return matches(HEAD, owner, id) || matches(CHEST, owner, id) || matches(LEGS, owner, id) || matches(FEET, owner, id);
    }

    @SuppressWarnings("all")
    private boolean matches(EquipmentSlot slot, Player owner, String match) {
        ItemStack item = owner.getInventory().getItem(slot);
        if (match == null) {
            return true;
        }
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        NBTItem i = new NBTItem(item);
        return Objects.equals(match, i.getString("sb_id"));
    }


    public ItemStack addGlow(ItemStack itemStack) {
        // adds protection to bows and infinity to every other item as infinity is only useful on bows and protection is only useful on armor
        itemStack.addUnsafeEnchantment((itemStack.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);
        // returns the new itemstack
        return itemStack;
    }

    public UUID skullId() {
        return null;
    }

    public String skullValue() {
        return null;
    }

    public Color getColor() {
        return null;
    }

    public ItemType type() {
        return type;
    }

    public String[] fullSet() {
        return fullSet;
    }

    public void setFullSet(String[] fullSet) {
        this.fullSet = fullSet;
    }

    public int stackCount() {
        return stackCount;
    }

    public Player owner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }

    public boolean vanilla() {
        return vanilla;
    }

    public void setVanilla(boolean vanilla) {
        this.vanilla = vanilla;
    }

    public abstract Material getDefaultItem();

    public abstract String getDisplayName();

    public abstract AbilityList getAbilities();

    public abstract boolean isEnchanted();

    public abstract StatList getStats();

    public abstract String getDescription();

    public abstract Rarity getRarity();

    public void setFragged(boolean fragged) {
        if (fraggable()) {
            this.fragged = fragged;
        }
    }

    public boolean fraggable() {
        return false;
    }

    public void setStars(int stars) {
        if (starrable()) {
            this.stars = stars;
        }
    }

    public boolean starrable() {
        return false;
    }

    public void setReforge(String reforge) {
        if (type.reforgable() && ReforgeManager.getReforge(reforge) != null && ReforgeManager.getReforge(reforge).canApply(this)) {
            this.reforge = reforge;
        }
    }

    public boolean fragged() {
        return fragged;
    }

    public int stars() {
        return stars;
    }

    public String reforge() {
        return reforge;
    }

    public StatList getCombinedStats() {
        StatList stats = getStats();
        StatList base;
        base = Objects.requireNonNullElseGet(stats, StatList::new);
        for (IStatAddition a : statAdditions.asStatList()) {
            if (a.show()) {
                base = base.merge(a.getStats(owner));
            }
        }
        if (reforge != null && type.reforgable()) {
            StatList addition = DataUtils.getStatsOfBest(reforge, getRarity());
            if (addition != null) {
                base = base.merge(addition);
            }
        }
        return base;
    }

    public StatList getEnchantStats(Player p, Entity e) {
        Enchant[] enchants = this.enchants.asList();
        StatList returned = new StatList();
        for (Enchant en : enchants) {
            returned = returned.merge(en.getBonusStats(p, e));
        }
        return returned;
    }

    public AbilityList getCombinedAbilities() {
        AbilityList abilities = getAbilities();
        AbilityList base;
        base = Objects.requireNonNullElseGet(abilities, AbilityList::new);
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

    public boolean unique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public UUID uniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void updateSkyblockItem() {
        ItemStack i = new ItemStack(baseMaterial);
        if (stackCount > 0) {
            i.setAmount(stackCount);
        }
        if (isEnchanted()) {
            i = addGlow(i);
        }
        ItemMeta m = i.getItemMeta();
        if (m == null) {
            return;
        }
        if (getColor() != null) {
            if (m instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) m).setColor(getColor());
            }
        }
        m.setDisplayName(new NameBuilder().setName(getDisplayName()).setRarity(getRarity()).setFragged(fragged).setStars(stars).setReforge(reforge).build());
        m.setLore(new LoreBuilder()
                .setEnchants(enchants)
                .setReforge(type.reforgable() ? reforge : null)
                .setDescription(getDescription() != null ? getDescription() : "")
                .setRarity(getFinalRarity())
                .setStats(getCombinedStats(), statAdditions(), getRarity(), owner)
                .addAbilities(getCombinedAbilities())
                .setType(type)
                .build()
        );
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON);
        m.setUnbreakable(true);
        i.setItemMeta(m);

        NBTItem nbti = new NBTItem(i);
        nbti.setString("sb_id", id);
        nbti.setBoolean("sb_unique", unique);
        nbti.setBoolean("vanilla", vanilla);
        nbti.setBoolean("fragged", fragged);
        nbti.setInteger("stars", stars);
        nbti.setString("reforge", reforge);
        if (skullId() != null) {
            NBTCompound skull = nbti.addCompound("SkullOwner");
            skull.setString("Name", "sus");
            skull.setString("Id", skullId().toString());

            NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", skullValue());
        }
        if ((fragged || stars > 0 || (reforge != null && !reforge.equals(""))) && !unique) {
            unique = true;
            if (uniqueId == null) {
                this.uniqueId = UUID.randomUUID();
            }
            nbti.setBoolean("sb_unique", true);
            nbti.setUUID("sb_unique_id", uniqueId);
        }
        if (uniqueId == null) {
            if (unique) {
                UUID value = UUID.randomUUID();
                this.uniqueId = value;
                nbti.setUUID("sb_unique_id", value);
            }
        } else {
            if (unique) {
                nbti.setUUID("sb_unique_id", uniqueId);
            } else {
                nbti.removeKey("sb_unique_id");
                uniqueId = null;
            }
        }
        if (getStats() != null) {
            nbti = getStats().applyStats(nbti);
        }
        nbti = statAdditions.applyAdditions(nbti);
        nbti = rarityAdditions.applyAdditions(nbti);
        nbti = abilityAdditions.applyAdditions(nbti);
        nbti = enchants.applyEnchants(nbti);
        this.unique = nbti.getBoolean("sb_unique");
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

    public EnchantList enchants() {
        return enchants;
    }

    public String id() {
        return id;
    }
}
