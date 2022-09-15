package chapter5;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.System.currentTimeMillis;
import static java.lang.invoke.MethodHandles.lookup;

public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }

        public static void main(String[] args) throws Throwable {
            Object obj = (currentTimeMillis() % 2) == 0 ? System.out : new ClassA();
            getPrintlnMH(obj).invoke("good");
        }

        private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
            MethodType mt = MethodType.methodType(void.class, String.class);
            return lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
        }
    }
}
