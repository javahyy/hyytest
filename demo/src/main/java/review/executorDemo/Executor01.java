package review.executorDemo;

import java.util.concurrent.*;

public class Executor01 {
    private static boolean isScheduled;
    public static void main(String[] args) throws Exception {
        System.out.println("111");
        //固定大小连接池
//        testFixedThreadPool();//开线程去执行，主线程继续执行，不会受到影响

        //单任务连接池
//        testSingleThreadExecutor();

//        TimeUnit.MINUTES.sleep(100);//代替 Thread.sleep(毫秒)
// TimeUnit还提供了便捷方法用于把时间转换成不同单位，例如，如果你想把秒转换成毫秒，你可以使用下面代码：
        TimeUnit.SECONDS.toMillis(44);

        //创建一个使用单个worker线程的Executor，以无界队列方式来运行该线程
//        testCachedThreadPool();

        //延迟连接池:创建一个线程池，它可安排在给定延迟后运行命令或者定期地执
        isScheduled = true;
        testScheduledThreadPool();


        System.out.println("222");

    }

    private static void testScheduledThreadPool() throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService pool = Executors.newScheduledThreadPool(2);
        doThread(pool);
    }
    private static void testCachedThreadPool() throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService pool = Executors.newCachedThreadPool();
        doThread(pool);
    }

    private static void testSingleThreadExecutor() throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        doThread(pool);
    }
    private static void testFixedThreadPool() throws InterruptedException, java.util.concurrent.ExecutionException {
        //创建一个可重用固定线程数的线,如2个线程
        ExecutorService pool = Executors.newFixedThreadPool(2);
        doThread(pool);
    }

    //创建实现了 Runnable 接口对象，Thread 对象当然也实现了 Runnable
    private static void doThread(ExecutorService pool) {
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        Thread t6 = new MyThread();
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        //使用定时执行风格的
        if(isScheduled){
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)pool;
           //t4 和 t5 在 10 毫秒后执行
            scheduledExecutorService.schedule(t4, 10, TimeUnit.MILLISECONDS);
            scheduledExecutorService.schedule(t5,10,TimeUnit.MILLISECONDS);
        }

        // submit 使用submit 可以得到执行线程的返回值
        Future<String> result = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(4000);
                System.out.println("返回状态");
                return "OK";
            }
        });
//        System.out.println("状态=="+result.get());//这样会导致一直执行完毕
        //关闭线程池
        pool.shutdown();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
        } catch (Exception e){

        }
        System.out.println(Thread.currentThread().getName()+"正在执行...");
    }
}