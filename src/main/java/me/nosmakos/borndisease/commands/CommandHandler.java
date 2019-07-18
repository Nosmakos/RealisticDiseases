package me.nosmakos.borndisease.commands;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.commands.executors.CommandItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor {

    private List<AbstractCommand> commands = new ArrayList<>();

    public CommandHandler(BornDisease plugin) {
        AbstractCommand[] bases = {
               new CommandItems(plugin),
        };

        Collections.addAll(commands, bases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length >= 1) {
            String command = args[0];
            String[] newArgs = new String[args.length-1];

            System.arraycopy(args, 1, newArgs, 0, args.length - 1);
            for (AbstractCommand base : commands) {
                if (base.getCommand().equalsIgnoreCase(command)) {
                    base.run(sender, newArgs);
                    return true;
                }
            }

            commands.get(0).run(sender, null);
        } else {
            commands.get(0).run(sender, null);
        }
        return true;
    }
}