package task2621;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ReferenceFieldTest {
    public static void main(String[] args) {
        Student student = new Student();
        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        updater.compareAndSet(student, null,"Gu du");
        System.out.println(student);
    }
}

@Slf4j
@ToString
class Student {
    volatile String name;
}

