package counter;

public class Counter {
private static int counter=0;

public static void main(String[] args) throws InstantiationException {
	Runnable task=()->{
		for(int i=0;i<1000;i++) {
			synchronized (Counter.class) {
				counter++;
			}
		}
	};
	Thread t1=new Thread(task);
	Thread t2=new Thread(task);
	t1.start();
	t2.start();
	try {
		t1.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		t2.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Gia tri cuoi cung cua counter: "+counter);
}

}
