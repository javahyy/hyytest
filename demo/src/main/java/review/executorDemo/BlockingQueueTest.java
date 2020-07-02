package review.executorDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
需求：在多线程操作下，一个数组中最多只能存入 3 个元素。多放入不可以存入数组，或等待某线程对数组
个元素取走才能放入，要求使用 java 的多线程来实现。（面
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        final BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {// lamda表达式   等他匿名内部类缩写
                while (true) {
                    try {
                        Thread.sleep((long) Math.random() * 1000);
                        System.out.println(Thread.currentThread().getName() + "准备放数据!");
                        blockingQueue.put(1);
                        System.out.println(Thread.currentThread().getName() + "已经放了数据，" + "队列目前有" + blockingQueue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    //将此处的睡眠时间分别改为 100 和 1000，观察运行结果Thread.sleep(100);
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "准备取数据!");
                    System.err.println(blockingQueue.take());
                    System.out.println(Thread.currentThread().getName() + "已经取走数据，" + "队列目前有" + blockingQueue.size() + "个数据");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}