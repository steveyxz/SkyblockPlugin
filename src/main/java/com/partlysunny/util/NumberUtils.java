package com.partlysunny.util;

public class NumberUtils {

    public static int clamp(int number, int min, int max) {
        if (number < min) {
            return min;
        }
        return Math.min(number, max);
    }

}
