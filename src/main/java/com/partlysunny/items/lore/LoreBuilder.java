package com.partlysunny.items.lore;

import com.partlysunny.enums.Rarity;
import com.partlysunny.items.abilities.Ability;
import com.partlysunny.items.abilities.AbilityList;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatList;
import com.partlysunny.stats.StatType;
import com.partlysunny.util.TextUtils;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    public LoreBuilder setStats(StatList stats) {
        //TODO add stat lore addition markings (like the text after each stat)
        List<ItemStat> listed = new ArrayList<>(stats.statList.values());
        Comparator<ItemStat> compareByType = Comparator.comparingInt(o -> (o.type().level()));
        listed.sort(compareByType);
        boolean greened = false;
        statLore.clear();
        for (ItemStat s : listed) {
            StatType type = s.type();
            if (!greened && type.isGreen()) {
                statLore.add("");
                greened = true;
            }
            if (type.isGreen()) {
                statLore.add(ChatColor.GRAY + type.displayName() + ": " + ChatColor.GREEN + "+" + trim(s.value()) + (type.percent() ? "%" : ""));
            } else {
                statLore.add(ChatColor.GRAY + type.displayName() + ": " + ChatColor.RED + "+" + trim(s.value()) + (type.percent() ? "%" : ""));
            }
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
