package app;

import java.util.concurrent.CountDownLatch;

public class Test {
	static int index=2002220;
	static final CountDownLatch latch = new CountDownLatch(index);
    public static void main(String[] args) {   
        for (int i = 0; i < index; i++) {
			new  Thread(new B()).start();
			latch.countDown();
		}

  
    }
    static class B implements Runnable{
    	
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		try {
				latch.await();
				System.out.println("222");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
}