package Bai_5;

public class emoryVisibilityExample {
	    private static boolean flag = false;

	    public static void main(String[] args) {
	        Thread readerThread = new Thread(() -> {
	            System.out.println("Reader Thread started...");
	            while (!flag) {
	              
	            }
	            System.out.println("Reader Thread detected flag change!");
	        });
	        Thread writerThread = new Thread(() -> {
	            try {
	                Thread.sleep(2000); 
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            flag = true;
	            System.out.println("Writer Thread changed flag to true.");
	        });

	        readerThread.start();
	        writerThread.start();
	    }
	}


