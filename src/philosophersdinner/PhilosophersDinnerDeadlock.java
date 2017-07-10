package philosophersdinner;

public class PhilosophersDinnerDeadlock {
    public static void main(String[] args) {

        final String[] names = {"Aristotle", "Immanuel Kant", "Karl Marks", "Friedrich Nietzsche", "Sigmund Freud"};
        Philosopher[] phils = new Philosopher[5];
        Object[] forks = new Object[5];

        for (int i = 0; i < phils.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < phils.length; i++) {
            phils[i] = new Philosopher(names[i],
                    forks[i], forks[(i + 1) % 5]);
        }

        for (final Philosopher phil : phils) {
            new Thread(() -> {
                for (int i = 0; i < Philosopher.NUM_OF_DISHES; i++) {
                    phil.think();
                    phil.eat();
                }
                System.out.format("%s finished eating!\n", phil.getName());
            }).start();
        }
    }
}