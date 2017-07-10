package philosophersdinner.servantmod;

public class Servant {

    private final static String[] names =
            {"Aristotle", "Immanuel Kant", "Karl Marks", "Friedrich Nietzsche", "Sigmund Freud"};
    private final static Philosopher[] phils = new Philosopher[5];
    private final static Fork[] forks = new Fork[5];

    static {
        for (int i = 0; i < phils.length; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < phils.length; i++) {
            phils[i] = new Philosopher(names[i],
                    forks[i], forks[(i + 1) % 5]);
        }
    }

    synchronized private static boolean getPermissionFor(Philosopher phil) {
        if (!phil.getLeftFork().isBusy() && !phil.getRightFork().isBusy()) {
            phil.getLeftFork().setBusy(true);
            phil.getRightFork().setBusy(true);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        for (final Philosopher phil : phils) {
            new Thread(() -> {
                int i = 0;
                while (i < Philosopher.NUM_OF_DISHES) {
                    phil.think();
                    if (getPermissionFor(phil)) {
                        phil.eat();
                        i++;
                    }
                }
                System.out.format("%s finished eating!\n", phil.getName());
            }).start();
        }
    }

}
