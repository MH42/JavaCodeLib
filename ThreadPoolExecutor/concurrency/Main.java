package concurrency;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    
	private static final int CORE_POOL_SIZE = 500;
	private static final int MAXIMUM_POOL_SIZE = 5000;
	private static final long KEEP_ALIVE_TIME = 2000;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			// @see http://howtodoinjava.com/2012/10/20/how-to-use-blockingqueue-and-threadpoolexecutor-in-java/
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				executor.execute(r);
			}
		});
		
		Main m = new Main();
		final int max = 1000000;
		for(int i = 0; i < max; i++) {
			threadPoolExecutor.execute(m.new Exec());
		}
	}
	
	private class Exec implements Runnable {
        
		public Exec() {
		}
        
		@Override
		public void run() {
			// Do something!
		}
		
	}
    
}
