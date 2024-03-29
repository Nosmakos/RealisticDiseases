package me.nosmakos.borndisease.commands;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.WorldManagement;
import me.nosmakos.borndisease.utilities.Lang;
import me.nosmakos.borndisease.utilities.Permission;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AbstractCommand {
    public static final ChatColor RED = ChatColor.RED;
    public static final ChatColor GOLD = ChatColor.GOLD;
    public static final ChatColor DARK_GRAY = ChatColor.DARK_GRAY;
    public static final ChatColor WHITE = ChatColor.WHITE;
    public static final ChatColor GRAY = ChatColor.GRAY;
    public static final ChatColor GREEN = ChatColor.GREEN;

    private BornDisease plugin;
    private final WorldManagement management;
    private final String command;

    private final String[] help;
    private final Permission permission;

    public AbstractCommand(BornDisease plugin, String command, String help[], Permission permission) {
        this.plugin = plugin;
        this.command = command;
        this.help = help;
        this.permission = permission;
        this.management = plugin.getWorldManagement();
    }

    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player && (management.isDisabledInWorld(((Player) sender).getWorld()))) return;

        if (sender.hasPermission(permission.get()) || sender.hasPermission(Permission.OP.get())) {
            execute(sender, args);
        } else {
            sender.sendMessage(Lang.INSUFFICIENT_PERMISSIONS.get() + " - " + WHITE + permission.get() + RED + "");
        }
    }

    protected void execute(CommandSender sender, String[] args) {
        return;
    }

    public TextComponent getComponent(String text, ClickEvent.Action action, String actionText, String showText) {
        TextComponent message = new TextComponent(text);
        message.setClickEvent(new ClickEvent(action, actionText));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(showText).create()));

        return message;
    }

    public String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(Arrays.stream(this.help).collect(Collectors.joining("\n")));
        sender.sendMessage("");
    }

    public String getCommand() {
        return this.command;
    }

    public String[] getHelp() {
        return this.help;
    }

    public Permission getPermission() {
        return permission;
    }

    public BornDisease getPlugin() {
        return this.plugin;
    }

}