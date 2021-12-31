package com.partlysunny.items.lore;

import com.partlysunny.enums.Rarity;
import com.partlysunny.items.ModifierType;
import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.AbilityList;
import com.partlysunny.items.additions.Addition;
import com.partlysunny.items.additions.AdditionList;
import com.partlysunny.items.additions.AdditionType;
import com.partlysunny.items.additions.common.stat.IStatAddition;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;
import com.partlysunny.util.TextUtils;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.*;

public class LoreBuilder {

    private final List<String> lore = new ArrayList<>();
    private final List<String> statLore = new ArrayList<>();
    private final List<String> abilityLore = new ArrayList<>();
    private String description = "";
    private Rarity r = Rarity.COMMON;

    public LoreBuilder setDescription(String description) {
        this.description = description;
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
        }
        return this;
    }

    public LoreBuilder setStats(StatList stats, AdditionList additions) {
        if (additions.accepting() != ModifierType.STAT) {
            throw new IllegalArgumentException("Additions argument is of wrong type (not of type STAT)");
        }
        List<ItemStat> listed = new ArrayList<>(stats.statList.values());
        Comparator<ItemStat> compareByType = Comparator.comparingInt(o -> (o.type().level()));
        listed.sort(compareByType);
        boolean greened = false;
        statLore.clear();
        List<Addition> additionList = additions.asArrayList();
        HashMap<StatType, HashMap<AdditionType, Double>> sorted = new HashMap<>();
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
        for (ItemStat s : listed) {
            StatType type = s.type();
            if (!greened && type.isGreen()) {
                statLore.add("");
                greened = true;
            }
            StringBuilder stat = new StringBuilder();
            if (type.isGreen()) {
                stat.append(ChatColor.GRAY).append(type.displayName()).append(": ").append(ChatColor.GREEN).append("+").append(trim(s.value())).append(type.percent() ? "%" : "");
            } else {
                stat.append(ChatColor.GRAY).append(type.displayName()).append(": ").append(ChatColor.RED).append("+").append(trim(s.value())).append(type.percent() ? "%" : "");
            }
            if (sorted.containsKey(s.type())) {
                HashMap<AdditionType, Double> sortedValue = sorted.get(s.type());
                for (AdditionType t : sortedValue.keySet()) {
                    Double amount = sortedValue.get(t);
                    stat.append(" ").append(t.color()).append(t.bt().start()).append(amount > -1 ? "+" : "-").append(trim(amount)).append(type.percent() ? "%" : "").append(t.bt().end());
                }
            }
            statLore.add(String.valueOf(stat));
        }
        return this;
    }

    private String trim(double value) {
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

    public List<String> build() {
        lore.addAll(statLore);
        lore.add("");
        if (!Objects.equals(description, "")) {
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
            lore.add("");
        }
        lore.remove(lore.size() - 1);
        if (abilityLore.size() > 0) {
            lore.addAll(abilityLore);
            lore.add("");
        }
        lore.add(r.color() + "" + ChatColor.BOLD + r);
        return lore;
    }

}
