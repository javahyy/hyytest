package review.sort;

import scala.util.Random;
import scala.util.parsing.combinator.testing.Str;

/**
 * 排序例子
 * https://www.cnblogs.com/guoyaohua/p/8600214.html  这个全是java写的
 * https://www.cnblogs.com/onepixel/articles/7674659.html 这个是js写的
 * https://www.cnblogs.com/0201zcr/p/4764427.html 这个思路清晰
 */
public class SortDemo {

    public static void main(String[] args) {
//        int[] arr = {3, 44, 56, 7, 22, 47, 4, 19, 21};
        int[] arr = randomAry(10);
        /** 冒泡排序 */
        bubbleSort(arr);

        /** 选择排序 */
        selectSort(randomAry(10));
        /** 插入排序 */
        insertionSort(randomAry(10));
        /** 希尔排序 */
        shellSort(arr);
        /** 归并排序(最快) */
        mergeSortTest();
        /** 快速排序 */
        quickSortTest();

        String str = "test";
        test(str);
        System.out.println("值并没有改变  " + str);

        String a = exceptionDemo();
        System.out.println("exceptionDemo 返回："+a);
    }

    public static String exceptionDemo() {
        try {
            System.out.println("1111");
            int a = 5;
            a = a / 0;
            return "1111";
        } catch (Exception e) {
            System.out.println("2222");
            return "2222";
        } finally {
            System.out.println("3333");
            return "3333";
        }
    }

    public static void test(String str) {
        str = "123aaa";
    }

    /**
     * 冒泡排序:找到最大的放到最后面
     * 1)比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 2)对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 3)针对所有的元素重复以上的步骤，除了最后一个；
     * 4)重复步骤1~3，直到排序完成。
     */
    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        printAry("冒泡排序", arr);
    }

    /**
     * 选择排序：有序的（默认没有） /  无序的  从无序的里面取一个最小的值与有序的进比较，放到合适的位置是，如此循环
     * n个记录的直接选择排序可经过n-1趟直接选择排序得到有序结果。具体算法描述如下：
     * 初始状态：无序区为R[1..n]，有序区为空；
     * 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
     * n-1趟结束，数组有序化了。
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        int len = arr.length;
        int minIndex, temp;
        for (int i = 0; i < len - 1; i++) {
            minIndex = i;
            //从无序组里面选取一个最小的进行比较后，交换位置
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {// 寻找最小的数与第一个进行比较
                    minIndex = j;//将最小数的索引保存
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        printAry("选择排序", arr);
    }

    /**
     * 插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     * 3.1 算法描述
     * 一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
     * <p>
     * 从第一个元素开始，该元素可以认为已经被排序；
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 将新元素插入到该位置后；
     * 重复步骤2~5。
     */
    public static void insertionSort(int[] arr) {
        int len = arr.length;
        int preIndex, current;
        for (int i = 1; i < len - 1; i++) {
            preIndex = i - 1;
            current = arr[i];
            //默认第一个元素有序， 从无序元素中拿出一个，插入到有序元素中（i--进行比较）
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        printAry("插入排序", arr);
    }

    /**
     * 希尔排序
     * 1959年Shell发明，第一个突破O(n2)的排序算法，是简单插入排序的改进版。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。
     * 4.1 算法描述
     * 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：
     * <p>
     * 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
     * 按增量序列个数k，对序列进行k 趟排序；
     * 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     */
    public static void shellSort(int[] arr) {
        int len = arr.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > temp) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        printAry("希尔排序", arr);
    }

    /**
     * 5、归并排序（Merge Sort）：分而治之  如分成两段分别排序，在合并
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。将已有序的子序列合并，
     * 得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
     * <p>
     * 5.1 算法描述
     * 把长度为n的输入序列分成两个长度为n/2的子序列；
     * 对这两个子序列分别采用归并排序；
     * 将两个排序好的子序列合并成一个最终的排序序列。
     */
    public static void mergeSortTest() {
        int[] test = {9, 2, 6, 3, 5, 7, 10, 11, 12};
        mergeSqort(test, 0, test.length - 1);
        printAry("归并排序：", test);
    }

    private static void mergeSqort(int[] arr, int left, int right) {
        int mid = (left + right) / 2;//每次递归，动态生成，不要把条件写死了
        if (left < right) {
            mergeSqort(arr, left, mid);//左边归并排序，使得左子序列有序
            mergeSqort(arr, mid + 1, right);//右边归并排序，使得右子序列有序
            merge(arr, left, mid, right);//合并两个子序列
        }
    }

    // 归并排序——将两段排序好的数组结合成一个排序数组
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];//ps：也可以从开始就申请一个与原数组大小相同的数组，因为重复new数组会频繁申请内存
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        int k = 0;//临时数组指针
        // 把较小的数先移到新数组中
        while (i <= mid && j <= right) {
            //左边和有右边进行比较，把小的放到新数组中
            if (arr[i] < arr[j]) {
                //把 arr[i]放到数组里面，之后i++即移动到下一个元素
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int x = 0; x < temp.length; x++) {
            arr[x + left] = temp[x];
        }
    }

    /**
     * 快速排序：  小  主元  大
     * 通过一趟排序将待排序记录分割成独立的两部分，其中一部分记录的关键字均比另一部分关键字小，则分别对这两部分继续进行排序，直到整个序列有序。
     * <p>
     * 把整个序列看做一个数组，把第零个位置看做中轴，和最后一个比，如果比它小交换，比它大不做任何处理；交换了以后再和小的那端比，比它小不交换，比他大交换。
     * 这样循环往复，一趟排序完成，左边就是比中轴小的，右边就是比中轴大的，然后再用分治法，分别对这两个独立的数组进行排序。
     */
    public static void quickSortTest() {
        int[] numbers = {9, 2, 6, 3, 5, 7, 10, 11, 12};
        if (numbers.length > 0) {//查看数组是否为空
            quickSort(numbers, 0, numbers.length - 1);
        }
        printAry("快速排序：", numbers);
    }

    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }
    }

    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers 带查找数组
     * @param low     开始位置
     * @param high    结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; //数组的第一个作为中轴
        while (low < high) {
            //一直循环，直到 numbers[high]<temp 则记录在左边，如果满足low<high 继续进行下一轮的while循环
            while (low < high && numbers[high] > temp) {
//            System.err.println("high1："+high);

                high--;
            }
//            System.err.println("high2："+high);
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while (low < high && numbers[low] < temp) {
                low++;
            }
            numbers[high] = numbers[low]; //比中轴大的记录移到高端
        }
        numbers[low] = temp; //中轴记录到尾
        return low; // 返回中轴的位置
    }

    public static void insertionSort2(int[] arr) {
        int len = arr.length;
        int preIndex, current;
    }


    public static int[] randomAry(int len) {
        int[] ary = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            ary[i] = random.nextInt(100);
        }
        return ary;
    }

    public static void printAry(String str, int[] ary) {
        System.out.println(str + "：");
        for (int i : ary) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }


}
