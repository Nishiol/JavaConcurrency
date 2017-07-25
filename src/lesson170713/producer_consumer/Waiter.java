package lesson170713.producer_consumer;

import lesson170713.BlockingQueueWithLock;
import utils.Utils;


class Waiter implements Runnable {

    private BlockingQueueWithLock<String> window;

    public Waiter(BlockingQueueWithLock<String> window) {
        this.window = window;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("start waiting");
            String dish = window.take();
            System.out.println("serving");
            Utils.pause(10000 + BlockingQueueWithLockExample.random.nextInt(3000));
        }
    }

}