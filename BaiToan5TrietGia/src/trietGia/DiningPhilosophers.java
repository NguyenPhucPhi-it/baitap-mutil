package trietGia;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    public static void main(String[] args) {
        ReentrantLock[] chopsticks = new ReentrantLock[5];
        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new ReentrantLock();
        }

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % 5]);
            philosophers[i].start();
        }
    }
}