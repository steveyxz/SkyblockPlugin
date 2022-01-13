package com.partlysunny.core.player;

import com.partlysunny.Skyblock;
import com.partlysunny.core.items.SkyblockItem;
import com.partlysunny.core.stats.Stat;
import com.partlysunny.core.stats.StatList;
import com.partlysunny.core.stats.StatType;
import com.partlysunny.core.util.DataUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static com.partlysunny.core.entities.DamageManager.updatePlayerHealthBar;
import static com.partlysunny.core.player.PlayerStatManager.playerStats;
import static com.partlysunny.core.util.NumberUtils.getIntegerStringOf;

public class PlayerUpdater implements Listener {

    public PlayerUpdater(Server s) {
        new ConstantUpdater(s).runTaskTimer(JavaPlugin.getPlugin(Skyblock.class), 0, 10);
        new NaturalRegeneration(s).runTaskTimer(JavaPlugin.getPlugin(Skyblock.class), 0, 20);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p) {
            updatePlayer(p);
        }
    }

    public static StatList getStats(Player player, boolean ignoreHand) {
        UUID id = player.getUniqueId();
        StatList oldStats = playerStats.get(id);
        StatList newStats = BaseStatManager.getStatListOf(id);
        PlayerInventory inventory = player.getInventory();
        SkyblockItem helmet = DataUtils.getSkyblockItem(inventory.getHelmet(), player);
        SkyblockItem chestplate = DataUtils.getSkyblockItem(inventory.getChestplate(), player);
        SkyblockItem leggings = DataUtils.getSkyblockItem(inventory.getLeggings(), player);
        SkyblockItem boots = DataUtils.getSkyblockItem(inventory.getBoots(), player);
        SkyblockItem hand = DataUtils.getSkyblockItem(inventory.getItemInMainHand(), player);
        if (ignoreHand) {
            hand = null;
        }
        if (helmet != null) {
            newStats = newStats.merge(helmet.getCombinedStats());
        }
        if (chestplate != null) {
            newStats = newStats.merge(chestplate.getCombinedStats());
        }
        if (leggings != null) {
            newStats = newStats.merge(leggings.getCombinedStats());
        }
        if (boots != null) {
            newStats = newStats.merge(boots.getCombinedStats());
        }
        if (hand != null) {
            newStats = newStats.merge(hand.getCombinedStats());
        }
        newStats.addStat(new Stat(StatType.HEALTH, oldStats.getStat(StatType.HEALTH)));
        newStats.addStat(new Stat(StatType.MANA, oldStats.getStat(StatType.MANA)));
        return newStats;
    }

    public static void updatePlayer(Player player) {
        StatList stats = getStats(player, false);
        double health = PlayerStatManager.getStat(player.getUniqueId(), StatType.HEALTH);
        double maxHealth = PlayerStatManager.getStat(player.getUniqueId(), StatType.MAX_HEALTH);
        updatePlayerHealthBar(player, health, maxHealth);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getPlayerDisplay(stats, false)));
        //NMS version
        //ActionBar.sendToPlayer(player, getPlayerDisplay(stats, false));
        playerStats.put(player.getUniqueId(), stats);
    }

    public static String getPlayerDisplay(StatList stats, boolean notEnoughMana) {
        return ChatColor.RED + "" + getIntegerStringOf(stats.getStat(StatType.HEALTH), 0) + "/" + getIntegerStringOf(stats.getStat(StatType.MAX_HEALTH), 0) + "❤   " + ChatColor.GREEN + "" + getIntegerStringOf(stats.getStat(StatType.DEFENSE), 0) + "❈   " + (notEnoughMana ? ChatColor.RED + "NOT ENOUGH MANA" : ChatColor.AQUA + "" + getIntegerStringOf(stats.getStat(StatType.MANA), 0) + "/" + getIntegerStringOf(stats.getStat(StatType.INTELLIGENCE), 0) + "✎");
    }
}

class NaturalRegeneration extends BukkitRunnable {
    private final Server s;

    public NaturalRegeneration(Server s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (Player p : s.getOnlinePlayers()) {
            UUID uniqueId = p.getUniqueId();
            double regenSpeed = PlayerStatManager.getStat(uniqueId, StatType.HEALTH_REGEN_SPEED);
            double maxHealth = PlayerStatManager.getStat(uniqueId, StatType.MAX_HEALTH);
            double health = PlayerStatManager.getStat(uniqueId, StatType.HEALTH);
            double maxMana = PlayerStatManager.getStat(uniqueId, StatType.INTELLIGENCE);
            double manaSpeed = PlayerStatManager.getStat(uniqueId, StatType.MANA_REGEN_SPEED);
            double mana = PlayerStatManager.getStat(uniqueId, StatType.MANA);
            PlayerStatManager.setStat(uniqueId, StatType.HEALTH, Math.min(health + (maxHealth * (regenSpeed / 100)), maxHealth));
            PlayerStatManager.setStat(uniqueId, StatType.MANA, Math.min(mana + (maxMana * (manaSpeed / 100)), maxMana));
        }
    }
}

class ConstantUpdater extends BukkitRunnable {
    private final Server s;

    public ConstantUpdater(Server s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (Player p : s.getOnlinePlayers()) {
            PlayerUpdater.updatePlayer(p);
        }
    }
}