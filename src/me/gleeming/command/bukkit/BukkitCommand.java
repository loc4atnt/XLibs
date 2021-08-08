package me.gleeming.command.bukkit;

import me.gleeming.command.CommandHandler;
import me.gleeming.command.help.HelpNode;
import me.gleeming.command.node.CommandNode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BukkitCommand extends Command {
    private static final HashMap<String, BukkitCommand> commands = new HashMap<>();
    public static HashMap<String, BukkitCommand> getCommands(){return commands;}

    public BukkitCommand(String root) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        super(root);
        commands.put(root.toLowerCase(), this);

        // Registers the command with bukkit
        Field commandMap = CommandHandler.getPlugin().getServer().getClass().getDeclaredField("commandMap");
        commandMap.setAccessible(true);
        ((org.bukkit.command.CommandMap) commandMap.get(CommandHandler.getPlugin().getServer())).register(CommandHandler.getPlugin().getName(), this);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        List<CommandNode> couldExecute = new ArrayList<>();
        CommandNode.getNodes().forEach(node -> {
            if(node.couldExecute(label.toLowerCase(), args)) couldExecute.add(node);
        });

        if(couldExecute.size() == 0) {
            HelpNode helpNode = null;
            int lastSize = 0;
            for(HelpNode node : HelpNode.getNodes()) {
                for(String name : node.getNames()) {
                    if(label.toLowerCase().equals(name)) {
                        helpNode = node;
                        lastSize = 100;
                        break;
                    }

                    String[] split = name.split(" ");
                    for(String s : split) {
                        if(s.contains(label.toLowerCase())) if(lastSize < split.length) { helpNode = node; lastSize = split.length; }
                    }
                }
            }

            if(helpNode != null) {
                try {
									helpNode.getMethod().invoke(helpNode.getParentClass(), sender);
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
									e.printStackTrace();
									Bukkit.getConsoleSender().sendMessage("BukkitCommand.java::61");
								}
                return false;
            }

            CommandNode highestProbabilityNode = null;
            int highestProbability = 0;
            for(CommandNode commandNode : CommandNode.getNodes()) {
                int probability = commandNode.getMatchProbability(label, args);
                if(probability > highestProbability) {
                    highestProbability = probability;
                    highestProbabilityNode = commandNode;
                }
            }

            if(highestProbabilityNode == null) {
                sender.sendMessage(ChatColor.RED + "You have entered an invalid set of command arguments. We were unable to find a usage message to display to you.");
                return false;
            }

            highestProbabilityNode.sendUsageMessage(sender);
            return false;
        }

        if(couldExecute.size() == 1) {
            try {
							couldExecute.get(0).execute(sender, args);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Bukkit.getConsoleSender().sendMessage("BukkitCommand::91");
						}
            return false;
        }

        List<CommandNode> notConcat = new ArrayList<>();
        for(CommandNode node : couldExecute)
            if(node.getParameters().size() < 1 || !node.getParameters().get(node.getParameters().size() - 1).isConcated())
                notConcat.add(node);

        if (notConcat.size() == 0) {
            // Cleaned code from https://github.com/GleemingKnight/spigot-command-api/pull/3
            // which addressed fixing mistake in https://github.com/GleemingKnight/spigot-command-api/issues/1
            couldExecute.stream()
                    .filter(node -> node.getNames().contains(label.toLowerCase()))
                    .limit(1)
                    .forEach(node -> {
											try {
												node.execute(sender, args);
											} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												Bukkit.getConsoleSender().sendMessage("BukkitCommand::113");
											}
										});

            return false;
        }

        for(CommandNode node : notConcat) {
            for(String name : node.getNames()) {
                if(name.split(" ")[0].equalsIgnoreCase(label)) {
                    try {
											node.execute(sender, args);
										} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											Bukkit.getConsoleSender().sendMessage("BukkitCommand::128");
										}
                    return false;
                }
            }
        }

        try {
					notConcat.get(0).execute(sender, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Bukkit.getConsoleSender().sendMessage("BukkitCommand::140");
				}
        return false;
    }
}
