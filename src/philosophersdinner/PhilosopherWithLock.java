package philosophersdinner;

import utils.Utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class PhilosopherWithLock {

    private AtomicInteger dishesLeft = new AtomicInteger(3);

    private final String NAME;
    private final Lock LEFT_FORK;
    private final Lock RIGHT_FORK;

    public PhilosopherWithLock(String name, Lock leftFork, Lock rightFork) {
        NAME = name;
        LEFT_FORK = leftFork;
        RIGHT_FORK = rightFork;
    }

    public void think() {
        System.out.format("%s is thinking...\n", NAME);
        Utils.pause(1000);
    }

    public void eat() {
        Utils.pause(1000);

        boolean canTake = false;

        try {
            canTake = LEFT_FORK.tryLock(1000, TimeUnit.MILLISECONDS);

            if (!canTake) {
                return;
            }

            try {
                System.out.format("%s took the left fork!\n", NAME);
                Utils.pause(1000);

                canTake = RIGHT_FORK.tryLock(1000, TimeUnit.MILLISECONDS);

                if (!canTake) {
                    return;
                }

                try {
                    System.out.format("%s took the right fork!\n", NAME);
                    System.out.format("%s is eating...\n", NAME);
                    dishesLeft.decrementAndGet();
                    Utils.pause(1000);
                } finally {
                    RIGHT_FORK.unlock();
                }
            } finally {
                LEFT_FORK.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int dishesLeft() {
        return dishesLeft.get();
    }

    public String getName() {
        return NAME;
    }
}
