package me.gleeming.command.node;
import java.lang.reflect.Parameter;

public class ArgumentNode {
	
	
	
    public ArgumentNode(String name, boolean concated, boolean required, Parameter parameter) {
		this.name = name;
		this.concated = concated;
		this.required = required;
		this.parameter = parameter;
	}

		// Argument information
    private final String name;
    private final boolean concated;
    private final boolean required;

    // Reflection information
    private final Parameter parameter;

		public String getName() {
			return name;
		}

		public boolean isConcated() {
			return concated;
		}

		public boolean isRequired() {
			return required;
		}

		public Parameter getParameter() {
			return parameter;
		}
    
    
}
