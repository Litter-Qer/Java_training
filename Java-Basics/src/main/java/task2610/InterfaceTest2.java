package task2610;

import task2610.interfaces.methods.MyClassSetName;
import task2610.interfaces.methods.MyConstructor;
import task2610.interfaces.methods.MyObjectSetName;
import task2610.interfaces.methods.MyStaticPower;
import task2610.pojo.Student;

public class InterfaceTest2 {
    public static void main(String[] args) {
        instanceTest();
        staticTest();
        classTest();
        constructorTest();
    }

    public static void constructorTest() {
        MyConstructor stuNew = Student::new;
        Student a = stuNew.StudentNew();
        System.out.println(a);
    }

    public static void classTest(){
        Student a = new Student();
        MyClassSetName myClassSetName = Student::setName;
        myClassSetName.set(a,"abc");
        System.out.println(a.getName());
    }

    public static void staticTest(){
        MyStaticPower myStaticPower = Math::max;
        System.out.println(myStaticPower.max(5,2));
    }

    public static void instanceTest(){
        Student a = new Student();

        // 简单的赋值方式
        MyObjectSetName myObjectSetName = (String name) ->{
            a.setName(name);
        };
        myObjectSetName.set("a");
        System.out.println(a.getName());

        // 简化上面的写法
        MyObjectSetName myObjectSetName1 = name -> a.setName(name);
        myObjectSetName1.set("b");
        System.out.println(a.getName());

        // 使用object::instanceMethod 来简写
        MyObjectSetName myObjectSetName2 = a::setName;
        myObjectSetName2.set("c");
        System.out.println(a.getName());
    }
}
