package chapter5;

public class StaticDispatch {
    static abstract class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}

    public void sayHello(Human guy){
        System.out.println("人类");
    }

    public void sayHello(Man guy){
        System.out.println("男士");
    }

    public void sayHello(Woman guy){
        System.out.println("女士");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
