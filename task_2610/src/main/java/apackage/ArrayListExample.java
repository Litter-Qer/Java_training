package apackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListExample {
    public static void main(String[] args) {
        ArrayListExample ale = new ArrayListExample();
        ale.list1();
        ale.list2();
        ale.lkList();
    }

    public void list1(){
        ArrayList al = new ArrayList();
        al.add("This is a String");
        al.add(10);
        al.add(true);

        for (int i = 0; i < al.size(); i++) {
            Object obj = al.get(i);
            System.out.println(obj);
        }
        System.out.println();
    }

    public void list2(){
        List<String> al = new ArrayList();
        al.add("This is the 1st line");
        al.add("This is the 2nd line");
        al.add("This is the 3rd line");

        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
        System.out.println();
    }

    public void list3(){
        ArrayList<String> al = new ArrayList();
        al.add("1");
        al.add("2");
        // int类型 不可加入
//        al.add(3);

        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
        System.out.println();
    }

    public void lkList(){
        LinkedList<String> al = new LinkedList<>();
        al.add("1");
        al.add("2");
        al.add("3");

        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
        System.out.println();
    }
}
