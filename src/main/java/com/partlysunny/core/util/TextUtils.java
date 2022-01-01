package com.partlysunny.core.util;

import org.bukkit.util.ChatPaginator;

import java.util.List;

public class TextUtils {

    public static List<String> wrap(String text, int width) {
        return List.of(ChatPaginator.wordWrap(text, width));
    }

}
