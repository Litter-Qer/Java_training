package pojo;

import computer.Computer;

import java.util.Comparator;

public class Student implements Comparable, Comparator {
    private final Integer id;
    private String name;
    private String gender;

    public Student() {
        this.id = 10000;
    }

    public Student(String name) {
        this();
        this.name = name;
    }

    public Student(String name, String gender) {
        this(name);
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(Object o) {
        // 由于传进来的是一个object所以需要先向下转型
        Student otherStudent = (Student) o;
        return Integer.compare(this.id, otherStudent.id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                "}\n";
    }


    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        Student otherStudent = (Student) obj;
        return this.id.equals(otherStudent.id);
    }
}
