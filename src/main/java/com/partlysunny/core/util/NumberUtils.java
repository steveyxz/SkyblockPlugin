package com.partlysunny.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class NumberUtils {

    public static int clamp(int number, int min, int max) {
        if (number < min) {
            return min;
        }
        return Math.min(number, max);
    }

    public static int getColor(int r, int g, int b) {
        System.out.println(r + ", " + b + ", " + g + ": " + (r<<16 + g<<8 + b));
        return r<<16 + g<<8 + b;
    }

    /*
    public static UUID getUUIDFromIntArray(int[] input) {
        return UUID.fromString(getUUIDPart(Math.abs(input[0])) + "-" + getUUIDPart(Math.abs(input[1])) + "-" + getUUIDPart(Math.abs(input[2])) + "-" + getUUIDPart(Math.abs(input[3])));
    }

    private static String getUUIDPart(int value) {
        String s = String.valueOf(value);
        String extra = "0".repeat(8 - s.length());
        return extra + s;
    }
     */

    public static int[] getArrayFromUUID(UUID id) {
        String s = id.toString();
        int[] returned = new int[4];
        s = s.replace("-", "");
        for (int i = 0; i < 4; i++) {
            returned[i] = Integer.parseInt(s.substring(i, i + 8));
        }
        return returned;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getIntegerStringOf(double value, int round) {
        String s = Double.toString(round(value, round));
        if (s.contains("E")) {
            s = new BigDecimal(s).toPlainString();
        }
        if (s.endsWith(".0")) {
            return s.substring(0, s.length() - 2);
        }
        return s;
    }

}
