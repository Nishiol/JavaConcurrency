package philosophersdinner.servantmod;

import utils.Utils;

public class Philosopher {

    public static int NUM_OF_DISHES = 3;

    private final String NAME;
    private final Fork LEFT_FORK;
    private final Fork RIGHT_FORK;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
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
                RIGHT_FORK.setBusy(false);
            }
            LEFT_FORK.setBusy(false);
        }
    }

    public Fork getLeftFork() {
        return LEFT_FORK;
    }

    public Fork getRightFork() {
        return RIGHT_FORK;
    }

    public String getName() {
        return NAME;
    }
}
