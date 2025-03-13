package bai_2;

public class DeadlockSolution {
	public static void main(String[] args) {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Runnable task = () -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " locked Resource 1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " locked Resource 2");
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}
