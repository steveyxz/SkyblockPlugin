package com.partlysunny.common;

import org.bukkit.util.ChatPaginator;

import java.util.List;

public class Global {

    public static int clamp(int number, int min, int max) {
        if (number < min) {
            return min;
        }
        return Math.min(number, max);
    }

    public static List<String> wrap(String text, int width) {
        return List.of(ChatPaginator.wordWrap(text, width));
    }

}
