package review.locktest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LockDemoCas {
    volatile int value = 0;
    static Unsafe unsafe;
    static long valueOffset;

    static {
        try {
            // 通过反射获取字段
            Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
            // 暴力破解
            declaredField.setAccessible(true);
            // 因为Unsafe 是静态的，没有对象，所以传递null就可以了
            unsafe = (Unsafe) declaredField.get(null);

            // 修改value,不是通过普通的方式去修改
            // 属性的偏移量 -- 用它定位内存中 一个对象 具体属性的内存位置，如 LockDemoCas类中value在内存中位置
            valueOffset = unsafe.objectFieldOffset(LockDemoCas.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void add() {
//        i++;// i+1
        //通过 CAS 去操作
        int current;
        do {
            current = unsafe.getIntVolatile(this, valueOffset);//获取value 在内存中的值
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));
    }

    public static void main(String[] args) throws Exception {
        LockDemoCas ld = new LockDemoCas();
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
        System.out.println("i=" + ld.value);
    }


}
