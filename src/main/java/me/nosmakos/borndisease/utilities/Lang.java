package me.nosmakos.borndisease.utilities;

import me.nosmakos.borndisease.BornDisease;
import org.bukkit.ChatColor;

public enum Lang {
    NO_PERMISSION("&cYou do not have the permission to do that."),
    INSUFFICIENT_PERMISSIONS("&cInsufficient Permissions"),
    PLAYER_NOT_FOUND("Player not found online."),
    SPECIFY_PLAYER("Specify a player."),
    SPECIFY_AMOUNT("Specify an amount."),
    SPECIFY_ITEM("Specify an item type."),
    NOT_VALID_ITEM("&cInvalid item."),
    NOT_VALID_PERK("&cInvalid perk."),
    NOT_VALID_AMOUNT("&cInvalid amount."),
    NOT_ENOUGH_SPACE("&cNot enough space."),
    GAVE("&7You successfully gave"),
    SYRINGE_TITLE("&3Syringe Liquids"),
    DIABETES("&7(&c!&7)&c Your player has Diabetes. Make sure to use &3Insulin &cwhenever it is required."),
    ASTHMA("&7(&c!&7)&c Your player has Asthma. Make sure to use &3Rescue Inhalers &cwhenever it is required."),
    HEART_DISEASE("&7(&c!&7)&c Your player has Heart Disease. Make sure to use &3Nitroglycerin &cwhenever it is required.");

    private String type;
    private BornDisease plugin;

    Lang(String type) {
        this.type = type;
        plugin = BornDisease.getPlugin(BornDisease.class);
    }

    public String get() {
        String value = plugin.getLanguageConfig().getString(name());
        if (value == null) {
            plugin.getLogger().warning("Missing lang message data: " + name());
            value = "&c[Missing lang message data - '" + name() + "']";
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}