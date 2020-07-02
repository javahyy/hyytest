package test;

public class Stu {
    public static void main(String[] args) {

//        Student student = new Student();
//        String name = student.getName();
//        System.out.println(name);
        int[] ary = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
//        testException(ary);
        Object num = null;


    }

    public static void testException(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            // 局部变量m
            int m = ary[i];
            try {
                System.out.println("aaaa====" + m);
                if (m == 5) {
                    System.out.println(m / 0);
                }
            } catch (Exception e) {
                System.err.println("err=== " + m);
                System.err.println(e); //java.lang.ArithmeticException: / by zero
                /* Object的toString方法，Throwable类重写了，所以对于异常对象e,实际打印为：
                    public String toString() {
                        String s = getClass().getName();
                        String message = getLocalizedMessage();
                        return (message != null) ? (s + ": " + message) : s;
                    }
                 */
                continue;
            }
        }
    }

}


class Student {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}