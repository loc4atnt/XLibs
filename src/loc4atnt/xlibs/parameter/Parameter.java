package loc4atnt.xlibs.parameter;

public class Parameter {
	
	private String key;
	private Object val;
	
	public Parameter(String key, Object defaultVal) {
		this.key = key;
		this.val = defaultVal;
	}

	public String getKey() {
		return this.key;
	}
	
	public Object getObject() {
		return this.val;
	}
}
