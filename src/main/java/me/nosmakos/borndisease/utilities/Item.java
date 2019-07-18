package me.nosmakos.borndisease.utilities;

import me.nosmakos.borndisease.BornDisease;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Item {

    private static BornDisease plugin;
    private static boolean initialized = false;
    private static FileConfiguration config;

    public static void initialize(BornDisease plugin) {
        if (!initialized) {
            Item.plugin = plugin;
            initialized = true;
            config = plugin.getConfig();

        } else throw new IllegalStateException("Plugin was already initialized!");
    }

    public static final ItemStack GUI_GLASS = XMaterial.GRAY_STAINED_GLASS_PANE.buildX(" ", 1);

    public static void createItemHologram(org.bukkit.entity.Item item, String name) {
        item.setCustomName(RDUtils.colorize(name));
        item.setCustomNameVisible(true);
    }

    public static void dropItemHologram(Location loc, ItemStack itemStack) {
        org.bukkit.entity.Item drop = loc.getWorld().dropItemNaturally(loc, itemStack);
        drop.setCustomName(itemStack.getItemMeta().getDisplayName());
        drop.setCustomNameVisible(true);
    }

    public static ItemStack getItem(CureItem item) {
        int recharge = item.hasUses() && config.getString(item.get() + ".item-duration") == null ? item.getUses()
                : config.getInt(item.get() + ".item-duration");

        String name = item.hasUses() ? config.getString(item.get() + ".item-name") + ChatColor.GRAY + " «" + recharge + "»"
                : config.getString(item.get() + ".item-name");

        return new ItemBuilder(XMaterial.valueOf(config.getString(item.get() + ".item-type").toUpperCase()).pM())
                .setDurability((short) config.getInt(item.get() + ".item-durability"))
                .setName(name)
                .setLore(config.getString(item.get() + ".item-lore") != null ? config.getString(item.get() + ".item-lore").split("\\|") : null)
                .build();
    }

    public static ItemStack getItem(CureItem item, int amount) {
        int recharge = item.hasUses() && config.getString(item.get() + ".item-duration") == null ? item.getUses()
                : config.getInt(item.get() + ".item-duration");

        String name = item.hasUses() ? config.getString(item.get() + ".item-name") + ChatColor.GRAY + " «" + recharge + "»"
                : config.getString(item.get() + ".item-name");


        return new ItemBuilder(XMaterial.valueOf(config.getString(item.get() + ".item-type").toUpperCase()).pM())
                .setDurability((short) config.getInt(item.get() + ".item-durability"))
                .setAmount(config.getString(item.get() + ".item-duration") != null ? 1 : amount)
                .setName(name)
                .setLore(config.getString(item.get() + ".item-lore") != null ? config.getString(item.get() + ".item-lore").split("\\|") : null)
                .build();
    }

    public static void removeItem(Player player) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if ((hand != null) && (hand.getAmount() >= 1) && (player.getGameMode() != GameMode.CREATIVE)) {
            hand.setAmount(hand.getAmount() - 1);
        }
    }

    public static boolean hasItem(Player player, Material type, String name) {
        return notNull(player.getInventory()) && isItem(player.getInventory(), type, name);
    }

    public static boolean hasItem(Player player, CureItem item) {
        return notNull(player.getInventory()) && isItem(player.getInventory(), item);
    }

    public static boolean hasItem(Inventory inv, CureItem item) {
        return notNull(inv.getItem(0)) && isItem(inv.getItem(0), item);
    }

    private static boolean notNull(PlayerInventory inventory) {
        ItemStack hand = inventory.getItemInMainHand();
        return hand != null && hand.getType() != null && hand.hasItemMeta() && hand.getItemMeta() != null && hand.getItemMeta().getDisplayName() != null;
    }

    private static boolean notNull(ItemStack i) {
        return i != null && i.getType() != null && i.hasItemMeta() && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null;
    }

    private static boolean isItem(PlayerInventory inventory, Material type, String name) {
        return (inventory.getItemInMainHand().getType() == type) && (inventory.getItemInMainHand().getItemMeta().getDisplayName().contains(RDUtils.colorize(name)));
    }

    private static boolean isItem(PlayerInventory inventory, CureItem item) {
        return (inventory.getItemInMainHand().getType() == getMaterial(item) && (inventory.getItemInMainHand().getItemMeta().getDisplayName().contains(getDisplayName(item))));
    }

    private static boolean isItem(ItemStack i, CureItem item) {
        return (i.getType() == getMaterial(item) && (i.getItemMeta().getDisplayName().equals(getDisplayName(item))));
    }

    public static Material getMaterial(CureItem item) {
        return XMaterial.valueOf(config.getString(item.get() + ".item-type")).pM();
    }

    public static String getDisplayName(CureItem item) {
        return RDUtils.colorize(config.getString(item.get() + ".item-name"));
    }

    public static boolean getArmour(PlayerInventory inventory) {
        return inventory != null && inventory.getHelmet() != null && inventory.getChestplate() != null && inventory.getLeggings() != null && inventory.getBoots() != null;
    }
}