package me.nosmakos.borndisease.utilities;

import org.bukkit.ChatColor;

public class RDUtils {

    public static String s(int amount) {
        return (amount > 1) ? "s" : "";
    }

    public static String s(String s, int amount) {
        return (!s.endsWith("s")) && (amount > 1) ? "s" : "";
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String progress(double current, double max, double totalBars) {
        final double progressBars = totalBars * (float) current / max;
        final StringBuilder sb = new StringBuilder("&a");
        for (double i = 0; i < progressBars; i++) {
            sb.append("❙");
        }
        sb.append("&7");
        for (double i = 0; i < totalBars - progressBars; i++) {
            sb.append("❙");
        }
        return colorize(sb.toString());
    }
}
