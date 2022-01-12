package com.partlysunny.core.items.lore;

import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.enums.Rarity;
import com.partlysunny.core.items.ItemType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.abilities.Ability;
import com.partlysunny.core.items.abilities.AbilityList;
import com.partlysunny.core.items.abilities.AbilityType;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.AdditionList;
import com.partlysunny.core.items.additions.IStatAddition;
import com.partlysunny.core.items.additions.reforges.Reforge;
import com.partlysunny.core.items.additions.reforges.ReforgeManager;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.util.DataUtils;
import com.partlysunny.core.util.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;

public class LoreBuilder {

    private final List<String> lore = new ArrayList<>();
    private final List<String> statLore = new ArrayList<>();
    private final List<String> abilityLore = new ArrayList<>();
    private final List<String> statAbilityLore = new ArrayList<>();
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
            if (a.type().weapon()) {
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
            } else {
                if (a.type() == AbilityType.PASSIVE) {
                    abilityLore.add(ChatColor.GOLD + "Ability: " + a.name());
                    if (split.size() > 1) {
                        for (String s : split) {
                            abilityLore.add(ChatColor.GRAY + s.substring(2));
                        }
                    } else {
                        for (String s : split) {
                            abilityLore.add(ChatColor.GRAY + s);
                        }
                    }
                } else {
                    abilityLore.add(ChatColor.GOLD + (a.type() == AbilityType.FULL_SET_BONUS ? "Full Set Bonus: " : "Piece Bonus: ") + a.name());
                    if (split.size() > 1) {
                        for (String s : split) {
                            abilityLore.add(ChatColor.GRAY + s.substring(2));
                        }
                    } else {
                        for (String s : split) {
                            abilityLore.add(ChatColor.GRAY + s);
                        }
                    }
                }
            }
        }
        return this;
    }

    //Must be called AFTER setReforge or will bug out
    public LoreBuilder setStats(StatList stats, AdditionList additions, Rarity rarity, Player player) {
        if (reforge != null) {
            setStats(stats, additions, DataUtils.getStatsOfBest(reforge.id(), rarity), reforge.displayName(), player);
        } else {
            setStats(stats, additions, null, null, player);
        }
        return this;
    }

    //Automatically called on setstats. No need to call again
    public LoreBuilder setStatLore(AdditionList additions, Player player) {
        if (additions != null) {
            if (additions.accepting() != ModifierType.STAT) {
                throw new IllegalArgumentException("Additions argument is of wrong type (not of type STAT)");
            }
            List<Addition> additionList = additions.asArrayList();
            for (Addition a : additionList) {
                statAbilityLore.add("");
                IStatAddition isa = (IStatAddition) a;
                String lore = isa.getLore(player);
                if (lore != null) {
                    //TODO fix the bug where lines of lore have no color
                    List<String> formatted = TextUtils.wrap(TextUtils.getHighlightedText(lore), 30);
                    if (formatted.size() > 1) {
                        for (String s : formatted) {
                            statAbilityLore.add(ChatColor.GRAY + s.substring(2));
                        }
                    } else {
                        for (String s : formatted) {
                            statAbilityLore.add(ChatColor.GRAY + s);
                        }
                    }
                }
            }
        }
        return this;
    }

    public LoreBuilder setStats(StatList stats, AdditionList additions, StatList reforgeBonus, String reforgeName, Player player) {
        Map<StatType, HashMap<AdditionInfo, Double>> sorted;
        TreeMap<StatType, HashMap<AdditionInfo, Double>> realSorted = null;
        if (additions != null) {
            if (additions.accepting() != ModifierType.STAT) {
                throw new IllegalArgumentException("Additions argument is of wrong type (not of type STAT)");
            }
            setStatLore(additions, player);
            List<Addition> additionList = additions.asArrayList();
            sorted = new HashMap<>();
            for (Addition a : additionList) {
                IStatAddition isa = (IStatAddition) a;
                for (Stat s : isa.getStats(player).asList()) {
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
        Stat[] reforgeAdditions = null;
        if (reforgeBonus != null && reforgeName != null) {
            reforgeAdditions = reforgeBonus.asList();
        }
        List<Stat> listed = new ArrayList<>(stats.statList.values());
        Comparator<Stat> compareByType = Comparator.comparingInt(o -> (o.type().level()));
        listed.sort(compareByType);
        statLore.clear();
        for (Stat s : listed) {
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
                for (Stat reforgeStat : reforgeAdditions) {
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
        if (statAbilityLore.size() > 0) {
            if (!hasDescription && statLore.size() > 0) {
                lore.remove(lore.size() - 1);
            }
            lore.addAll(statAbilityLore);
        } else {
            if (lore.size() > 0 && hasDescription) {
                lore.add("");
            }
        }
        if (abilityLore.size() > 0) {
            lore.addAll(abilityLore);
            lore.add("");
        } else {
            if (lore.size() > 0 && statAbilityLore.size() > 0) {
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
