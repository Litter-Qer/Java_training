package pojo;

import computer.Computer;

import java.util.Comparator;

public class Student implements Comparable, Comparator {
    private int id;
    private String name;
    private String gender;

    public Student() {

    }

    public Student(int id) {
        this();
        this.id = id;
    }

    public Student(int id, String name) {
        this(id);
        this.name = name;
    }

    public Student(int id, String name, String gender) {
        this(id, name);
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
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
        return this.id == otherStudent.id;
    }
}
