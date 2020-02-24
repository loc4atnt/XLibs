package loc4atnt.xlibs.parameter;

import java.util.HashMap;

import loc4atnt.xlibs.config.SimpleConfig;

public class ParameterManager {

	private SimpleConfig cfg;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public ParameterManager(SimpleConfig cfg, Parameter... param) {
		this.cfg = cfg;
		getParamter(param);
	}

	private void getParamter(Parameter... param) {
		for(Parameter para:param) {
			String key = para.getKey();
			Object obj;
			if(cfg.contains(key)) {
				obj = cfg.get(key);
			}else {
				obj = para.getObject();
				cfg.set(key, obj);
			}
			map.put(key, obj);
		}
		cfg.saveConfig();
	}
	
	public void reload() {
		cfg.reloadConfig();
		getParamter();
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public int getInt(String key) {
		Object obj = get(key);
		int val = (obj!=null)?((int) obj):-1;
		return val;
	}
	
	public String getString(String key) {
		Object obj = get(key);
		return (String) obj;
	}
	
	public void set(String key, Object obj) {
		map.put(key, obj);
		cfg.set(key, obj);
		cfg.saveConfig();
	}
}
