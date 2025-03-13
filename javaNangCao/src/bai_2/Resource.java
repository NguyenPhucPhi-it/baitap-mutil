package bai_2;

public class Resource {
    public static void main(String[] args) {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1 locked Resource 1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                
                synchronized (resource2) {
                    System.out.println("Thread 1 locked Resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2 locked Resource 2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource1) {
                    System.out.println("Thread 2 locked Resource 1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}