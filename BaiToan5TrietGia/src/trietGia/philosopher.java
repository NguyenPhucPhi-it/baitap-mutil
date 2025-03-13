package trietGia;

import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
    private int id;
    private ReentrantLock leftChopstick;
    private ReentrantLock rightChopstick;

    public Philosopher(int id, ReentrantLock left, ReentrantLock right) {
        this.id = id;
        this.leftChopstick = left;
        this.rightChopstick = right;
    }

    private void think() throws InterruptedException {
        System.out.println("Triết gia " + id + " đang suy nghĩ...");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Triết gia " + id + " đang ăn...");
        Thread.sleep((long) (Math.random() * 1000));
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                if (id == 4) {
                    rightChopstick.lock();
                    leftChopstick.lock();
                } else {
                    leftChopstick.lock();
                    rightChopstick.lock();
                }

                eat();

                leftChopstick.unlock();
                rightChopstick.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



