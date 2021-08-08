package me.gleeming.command.duration;

public class Duration {
    private final String parsed;
    private final long time;
		public String getParsed() {
			return parsed;
		}
		public long getTime() {
			return time;
		}
		public Duration(String parsed, long time) {
			super();
			this.parsed = parsed;
			this.time = time;
		}
    
}
