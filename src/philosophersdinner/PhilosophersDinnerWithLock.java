package philosophersdinner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosophersDinnerWithLock {
    public static void main(String[] args) {

        final String[] names = {"Aristotle", "Immanuel Kant", "Karl Marks", "Friedrich Nietzsche", "Sigmund Freud"};
        PhilosopherWithLock[] phils = new PhilosopherWithLock[5];
        Lock[] forks = new ReentrantLock[5];

        for (int i = 0; i < phils.length; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < phils.length; i++) {
            phils[i] = new PhilosopherWithLock(names[i],
                    forks[i], forks[(i + 1) % 5]);
        }

        for (final PhilosopherWithLock phil : phils) {
            new Thread(() -> {
                while (phil.dishesLeft() != 0) {
                    phil.think();
                    phil.eat();
                }
                System.out.format("%s finished eating!\n", phil.getName());
            }).start();
        }
    }
}
