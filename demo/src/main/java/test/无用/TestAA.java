package test.无用;

public class TestAA {
    // Byte 一个字节的数据大小范围为什么是-128~127  https://blog.csdn.net/hml666888/article/details/81107043
    // https://www.cnblogs.com/yibao/p/7670177.html java中字节数组byte[]和字符（字符串）之间的转换
    public static void main(String[] args) {

        byte[] b = new byte[]{110,117,108,108};
        System.out.println(b.length+" "+b[3]);
        String a = new String(b);
        System.out.println(a);  //null   110的ASCII 是n  117是u 108是l 拼起来就是 null

        byte[] c = new byte[2];
        c[0]=123;
        System.out.println(c[0]+" "+c[1]);
        System.out.println("测试="+new String(c));

        byte[] b1={(byte)0xB8,(byte)0xDF,(byte)0xCB,(byte)0xD9};
        String str= new String (b1);
        System.out.println(str);
    }
}
