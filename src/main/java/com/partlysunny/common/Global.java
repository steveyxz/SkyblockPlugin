package com.partlysunny.common;

public class Global {

    public static int clamp(int number, int min, int max) {
        if (number < min) {
            return min;
        }
        return Math.min(number, max);
    }

}
