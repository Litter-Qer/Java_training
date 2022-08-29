import pojo.*;

import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Example1 {
    public static void main(String[] args) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, NoSuchFieldException {
        Example1 emp = new Example1();
//        // 获取class
//        System.out.println("Method 1");
//        emp.obtainClass();
//        System.out.println("\nMethod 2");
//        emp.obtainClass2();
//        System.out.println("\nMethod 3");
//        Hotpot htt = new Hotpot();
//        Class htc = htt.getClass();
        Class cls = emp.obtainClass3();
        System.out.println();

        // 创建一个class对象
        Object htObj = cls.getDeclaredConstructor().newInstance();
        Hotpot ht = (Hotpot) htObj;
//        System.out.println(ht);

        // 获取类中的构造器
        emp.obtainMethods(cls);

        // 调取所有的fields
        emp.obtainFields(cls);

        // 赋值和取值
        Field fBranch = cls.getDeclaredField("branch");
        fBranch.setAccessible(true);
        fBranch.set(ht,3);
        System.out.println(ht.getBranch());

        // 使用方法
        Method method = cls.getMethod("order", ArrayList.class);
        ArrayList<String> orders = new ArrayList<>();
        orders.add("a");
        orders.add("b");
        method.invoke(ht,orders);
    }


    public void obtainClass() {
        Class clm = Example1.class;
        System.out.println(clm);
        Class rsc = Restaurant.class;
        System.out.println(rsc);
    }

    /**
     * 可能会出现不存在的class，要保证名字输入正确
     *
     * @throws ClassNotFoundException 没找到class
     */
    public void obtainClass2() throws ClassNotFoundException {
        System.out.println(Class.forName("Example1"));
        System.out.println(Class.forName("pojo.Restaurant"));
    }

    /**
     * 使用getClass方法必须确保 想要调取的类可以被实例化
     */
    public Class obtainClass3() {
        Hotpot ht = new Hotpot();
        Class htc = ht.getClass();
        System.out.println(htc);
        return htc;
    }

    public void obtainConstructor(Class cls){
        StringBuilder str = new StringBuilder();
        Constructor[] constructors = cls.getConstructors();
        for (Constructor constructor : constructors) {
            str.append(MessageFormat.format(
                    "mod: {0} name: {1} types: ",
                    Modifier.toString(constructor.getModifiers()),
                    constructor.getName()));
            Class[] pars = constructor.getParameterTypes();
            for (Class par : pars) {
                str.append(MessageFormat.format("{0} ",par.getName()));
            }
            str.append("\n");
        }
        System.out.println(str);
    }

    public void obtainMethods(Class cls){
        StringBuilder str = new StringBuilder();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            str.append(MessageFormat.format(
                    "modifier: {0} name: {1} types: ",
                    Modifier.toString(method.getModifiers()),
                    method.getName()));
            Class[] pars = method.getParameterTypes();
            for (Class par : pars) {
                str.append(MessageFormat.format("{0} ",par.getName()));
            }
            str.append("\n");
        }
        System.out.println(str);
    }

    public void obtainFields(Class cls){
        StringBuilder str = new StringBuilder();
        Field[] fields = cls.getFields();
        for(Field field: fields){
            str.append(MessageFormat.format(
                    "mod: {0} type: {1} name: {2}\n",
                    Modifier.toString(field.getModifiers()),
                    field.getType(),
                    field.getName()
            ));
        }
        System.out.println(str);
    }


}