package philosophersdinner;

import utils.Utils;

public class Philosopher {

    public static int NUM_OF_DISHES = 3;

    private final String NAME;
    private final Object LEFT_FORK;
    private final Object RIGHT_FORK;

    public Philosopher(String name, Object leftFork, Object rightFork) {
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
        synchronized (LEFT_FORK) {
            System.out.format("%s took the left fork!\n", NAME);
            Utils.pause(1000);
            synchronized (RIGHT_FORK) {
                System.out.format("%s took the right fork!\n", NAME);
                System.out.format("%s is eating...\n", NAME);
                Utils.pause(1000);
            }
        }
    }

    public String getName() {
        return NAME;
    }
}
