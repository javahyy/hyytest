package review.design;

//单例设计模式:最好理解的一种设计模式，分为懒汉式和饿汉式

//饿汉式
public class Singleton01 {
    // 直接创建
    public static Singleton01 instance = new Singleton01();
    // 私有化构造函数
    private Singleton01(){}

    // 返回对象实例
    public static Singleton01 getInstance() {
        return instance;
    }
}

//懒汉式
//public
class Singleton02 {
    // 必须加上这个 volatile 让值线程间可见
    public static volatile Singleton02 instance = null;
    // 私有化构造函数
    private Singleton02(){}

    // 返回对象实例
    public static Singleton02 getInstance() {
        if (instance == null){
            //保证同一时间只有一个线程能访问到，避免线程不安全而创建多个实例
            synchronized (Singleton02.class){
                if (instance == null){
                    instance = new Singleton02();
                }
            }
        }
        return instance;
    }
}

