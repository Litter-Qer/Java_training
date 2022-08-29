package task2611;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

public class ReflectionTest {
    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test1() throws Exception {
        StringBuilder str = new StringBuilder("The outer class is ");
        Class clazz = OuterClass.class;
        Constructor outCst = clazz.getDeclaredConstructor();
        Object outObj = outCst.newInstance();

        str.append(clazz.getName());
        str.append("\nThe inner classes are showing below");

        Class[] classes = clazz.getDeclaredClasses();
        for (Class cls: classes) {
            String mod = Modifier.toString(cls.getModifiers());
            String name = cls.getName();
            Field field;
            String fVar = "";
            if(mod.contains("static")){
                // 获取
                Object inObj = cls.getDeclaredConstructor().newInstance();
                field = cls.getDeclaredField("name");
                field.setAccessible(true);
                fVar = (String) field.get(inObj);
            } else {
                Constructor inCst = cls.getDeclaredConstructor(clazz);
                inCst.setAccessible(true);
                Object inObj = inCst.newInstance(outObj);
                field = cls.getDeclaredField("name");
                field.setAccessible(true);
                fVar = (String) field.get(inObj);
            }
            str.append(MessageFormat.format(
                    "\n\nName: {0}\nModifier: {1}\nField: {2}\nField value: {3}",
                    name, mod, field.getName(), fVar));
        }

        // 获取匿名内部类
        Field f = clazz.getDeclaredField("r");
        f.setAccessible(true);
        Runnable r = (Runnable) f.get(outObj);
        System.out.println(str);
        r.run();
    }
}
