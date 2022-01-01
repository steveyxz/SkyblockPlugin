package com.partlysunny.core;

import com.partlysunny.Skyblock;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ConsoleLogger {

    private static final Logger log = JavaPlugin.getPlugin(Skyblock.class).getLogger();

    public static void console(String msg) {
        log.info(msg);
    }

    public static void console(String[] msg) {
        for (String s : msg) {
            log.info(s);
        }
    }
}
