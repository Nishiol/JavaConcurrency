package lesson170713;

import utils.Utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WorkerThreadWithInterruptShutdown {

    public static class Task implements Runnable {

        private final int id;
        private final Random random = new Random();

        public Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int millis = 1000 + random.nextInt(1000);
            long dt = System.currentTimeMillis();
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                dt = System.currentTimeMillis() - dt;
                try {
                    Thread.sleep(millis - dt);
                } catch (InterruptedException ignored) {}
                Thread.currentThread().interrupt();
            }
            System.out.println("Done task " + id);
        }
    }

    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Thread thread;
    private boolean mayAcceptTasks = true;

    public WorkerThreadWithInterruptShutdown() {
        synchronized (tasks) {
            thread = new Thread(this::process);
            thread.start();
        }
    }

    public void shutdown() {
        thread.interrupt();
    }

    private void process() {
        OUTER:
        while (!tasks.isEmpty() || mayAcceptTasks) {
            Runnable task = null;
            synchronized (tasks) {
                while (tasks.isEmpty() && mayAcceptTasks) {
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Shutdown!");
                        mayAcceptTasks = false;
                        break OUTER;
                    }
                }
                task = tasks.poll();
            }
            task.run();
        }
    }

    public boolean submit(Runnable task) {
        synchronized (tasks) {
            if (!mayAcceptTasks) {
                return false;
            }
            tasks.add(task);
            tasks.notify();
        }
        return true;
    }

    public static void main(String[] args) {

        final WorkerThreadWithInterruptShutdown worker = new WorkerThreadWithInterruptShutdown();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                worker.submit(new Task(i));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 5; i < 10; i++) {
                worker.submit(new Task(i));
            }
        });

        thread1.start();
        thread2.start();

        Utils.pause(5000);

        worker.shutdown();

    }
}
