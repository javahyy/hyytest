package review.locktest;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class LockDemo {
    volatile int i = 0;
    private void add() {
        i++;// i+1
    }

    public static void main(String[] args) throws Exception {
        LockDemo ld = new LockDemo();
        //期望值是20000
        for (int i = 0; i < 2 ; i++) {

            new Thread(()->{// 多线程环境下
                for (int j = 0; j <10000 ; j++) {
                    ld.add();
                }
            }).start();
        }
//        Thread.sleep(2000L);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("i="+ld.i);
    }



}
