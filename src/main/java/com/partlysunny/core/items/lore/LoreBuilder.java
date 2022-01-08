package com.partlysunny.core.items.lore;

import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.AdditionList;
import com.partlysunny.core.items.additions.IStatAddition;
import com.partlysunny.core.items.additions.reforges.Reforge;
import com.partlysunny.core.items.additions.reforges.ReforgeManager;
import com.partlysunny.core.stats.ItemStat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.core.util.TextUtils;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.*;

public class LoreBuilder {

    private final List<String> lore = new ArrayList<>();
    private final List<String> statLore = new ArrayList<>();
    private final List<String> abilityLore = new ArrayList<>();
    private String description = "";
    private Reforge reforge;
    private Rarity r = Rarity.COMMON;
    private ItemType type = ItemType.ITEM;

    public LoreBuilder setReforge(String r) {
        reforge = ReforgeManager.getReforge(r);
        return this;
    }

    public LoreBuilder setDescription(String description) {
        this.description = TextUtils.getHighlightedText(description);
        return this;
    }

    public LoreBuilder addAbilities(AbilityList abilities) {
        for (Ability a : abilities.asList()) {
            String desc = a.description();
            List<String> split = new ArrayList<>(TextUtils.wrap(desc, 30));
            abilityLore.add("");
            if (!Objects.equals(a.type().toString(), "")) {
                abilityLore.add(ChatColor.GOLD + "Ability: " + a.name() + " " + ChatColor.YELLOW + ChatColor.BOLD + a);
            }
            if (split.size() > 1) {
                for (String s : split) {
                    abilityLore.add(ChatColor.GRAY + s.substring(2));
                }
            } else {
                for (String s : split) {
                    abilityLore.add(ChatColor.GRAY + s);
                }
            }
            if (a.manaCost() > 0) {
                abilityLore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + a.manaCost());
            }
            if (a.soulflowCost() > 0) {
                abilityLore.add(ChatColor.DARK_GRAY + "Soulflow Cost: " + ChatColor.DARK_AQUA + a.soulflowCost());
            }
            if (a.cooldown() > 0) {
                abilityLore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + a.cooldown() + "s");
            }
        }
        return this;
    }

    //Must be called AFTER setReforge or will bug out
    public LoreBuilder setStats(StatList stats, AdditionList additions, Rarity rarity) {
        if (reforge != null) {
            setStats(stats, additions, DataUtils.getStatsOfBest(reforge.id(), rarity), reforge.displayName());
        } else {
            setStats(stats, additions, null, null);
        }
        return this;
    }

    public LoreBuilder setStats(StatList stats, AdditionList additions, StatList reforgeBonus, String reforgeName) {
        Map<StatType, HashMap<AdditionInfo, Double>> sorted;
        TreeMap<StatType, HashMap<AdditionInfo, Double>> realSorted = null;
        if (additions != null) {
            if (additions.accepting() != ModifierType.STAT) {
                throw new IllegalArgumentException("Additions argument is of wrong type (not of type STAT)");
            }
            List<Addition> additionList = additions.asArrayList();
            sorted = new HashMap<>();
            for (Addition a : additionList) {
                IStatAddition isa = (IStatAddition) a;
                for (ItemStat s : isa.getStats().asList()) {
                    if (sorted.containsKey(s.type())) {
                        sorted.get(s.type()).put(a.type(), s.value());
                    } else {
                        sorted.put(s.type(), new HashMap<>());
                        sorted.get(s.type()).put(a.type(), s.value());
                    }
                }
            }
            realSorted = new TreeMap<>(Comparator.comparingInt(StatType::level));
            realSorted.putAll(sorted);
        }
        ItemStat[] reforgeAdditions = null;
        if (reforgeBonus != null && reforgeName != null) {
            reforgeAdditions = reforgeBonus.asList();
        }
        List<ItemStat> listed = new ArrayList<>(stats.statList.values());
        Comparator<ItemStat> compareByType = Comparator.comparingInt(o -> (o.type().level()));
        listed.sort(compareByType);
        statLore.clear();
        for (ItemStat s : listed) {
            StatType type = s.type();
            StringBuilder stat = new StringBuilder();
            if (type.isGreen()) {
                stat.append(ChatColor.GRAY).append(type.displayName()).append(": ").append(ChatColor.GREEN).append("+").append(getIntegerStringOf(s.value())).append(type.percent() ? "%" : "");
            } else {
                stat.append(ChatColor.GRAY).append(type.displayName()).append(": ").append(ChatColor.RED).append("+").append(getIntegerStringOf(s.value())).append(type.percent() ? "%" : "");
            }
            if (additions != null) {
                if (realSorted.containsKey(s.type())) {
                    HashMap<AdditionInfo, Double> sortedValue = realSorted.get(s.type());
                    for (AdditionInfo t : sortedValue.keySet()) {
                        if (t.bt() == null || t.shownLevel() == null || t.color() == null) {
                            continue;
                        }
                        Double amount = sortedValue.get(t);
                        stat.append(" ").append(t.color()).append(t.bt().start()).append(amount > -1 ? "+" : "-").append(getIntegerStringOf(amount)).append(type.percent() ? "%" : "").append(t.bt().end());
                    }
                }
            }
            if (reforgeAdditions != null) {
                for (ItemStat reforgeStat : reforgeAdditions) {
                    if (reforgeStat.type() == type) {
                        stat.append(" ").append(ChatColor.BLUE).append("(").append(reforgeName).append(" +").append(getIntegerStringOf(reforgeStat.value())).append(")");
                    }
                }
            }
            statLore.add(String.valueOf(stat));
        }
        return this;
    }

    private String getIntegerStringOf(double value) {
        String s = Double.toString(value);
        if (s.contains("E")) {
            s = new BigDecimal(s).toPlainString();
        }
        if (s.endsWith(".0")) {
            return s.substring(0, s.length() - 2);
        }
        return s;
    }

    public LoreBuilder setRarity(Rarity r) {
        this.r = r;
        return this;
    }

    public LoreBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public List<String> build() {
        if (statLore.size() > 0) {
            lore.addAll(statLore);
            lore.add("");
        }
        boolean hasDescription = false;
        if (!Objects.equals(description, "")) {
            hasDescription = true;
            List<String> desc = TextUtils.wrap(description, 30);
            if (desc.size() > 1) {
                for (String s : desc) {
                    lore.add(ChatColor.GRAY + s.substring(2));
                }
            } else {
                for (String s : desc) {
                    lore.add(ChatColor.GRAY + s);
                }
            }
        }
        if (abilityLore.size() > 0) {
            lore.addAll(abilityLore);
            lore.add("");
        } else {
            if (lore.size() > 0 && hasDescription) {
                lore.add("");
            }
        }
        if (reforge != null && reforge.bonusDesc() != null) {
            lore.add(ChatColor.BLUE + reforge.displayName() + " Bonus");
            List<String> reforgeText = TextUtils.wrap(TextUtils.getHighlightedText(reforge.bonusDesc()), 30);
            if (reforgeText.size() > 1) {
                for (String s : reforgeText) {
                    lore.add(ChatColor.GRAY + s.substring(2));
                }
            } else {
                for (String s : reforgeText) {
                    lore.add(ChatColor.GRAY + s);
                }
            }
            lore.add("");
        }
        if (type.reforgable()) {
            lore.add(ChatColor.DARK_GRAY + "This item can be reforged!");
        }
        lore.add(r.color() + "" + ChatColor.BOLD + r + " " + type.display().toUpperCase());
        return lore;
    }

}
