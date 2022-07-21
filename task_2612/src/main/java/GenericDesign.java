import pojo.Person;
import pojo.Student;

import java.util.Comparator;

public class GenericDesign {
    public static void main(String[] args) {
        Pair<String> pair1 = new Pair<>("a","b");
//        Pair<Integer> pair2 = new Pair<>(1,2);
//
        System.out.println(pair1.getFirst()+pair1.getSecond());
//        System.out.println(pair2.getFirst()+pair2.getSecond());
//        String[] names = {"hash","jon","jeff","ryan","ashe","zood","tom"};

//        Pair<String> pair3 = minmax(names);
//        System.out.println(pair3.getFirst()+" "+pair3.getSecond());

//        String middle = GenericDesign.<String>getMiddle(names);
//        System.out.println(middle);

//        double middle2 = GenericDesign.getMiddle(1.2, (double) 5, 0.5);
//        System.out.println(middle2);

//        String min = GenericDesign.min(names);
//        System.out.println(min);
        Person a = new Person(100);
        Person b = new Person(200);
        Student c = new Student(300);
        Student d = new Student(400);

        printMaxMin(new Pair<Student>(c,d));

    }

    public static void printMaxMin(Pair<? extends Person> pair){
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }

    public static Pair<String> minmax(String[] strings){
        String min = strings[0];
        String max = strings[0];
        for (int i = 0; i < strings.length; i++) {
            if (min.compareTo(strings[i]) > 0){
                min = strings[i];
            }
            if (max.compareTo(strings[i]) < 0){
                max = strings[i];
            }
        }
        return new Pair<String>(min,max);
    }

    public static <T> T getMiddle(T[] ts){
        return ts[ts.length/2];
    }

    public static <T extends Comparable> T min(T[] ts) {
        if (ts == null || ts.length == 0) return null;
        T smallest = ts[0];
        for (T t : ts) {
            if(t.compareTo(smallest) < 0) smallest = t;
        }
        return smallest;
    }
}
