package review.executorDemo;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/*
https://blog.csdn.net/qq_34337272/article/details/81252853
面试必备之深入理解自旋锁
 */
//自旋锁
public class SpinLock {

    private AtomicReference<Thread> cas = new AtomicReference<>();
    private int count;

    public void lock() {
        Thread current = Thread.currentThread();
        //利用cas
        while (!cas.compareAndSet(null, current)) {
            // DO nothing
        }
    }

    public void unlock() {
        Thread cur = Thread.currentThread();
        if (cur == cas.get()) {// 如果当前线程已经获取到了锁，线程数增加一，然后返回
            if (count > 0) {
                count++;
                return;
            } else {// 如果count==0，可以将锁释放，这样就能保证获取锁的次数与释放锁的次数是一致的了。
                cas.compareAndSet(cur, null);
            }
        }
    }

}
