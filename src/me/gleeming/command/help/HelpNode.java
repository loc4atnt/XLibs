package me.gleeming.command.help;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelpNode {
    private static final List<HelpNode> nodes = new ArrayList<>();

    private final Object parentClass;
    private final String[] names;
    private final Method method;
    public HelpNode(Object parentClass, String[] names, Method method) {
        this.parentClass = parentClass;
        this.names = names;
        this.method = method;

        nodes.add(this);
    }
		public static List<HelpNode> getNodes() {
			return nodes;
		}
		public Object getParentClass() {
			return parentClass;
		}
		public String[] getNames() {
			return names;
		}
		public Method getMethod() {
			return method;
		}
    
}
