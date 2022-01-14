package com.partlysunny.additions.stat;

import com.partlysunny.core.enums.BracketType;
import com.partlysunny.core.items.ModifierType;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.items.additions.Addition;
import com.partlysunny.core.items.additions.AdditionInfo;
import com.partlysunny.core.items.additions.AppliableTypeDefaults;
import com.partlysunny.core.items.additions.IStatAddition;
import com.partlysunny.core.player.PlayerStatManager;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Infusion extends Addition implements IStatAddition {

    public Infusion() {
        this(null);
    }

    public Infusion(SkyblockItem parent) {
        super(new AdditionInfo("infusion", 1, ModifierType.STAT, Infusion.class, 1, ChatColor.GOLD, BracketType.SQUARE, AppliableTypeDefaults.armor), parent);
    }

    @Nullable
    @Override
    public String getLore(Player player) {
        return "Grants you Crit Damage and Strength based on your %%health%%! Also increases your Speed Cap if above @@50%@@ Health";
    }

    @Override
    public StatList getStats(Player player) {
        double health;
        if (player == null) {
            health = 100;
        } else {
            health = PlayerStatManager.getStat(player.getUniqueId(), StatType.HEALTH);
        }
        StatList statList = new StatList(
                new Stat(StatType.CRIT_DAMAGE, health / 2),
                new Stat(StatType.STRENGTH, health / 2)
        );
        if (player != null) {
            if (health > PlayerStatManager.getStat(player.getUniqueId(), StatType.MAX_HEALTH) / 2) {
                statList.addStat(new Stat(StatType.SPEED_CAP, 100));
            }
        }
        return statList;
    }

    @Override
    public boolean show() {
        return false;
    }
}
