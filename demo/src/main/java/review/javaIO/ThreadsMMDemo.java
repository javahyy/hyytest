package review.javaIO;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadsMMDemo {

    public static void main(String[] args) {
        CountDownLatch cd1 = new CountDownLatch(1);
        try{
            //休眠20秒
//            Thread.sleep(20000L);
            TimeUnit.SECONDS.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        for (int i = 0; i < 1000 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cd1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("i="+i);
        }
    }
}
