package learn;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*
https://mp.weixin.qq.com/s/5CbjktmOIEMAPLmVgouHIg
*/
public class TokenDemo {

    //qps:每秒钟处理完请求的次数；tps:每秒钟处理完的事务次数
    //代表qps是10；
    RateLimiter rateLimiter = RateLimiter.create(10);

    public void doSomething() {
        if (rateLimiter.tryAcquire()) {
            //尝试获得令牌.为true则获取令牌成功
            System.out.println("正常处理");
        } else {
            System.out.println("处理失败");
        }

    }

    public static void main(String args[]) throws IOException {
        /*
         * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量，此值是线程将要等待的操作数（线程的数量）。
         * 当某个线程为了想要执行这些操作而等待时， 它要使用 await()方法。
         * 此方法让线程进入休眠直到操作完成。
         * 当某个操作结束，它使用countDown() 方法来减少CountDownLatch类的内部计数器，计数器的值就会减1。
         * 当计数器到达0时，它表示所有的线程已经完成了任务，这个类会唤醒全部使用await() 方法休眠的线程们恢复执行任务。
         *
         * */
        CountDownLatch latch = new CountDownLatch(1);
        Random random = new Random(10);
        TokenDemo tokenDemo = new TokenDemo();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    Thread.sleep(random.nextInt(1000));
                    tokenDemo.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        latch.countDown();
        System.in.read();
    }

}