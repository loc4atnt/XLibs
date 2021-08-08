package me.gleeming.command.paramter;

import me.gleeming.command.duration.Duration;
import me.gleeming.command.node.ArgumentNode;
import me.gleeming.command.paramter.impl.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ParamProcessor {
    private static final HashMap<Class<?>, Processor> processors = new HashMap<>();
    private static boolean loaded = false;

    private final ArgumentNode node;
    private final String supplied;
    private final CommandSender sender;
    
    
    

    public static boolean isLoaded() {
			return loaded;
		}

		public static void setLoaded(boolean loaded) {
			ParamProcessor.loaded = loaded;
		}

		public static HashMap<Class<?>, Processor> getProcessors() {
			return processors;
		}

		public ArgumentNode getNode() {
			return node;
		}

		public String getSupplied() {
			return supplied;
		}

		public CommandSender getSender() {
			return sender;
		}

		public ParamProcessor(ArgumentNode node, String supplied, CommandSender sender) {
			super();
			this.node = node;
			this.supplied = supplied;
			this.sender = sender;
		}

		/**
     * Processes the param into an object
     * @return Processed Object
     */
    public Object get() {
        if(!loaded) loadProcessors();

        Processor processor = processors.get(node.getParameter().getType());
        if(processor == null) return supplied;

        return processor.process(sender, supplied);
    }

    /**
     * Loads the processors
     */
    public static void loadProcessors() {
        loaded = true;

        processors.put(Integer.class, new IntegerProcessor());
        processors.put(Long.class, new LongProcessor());
        processors.put(Double.class, new DoubleProcessor());
        processors.put(Player.class, new PlayerProcessor());
        processors.put(OfflinePlayer.class, new OfflinePlayerProcessor());
        processors.put(World.class, new WorldProcessor());
        processors.put(Boolean.class, new BooleanProcessor());
        processors.put(Duration.class, new DurationProcessor());
    }
}
