package com.partlysunny;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ConsoleLogger {

    private static Logger log = JavaPlugin.getPlugin(Skyblock.class).getLogger();

    public static void console(String msg){
        log.info(msg);
    }

    public static void console(String[] msg){
        for(String s : msg){
            log.info(s);
        }
    }
}
