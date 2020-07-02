package review;

public class ThreadDemo1 {
    //子线程运行执行 10 次后，主线程再运行 5 次。这样交替执行三遍
    public static void main(String[] args) {
        final Business business = new Business();
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    business.subMethod();
                }
            }
        }).start();
        //主线程
        for (int i = 0; i < 3; i++) {
            business.mainMethod();
        }
    }
}

class Business {
    private boolean subFlag = true; //用于保证第一次先运行子线程，后续用于线程切换
    public synchronized void mainMethod() {
        while (subFlag) {
            try {
                wait();//释放，等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("1111");
        for (int i = 0; i < 5; i++) {
            System.err.println(Thread.currentThread().getName() + " : main thread running loop count --" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        subFlag = true;
        notify();//唤醒
    }

    public synchronized void subMethod() {
        System.out.println("222");
        while (!subFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.err.println(Thread.currentThread().getName() + " : sub thread running loop count -- " + i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subFlag = false;
        notify();
    }
}
