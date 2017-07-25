package lesson170713;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithLock<T> {
    private final Queue<T> tasks = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private AtomicInteger counter = new AtomicInteger();

    public void put(T item) {
        lock.lock();
        try {
            tasks.add(item);
            counter.incrementAndGet();
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (tasks.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter.decrementAndGet();
            return tasks.poll();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return counter.get();
    }
}
