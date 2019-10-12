package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDemo {
   static List<Integer> randomList = new ArrayList<>();


   // 生成[1,100]的随机数，其中[1,80] 为80%概率
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            getRandom();
        }
        System.out.println("随机数长度："+randomList.size());
        long count = randomList.stream().filter(i -> i > 80).count();
        System.out.println(count);
    }

    private static void getRandom() {
        Random random = new Random();
        int j = random.nextInt(5);
        int randomValue = 0;
        // [1,80] 随机数
        if (j / 4 == 0) {
            randomValue = random.nextInt(80) + 1;
        } else {
            // [81,100] 随机数
            randomValue = random.nextInt(20) + 81;
        }
//        System.out.println(randomValue);
        randomList.add(randomValue);
    }

}
