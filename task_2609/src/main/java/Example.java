import pojo.*;

public class Example {
    public static void main(String[] args) {
//        rchTest();
//        abTest();
//        abTest1();
        abTest2();
    }

    public static void abTest() {
//        B b = new B();
//        A.print();
//        B.print();
    }

    public static void abTest1() {
        A a1 = new A();
        B b1 = new B();
        b1 = (B) a1;
        System.out.println(b1);
    }

    public static void abTest2() {
        A a1 = new B();
        B b1 = new B();
        b1 = (B) a1;
        System.out.println(a1);
        System.out.println(b1);
    }

    public static void rchTest() {
        // plain class construction
        Hotpot pandaMain = new Hotpot("Panda Hotpot");
        pandaMain.setBranch(0);
        pandaMain.setSlogan("Spicy is the thing");
        pandaMain.setAddress("100 Victoria St, Carlton VIC 3053");
        pandaMain.setType("Fine dining");
        System.out.println(pandaMain);

        // plain class construction
        Cafeteria dearAbbey = new Cafeteria("Dear Abbey");
        dearAbbey.setSale(true);
        dearAbbey.setDecorated(false);
        dearAbbey.setAddress("23A Gladstone St, Moonee Ponds VIC 3039");
        dearAbbey.setType("Normal");
        System.out.println(dearAbbey);

        // Polymorphism example
        Restaurant pandaBr1 = new Hotpot("Panda Hotpot");
        ((Hotpot) pandaBr1).setBranch(1);
        ((Hotpot) pandaBr1).setSlogan("Spicy is the thing");
        pandaBr1.setAddress("1056/1060 Dandenong Rd, Carnegie VIC 3163");
        pandaBr1.setType("Fine dining");
        System.out.println(pandaBr1);

        // Receive Method
        pandaMain.receive(10);
    }
}