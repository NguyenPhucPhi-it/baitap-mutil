package Bai_4;

public class ThreadlnterruptionDemo {
	 public static void main(String[] args) {
	        Thread loopingThread = new Thread(() -> {
	            try {
	                while (!Thread.currentThread().isInterrupted()) {
	                    System.out.println("Thread is running...");
	                    Thread.sleep(500);
	                }
	            } catch (InterruptedException e) {
	                System.out.println("Thread was interrupted!");
	            }
	        });

	        loopingThread.start();

	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }	        
	        loopingThread.interrupt();
	        System.out.println("Sent interrupt signal to thread.");
	    }
}

