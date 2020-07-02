package review.locktest;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

// 多线程 原子性  实现 lock 和 unlock方法
public class MyLock implements Lock {
    // 标志： 谁拿到了锁
    // 保证这个东西的修改线程安全
    AtomicReference<Thread> owner = new AtomicReference<>();
    // 集合 -- 存储政治等待的线程
    public LinkedBlockingDeque<Thread> waiters = new LinkedBlockingDeque<>();

    @Override
    public void lock() {
        // cas
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            // 没有拿到锁，线程需要等待，其他线程释放锁   （wait  notify 这个需要在同步关键字里面使用 synchronize），因此使用 park 和 unpark方法
            waiters.add(Thread.currentThread());
            // 等待 ， 线程不继续执行 park  unpark
            LockSupport.park();// 让一个线程 等待
            waiters.remove(Thread.currentThread());//唤醒之后 ，就不用在等待队列里面了
        }
        //拿到锁之后执行接下来的代码
    }

    @Override
    public void unlock() {
        // 释放锁之后，告知其他线程，你们可以继续
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            Object[] objects = waiters.toArray();
            for (Object object : objects) { // 遍历 拿出来所有的等待线程
                Thread next = (Thread) object;
                // 通知线程继续执行
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
