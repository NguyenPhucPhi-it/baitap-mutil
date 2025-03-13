package Bai_3;

class HighPriorityThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("Chủ đề có mức độ ưu tiên cao đang chạy...");
        }
    }
}

class LowPriorityThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("Chủ đề có mức độ ưu tiên thấp đang chạy...");
        }
    }
}

public class ThreadPriorityExample {
	public static void main(String[] args) {
		HighPriorityThread high = new HighPriorityThread();
		LowPriorityThread low = new LowPriorityThread();

		high.setPriority(Thread.MAX_PRIORITY);
		low.setPriority(Thread.MIN_PRIORITY);

		high.start();
		low.start();
	}
}