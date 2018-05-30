package think.generic.method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class New {
    public static <K,V> Map<K,V> map() {
        return new HashMap<K, V>();
    }

    public static void main(String[] args) {
        // 自动将要接受引用的类型 注入 构造方法中
        // 返回值 类型推断 只对 = 有效， 在1.7后该特性可以直接被编译器识别了，不需要额外定义方法
        Map<String, List<String>> sls = New.map();
    }
}
