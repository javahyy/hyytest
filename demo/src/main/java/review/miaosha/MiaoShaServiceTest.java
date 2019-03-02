package review.miaosha;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MiaoShaServiceTest {

    long startTime = 0L;
    @Before
    public void start(){
        startTime = System.currentTimeMillis();
        System.out.println("开始测试：");
    }
    @After
    public void end(){
        System.out.println("结束测试时间，执行时长："+(System.currentTimeMillis()-startTime)+" 毫秒");
    }
//    @Autowired
//    MiaoshaService miaoshaService;
    MiaoshaService miaoshaService = new MiaoshaService();


    @Test
    public void testA(){
        miaoshaService.miaosha("bike",null);
    }

    // 循环创建N个线程，模拟用户请求
    @Test
    public void benchmark() throws InterruptedException{
        // 模拟的请求数量，比如200个(线程)用户
        final int threadNum = 200;
        // 倒计数器，用于模拟高并发（信号抢机制）
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        // 循环创建N个线程
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threads.length; i++) {
            String userId = "hyy:"+i;
            Thread thread = new Thread(()->{
                try {
                    // 等待 countDownLatch 值为0， 也就是等待其他线程就绪后（200个线程都创建好了一起去请求miaosha接口模拟并发），在运行后面的代码
                    countDownLatch.await();
                    // http 请求实际上就是多线程调用这个方法
                    miaoshaService.miaosha("bike",userId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i] = thread;
            thread.start();

            // 倒计时 减一
            countDownLatch.countDown();
        }

        // 因为服务器是一直启动的，不会停止，这里不如程序没有足够时间去执行，阻塞等待
        try {
            //等待
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
