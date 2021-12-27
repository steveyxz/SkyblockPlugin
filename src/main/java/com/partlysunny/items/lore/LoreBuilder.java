package com.partlysunny.items.lore;

import com.partlysunny.common.Global;
import com.partlysunny.common.Rarity;
import com.partlysunny.items.lore.abilities.Ability;
import com.partlysunny.stats.ItemStat;
import com.partlysunny.stats.StatType;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.*;

public class LoreBuilder {

    private final List<String> lore = new ArrayList<>();
    private final List<String> statLore = new ArrayList<>();
    private String description = "";
    private Rarity r = Rarity.COMMON;

    public LoreBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public LoreBuilder addAbilities(Ability... abilities) {
        return this;
    }

    public LoreBuilder setStats(ItemStat... stats) {
        List<ItemStat> listed = new LinkedList<>(Arrays.asList(stats));
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
        lore.add("");
        if (!Objects.equals(description, "")) {
            List<String> desc = Global.wrap(description, 30);
            for (String s : desc) {
                lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + s);
            }
            lore.add(ChatColor.RESET + "");
        }
        lore.addAll(statLore);
        lore.add("");
        lore.add(r.color() + "" + ChatColor.BOLD + r);
        return lore;
    }

}
