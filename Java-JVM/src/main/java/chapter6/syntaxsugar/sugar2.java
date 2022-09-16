package chapter6.syntaxsugar;

import java.util.Arrays;
import java.util.List;

public class sugar2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        System.out.println(sum);
    }
}
