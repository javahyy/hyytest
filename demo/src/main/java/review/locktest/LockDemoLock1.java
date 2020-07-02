package review.locktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LockDemoLock1 {
        volatile int i = 0;
//    AtomicInteger i = new AtomicInteger(0);

    private void add() {
//        i++;// i+1
// 解决： 1）加锁      2）AtomicInteger  3)自己写unsafe
    synchronized (this){
        i++;
    }
//        i.incrementAndGet();//对变量进行加1操作，并返回i
    }

    public static void main(String[] args) throws Exception {
        LockDemoLock1 ld = new LockDemoLock1();
        //期望值是20000
        for (int i = 0; i < 2; i++) {

            new Thread(() -> {// 多线程环境下
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
//        Thread.sleep(2000L);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("i=" + ld.i);
    }


}
