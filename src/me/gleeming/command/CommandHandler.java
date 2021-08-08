package me.gleeming.command;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.Plugin;
import org.reflections.util.ClasspathHelper;
import org.reflections.vfs.Vfs;

import com.google.common.collect.ImmutableSet;

import me.gleeming.command.help.Help;
import me.gleeming.command.help.HelpNode;
import me.gleeming.command.node.CommandNode;

public class CommandHandler {
    private static Plugin plugin;
    public static void setPlugin(Plugin _p) {
    	plugin = _p;
    }
    public static Plugin getPlugin() {
    	return plugin;
    }

    /**
     * Registers commands based off a file path
     * @param path Path
     */
    public static void registerCommands(String path, Plugin plugin) {
        CommandHandler.setPlugin(plugin);

        Set<Class<?>> classes = new HashSet<>();
        Collection<URL> urls = ClasspathHelper.forClassLoader(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), plugin.getClass().getClassLoader());

        if(urls.size() > 0) {
            urls.forEach(url -> Vfs.fromURL(url).getFiles().forEach(file -> {
                String name = file.getRelativePath().replace("/", ".").replace(".class", "");
                try { if (name.startsWith(path)) classes.add(Class.forName(name)); } catch(ClassNotFoundException ex) { ex.printStackTrace(); }
            }));
        }

        ImmutableSet.copyOf(classes).forEach(clazz -> {
            try { registerCommands(clazz.newInstance()); } catch(Exception ex) { ex.printStackTrace(); }
        });
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static void registerCommands(Class<?> commandClass, Plugin plugin) throws InstantiationException, IllegalAccessException {
        CommandHandler.setPlugin(plugin);
        registerCommands(commandClass.newInstance());
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    private static void registerCommands(Object commandClass) {
        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Command command = method.getAnnotation(Command.class);
            if(command == null) return;

            new CommandNode(commandClass, method, command);
        });

        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Help help = method.getAnnotation(Help.class);
            if(help == null) return;

            new HelpNode(commandClass, help.names(), method);
        });
    }
}
