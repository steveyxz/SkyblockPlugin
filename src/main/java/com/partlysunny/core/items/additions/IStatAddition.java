package com.partlysunny.core.items.additions;

import com.partlysunny.core.stats.StatList;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public interface IStatAddition {

    //CALCULATE THE FINAL STATS, NOT JUST FOR ONE ADDITION (include amount)
    @Nullable
    String getLore(Player player);

    StatList getStats(@Nullable Player player);

    boolean show();

}
