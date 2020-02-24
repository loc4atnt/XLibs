package loc4atnt.xlibs.thread;

public class ThreadUtil {
	
	public static void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//
		}
	}

}
