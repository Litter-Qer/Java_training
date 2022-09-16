package chapter6;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {
//    public static String method(List<String> list) {
//        System.out.println("invoke method String list");
//        return "";
//    }

    public static int method(List<Integer> list) {
        System.out.println("invoke method Integer list");
        return 1;
    }

    public static void main(String[] args) {
//        method(new ArrayList<String>());
        method(new ArrayList<Integer>());
    }
}
