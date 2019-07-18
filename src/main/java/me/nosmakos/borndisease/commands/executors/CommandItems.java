package me.nosmakos.borndisease.commands.executors;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.commands.AbstractCommand;
import me.nosmakos.borndisease.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandItems extends AbstractCommand {

    private BornDisease plugin;
    private static final String COMMAND = "items";
    private static final Permission PERMISSION = Permission.ITEMS_COMMAND;
    private static final String[] HELP = {
            GRAY + "Use: " + RED + "/RD items list" + GRAY + " - to get the list of all the available custom items.",
            GRAY + "Use: " + RED + "/RD items give [player] [item] [amount]" + GRAY + " - to give a custom item on the selected player with the defined amount."
    };
    private static final String[] LIST = {
            RED + "-----------------------------------------------------",
            GRAY + "Displaying all the available custom items:",
            " ",
            GRAY + "Usable Tablets or Inhalers:",
            RED + "> " + GRAY + "Rescue_Inhaler",
            RED + "> " + GRAY + "Naproxen",
            RED + "> " + GRAY + "Aspirin",
            RED + "> " + GRAY + "Ibuprofen",
            " ",
            GRAY + "Liquids:",
            RED + "> " + GRAY + "Nitroglycerin",
            RED + "> " + GRAY + "Insulin",
            RED + "> " + GRAY + "Alteplase",
            " ",
            GRAY + "Syringes:",
            RED + "> " + GRAY + "Nitroglycerin_Syringe",
            RED + "> " + GRAY + "Insulin_Syringe",
            RED + "> " + GRAY + "Alteplase_Syringe",
            RED + "> " + GRAY + "Syringe",
            RED + "-----------------------------------------------------"
    };

    public CommandItems(BornDisease plugin) {
        super(plugin, COMMAND, HELP, PERMISSION);
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (args.length == 0 || args.length >= 2 && !args[0].equalsIgnoreCase("give")) {
            sendHelp(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("give")) {
                sender.sendMessage(Lang.SPECIFY_PLAYER.get());

            } else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(Arrays.stream(LIST).collect(Collectors.joining("\n")));
            } else {
                sendHelp(sender);
            }
        } else if (args.length == 2 || args.length == 3) {
            String specify = args.length == 2 ? Lang.SPECIFY_ITEM.get() : Lang.SPECIFY_AMOUNT.get();
            sender.sendMessage(specify);

        } else if (args.length == 4) {
            Player selectedPlayer = Bukkit.getServer().getPlayer(args[1]);
            try {
                if (selectedPlayer == null) {
                    sender.sendMessage(Lang.PLAYER_NOT_FOUND.get());
                    return;
                }
                if (plugin.getWorldManagement().isDisabledInWorld(selectedPlayer.getWorld())) return;
                if (selectedPlayer.getInventory().firstEmpty() == -1) {
                    sender.sendMessage(Lang.NOT_ENOUGH_SPACE.get());
                    return;
                }
                int amount = Integer.parseInt(args[3]);
                if (amount <= 0) {
                    sender.sendMessage(Lang.NOT_VALID_AMOUNT.get());
                    return;
                }
                String item = args[2].toLowerCase();
                if (plugin.getConfig().getString("custom-items." + item) == null) {
                    sender.sendMessage(Lang.NOT_VALID_ITEM.get());
                    return;
                }
                try {
                    CureItem cureType = CureItem.valueOf(item.toUpperCase());
                    selectedPlayer.getInventory().addItem(Item.getItem(cureType, amount));
                    sender.sendMessage(Lang.GAVE.get() + " " + ChatColor.DARK_GREEN + selectedPlayer.getName() + " " + RED + amount + " " + RED + item + RDUtils.s(item, amount) + ".");
                } catch (NullPointerException exception) {
                    sender.sendMessage(RED + "The selected item is corrupted due to incorrect type, name or lore. Also, Make sure you're using the correct material type for the current server version.");
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Lang.NOT_VALID_AMOUNT.get());
            }
        } else {
            sendHelp(sender);
        }
    }
}