package learn;

import com.google.common.collect.ImmutableMap;

public class ImmutableMapTest {

    public static void main(String[] args) {

        // 等同map url:ccc   name:hyy
        ImmutableMap<String, String> of = ImmutableMap.of("url", "ccc","name","hyy");
        String url = of.get("url");
        String name = of.get("name");
        System.out.println(url+"   "+name);
    }


}
