package review.mapsync;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MapLock {

    private final Map<String,Long> wordCounts = new ConcurrentHashMap<>();
    public static void main(String[] args) {

//        countWordMethod();

        MapLock mapLock = new MapLock();



        new Thread(()->{
            System.out.println( mapLock.increase03("aa"));
            System.out.println( mapLock.increase03("aa"));
        }).start();
        new Thread(()->{
            System.out.println( mapLock.increase03("aa"));
            System.out.println( mapLock.increase03("aa"));
        }).start();

    }

    /** 存在bug
     * 如果多个线程并发调用这个increase()方法，increase()的实现就是错误的，
     * 因为多个线程用相同的word调用时，很可能会覆盖相互的结果，造成记录的次数比实际出现的次数少。
     */
    public long increase01(String word){
        Long oldValue = wordCounts.get(word);
        Long newValue = (oldValue==null)?1L:oldValue+1;
        wordCounts.put(word,newValue);
        return newValue;
    }

    /** 解决bug
     * 除了用锁解决这个问题，另外一个选择是使用ConcurrentMap接口定义的方法：
     */
    public long increase02(String word){
        Long oldValue, newValue;
        while (true){//循环的目的：防止多个线程进入到同步步骤，其中一个持有锁后break，其他没有锁的无法执行else
            oldValue = wordCounts.get(word);
            if (oldValue == null){
                //此处可能有多个线程到达
                System.out.println("线程=["+Thread.currentThread().getName()+"]已经到达1");
                // Add the word firstly, initial the value as 1
                newValue = 1L;
                if(wordCounts.putIfAbsent(word,newValue)==null){
                    System.out.println("线程=["+Thread.currentThread().getName()+"]已经到达2");
                    break;
                }
            }else{
                System.out.println("线程=["+Thread.currentThread().getName()+"]已经到达3");
                newValue = oldValue +1;
                if(wordCounts.replace(word,oldValue,newValue)){
                    System.out.println("线程=["+Thread.currentThread().getName()+"]已经到达4");
                    break;
                }
            }
        }
        return newValue;
    }

    /** 简化代码
     * 代码有点复杂，主要因为ConcurrentMap中不能保存value为null的值，所以得同时处理word不存在和已存在两种情况。
     * 上面的实现每次调用都会涉及Long对象的拆箱和装箱操作，很明显，更好的实现方式是采用AtomicLong，下面是采用AtomicLong后的代码：
     */
    private final Map<String,AtomicLong> wordCounts2 = new ConcurrentHashMap<>();
    public long increase03(String word){
        AtomicLong number = wordCounts2.get(word);
        if (number == null){
            AtomicLong newNumber = new AtomicLong(0);
            number = wordCounts2.putIfAbsent(word, newNumber);
            if (number == null) {
                number = newNumber;
            }
        }
        return number.incrementAndGet();
    }














}
