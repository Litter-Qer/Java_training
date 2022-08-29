package task2612.pojo;

public class Person implements Comparable {
    protected int salary;

    public Person() {
    }

    public Person(int salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Object o) {
        Person p = (Person) o;
        return Integer.compare(this.salary, p.salary);
    }

    @Override
    public String toString() {
        return "salary = " + salary;
    }
}
