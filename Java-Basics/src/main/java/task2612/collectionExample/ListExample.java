package task2612.collectionExample;

import pojo.Student;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ListExample {
    public static void main(String[] args) {
        System.out.println("This is a program");
    }

    public void pQueue(){
        PriorityQueue<Student> stuQue = new PriorityQueue<Student>(5,
                new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        });

    }
}
