package chapter6.syntaxsugar;

import java.util.ArrayList;

public class sugar1 {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        System.out.println(a.getClass() == b.getClass());
        a.add("good");
        System.out.println(a.get(0));
    }
}
