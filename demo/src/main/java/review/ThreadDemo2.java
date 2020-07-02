package review;

/*用 Java 的 notify 机制实现
启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        PrintDemo printDemo = new PrintDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                printDemo.printOld();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                printDemo.printEven();
            }
        }).start();
    }
}

class PrintDemo {
    private boolean flag = false;
    public PrintDemo() {
    }
    public PrintDemo(boolean flag) {
        this.flag = flag;
    }
    public synchronized void printOld() {
        for (int i = 1; i < 100; i++) {
            while (flag) {
                try {
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if (i % 2 != 0) {
                System.out.print(i + "\t");
            }else{
                continue;
            }
            flag = true;
            notify();
        }
    }

    public synchronized void printEven() {
        for (int i = 1; i < 100; i++) {
            while (!flag) {
                try {
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if (i % 2 == 0) {
                System.out.print(i + "\t");
            }else{
                continue;
            }
            flag = false;
            notify();
        }
    }
}


