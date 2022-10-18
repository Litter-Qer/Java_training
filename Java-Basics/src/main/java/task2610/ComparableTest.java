package task2610;

import task2604.pojo.Student;

import java.util.Arrays;

public class ComparableTest {
    public static void main(String[] args) {
        Student[] students = new Student[3];

        Student student1 = new Student(1000,"Jon", "male");
        Student student2 = new Student(1001,"Jeff", "male");
        Student student3 = new Student(1002,"Julie", "female");

        students[0] = student1;
        students[1] = student2;
        students[2] = student3;
        System.out.println(Arrays.toString(students));

        Arrays.sort(students);
        System.out.println(Arrays.toString(students));
    }
}
