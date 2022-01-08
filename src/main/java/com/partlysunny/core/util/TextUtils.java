package com.partlysunny.core.util;

import com.partlysunny.core.stats.StatType;
import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

import java.util.List;

public class TextUtils {

    public static List<String> wrap(String text, int width) {
        return List.of(ChatPaginator.wordWrap(text, width));
    }

    public static String capitalizeWord(String str) {
        String[] words = str.split("\\s");
        StringBuilder capitalizeWord = new StringBuilder();
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterFirst = w.substring(1);
            capitalizeWord.append(first.toUpperCase()).append(afterFirst).append(" ");
        }
        return capitalizeWord.toString().trim();
    }

    /**
     * Updates text, replacing stat names with their color and adding their
     * symbol in front. Also will highlight text green if surrounded with %%
     * and red if surrounded by @@.
     * @param text The description / ability text to update
     * @return New text
     */
    public static String getHighlightedText(String text) {
        for (StatType t : StatType.values()) {
            if (t == StatType.DAMAGE) {
                continue;
            }
            text = text.replace(t.displayName(), t.color() + t.symbol() + " " + t.displayName() + ChatColor.GRAY);
        }
        boolean isGreen = false;
        boolean isRed = false;
        int count = 0;
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (count < chars.length - 1) {
                if (c == '%' && chars[count + 1] == '%') {
                    if (!isGreen) {
                        text = text.replaceFirst("%%", ChatColor.GREEN.toString());
                        isGreen = true;
                        isRed = false;
                    } else {
                        text = text.replaceFirst("%%", ChatColor.GRAY.toString());
                        isGreen = false;
                    }
                }
                if (c == '@' && chars[count + 1] == '@') {
                    if (!isRed) {
                        text = text.replaceFirst("@@", ChatColor.RED.toString());
                        isRed = true;
                        isGreen = false;
                    } else {
                        text = text.replaceFirst("@@", ChatColor.GRAY.toString());
                        isRed = false;
                    }
                }
            }
            count++;
        }
        return text;
    }

}
