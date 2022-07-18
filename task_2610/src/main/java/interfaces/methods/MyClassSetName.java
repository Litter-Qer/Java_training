package interfaces.methods;

import pojo.Student;

@FunctionalInterface
public interface MyClassSetName {
    void set(Student student, String name);
}
