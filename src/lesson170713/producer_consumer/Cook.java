package lesson170713.producer_consumer;

import lesson170713.BlockingQueueWithLock;
import utils.Utils;

class Cook implements Runnable {

    private BlockingQueueWithLock<String> window;

    public Cook(BlockingQueueWithLock<String> window) {
        this.window = window;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("start cooking");
            Utils.pause(1000 + BlockingQueueWithLockExample.random.nextInt(3000));
            System.out.println("dish is ready, waiting for waiter");
            window.put("dish");
        }
    }

}