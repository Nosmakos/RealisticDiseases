package me.nosmakos.borndisease.items;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.WorldManagement;
import me.nosmakos.borndisease.utilities.CureItem;
import me.nosmakos.borndisease.utilities.DiseaseType;
import me.nosmakos.borndisease.utilities.Lang;
import me.nosmakos.borndisease.utilities.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AbstractItem {

    private final BornDisease plugin;
    private final WorldManagement management;
    private CureItem itemType;
    private int cooldown;
    private boolean cancelled;
    private Permission permission;

    private Set<UUID> control = new HashSet<>();

    public AbstractItem(BornDisease plugin, CureItem itemType, int cooldown, Permission permission, boolean cancelled) {
        this.plugin = plugin;
        this.itemType = itemType;
        this.cooldown = cooldown;
        this.permission = permission;
        this.cancelled = cancelled;
        this.management = plugin.getWorldManagement();
    }


    public void use(Player player) {
        if (control.contains(player.getUniqueId()) || management.isDisabledInWorld(player.getWorld())) return;
        control.add(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(plugin, () -> control.remove(player.getUniqueId()), this.cooldown);

        if (player.hasPermission(permission.get()) || player.hasPermission(Permission.ITEMS_OP.get()) || player.hasPermission(Permission.OP.get())) {
            interact(player);

        } else {
            player.sendMessage(Lang.NO_PERMISSION.get());
        }
    }

    protected void interact(Player player) {
        return;
    }

    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public int getUses(Player player) {
        return Integer.parseInt(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().split("[«»]")[1]);
    }

    public int hasUses(){
        return itemType.hasUses() && plugin.getConfig().getString(itemType.get() + ".item-uses") == null ? itemType.getUses() : plugin.getConfig().getInt(itemType.get() + ".item-uses");
    }

    public boolean hasDisease(Player player, DiseaseType type){
        return plugin.getUserManagement().hasDiseaseType(player, type);
    }

    public CureItem getItemType() {
        return this.itemType;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Permission getPermission() {
        return permission;
    }

    public BornDisease getPlugin() {
        return this.plugin;
    }
}
