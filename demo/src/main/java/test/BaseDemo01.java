package test;

import java.util.*;

public class BaseDemo01 {

    public static void main(String[] args) {
//        DemoVariable d1 = new DemoVariable();
//        DemoVariable d2 = new DemoVariable();
//        Integer a = 3;
//        testString01();
//        testString02();


//        hashMapSort();
        listDemo();
    }

    public static void listDemo() {
        //list有序：指的是存储有序
        List list = new ArrayList();
        list.add(11);
        list.add(6);
        list.add(2);
        list.add(4);
        Collections.sort(list);//默认升序
        list.forEach(i -> System.out.println(i));
        Hashtable h = new Hashtable<>();
    }

    public static void hashMapSort() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, new User("张三", 25));
        users.put(3, new User("李四", 22));
        users.put(2, new User("王五", 28));

        sortHashMap(users);
    }

    private static void sortHashMap(HashMap<Integer, User> users) {
        // 首先拿到 map 的键值对集合
        Set<Map.Entry<Integer, User>> entrySet = users.entrySet();

        // 将 set 集合转为 List 集合，为什么，为了使用工具类的排序方
        List<Map.Entry<Integer, User>> list = new ArrayList<>(entrySet);
        // 使用 Collections 集合工具类对 list 进行排序，排序规则使用匿名内部类来实现
//        Collections.sort(list, new Comparator<Map.Entry<Integer, User>>() {
//            @Override
//            public int compare(Map.Entry<Integer, User> o1, Map.Entry<Integer, User> o2) {
//                //按照要求根据 User 的 age 的倒序进行排
//                return o2.getValue().getAge() - o1.getValue().getAge();
//            }
//        });
        Collections.sort(list, (o1, o2) -> {
            //按照要求根据 User 的 age 的倒序进行排
            return o2.getValue().getAge() - o1.getValue().getAge();
        });
        //创建一个新的有序的 HashMap 子类的集合
        LinkedHashMap<Integer, User> linkedHashMap = new LinkedHashMap<>();
        // 将 List 中的数据存储在 LinkedHashMap 中
        for (Map.Entry<Integer, User> entry : list) {
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        entrySet.forEach(v -> {
            System.out.println(v.getKey() + "  " + v.getValue());
        });


    }


    public static void testString01() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(random.nextInt(100) + " ");
        }
    }

    public static void testString02() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        // == 比较的是地址，  equals 比较的是值
        System.out.println(s1 == s2); //false
        System.out.println(s1 == s5); //true
        System.out.println(s1 == s6); //false
        System.out.println(s1 == s6.intern()); //true
        System.out.println(s2 == s2.intern()); //false
    }
}


class User {
    public String name;
    public Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

class DemoVariable {
    static int i = 1;//类变量（静态变量）
    int j = 1;//实例变量

    public DemoVariable() {
        i++;
        j++;
        System.out.println("静态变量：" + i + "，实例变量：" + j);
    }
}
