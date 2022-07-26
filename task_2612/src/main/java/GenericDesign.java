import pojo.Person;
import pojo.StringList;
import pojo.Student;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenericDesign {
    public static void main(String[] args) {
//        Pair<String> pair1 = new Pair<>("a","b");
//        Pair<Integer> pair2 = new Pair<>(1,2);
//
//        System.out.println(pair1.getFirst()+pair1.getSecond());
//        System.out.println(pair2.getFirst()+pair2.getSecond());
//        String[] names = {"hash","jon","jeff","ryan","ashe","zood","tom"};

//        Pair<String> pair3 = minmax(names);
//        System.out.println(pair3.getFirst()+" "+pair3.getSecond());

//        String middle = GenericDesign.<String>getMiddle(names);
//        System.out.println(middle);

//        double middle2 = GenericDesign.getMiddle(1.2, (double) 5, 0.5);
//        System.out.println(middle2);

//        String min = GenericDesign.min(names);
//        System.out.println(min);
//        Person a = new Person(100);
//        Person b = new Person(200);
//        Student c = new Student(300);
//        Student d = new Student(400);

//        printMaxMin(new Pair<Student>(c,d));
//        reflectiveEmp1();
//        reflectiveEmp2();
        reflectiveEmp3();
    }

    /**
     * 抽取成员方法
     */
    public static void reflectiveEmp3(){
        StringBuilder str = new StringBuilder();
        List<String> strings = new ArrayList<>();
        Method[] methods = strings.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Type[] types = method.getGenericParameterTypes();
            String name = method.getName();
            name = String.format("%-15s", name);
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType) type;
                    Type[] typeArgs = pType.getActualTypeArguments();
                    for (Type typeArg : typeArgs) {
                        Class fieldArgClass = typeArg.getClass();
                        String pars = fieldArgClass.getName();
                        pars = String.format("%-28s", pars);
                        str.append(MessageFormat.format("\nMethod Name: {0}  Pars: {1}  Generic type: {2}",
                                name, pars, type));
                    }
                }
            }
        }
        System.out.println(str);
    }

    /**
     * 抽取成员变量
     */
    public static void reflectiveEmp2(){
        StringBuilder str = new StringBuilder();
        List<String> strings = new ArrayList<>();
        Field[] fields = strings.getClass().getDeclaredFields();
        for (Field field : fields) {
            Type gType = field.getGenericType();
            String name = field.getName();
            name = String.format("%-15s",name);
            if (gType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) gType;

                Type[] typeArgs = pType.getActualTypeArguments();
                for (Type typeArg : typeArgs) {
                    Class fieldArgClass = (Class) typeArg;
                    String pars = fieldArgClass.getName();
                    pars = String.format("%-28s",pars);
                    str.append(MessageFormat.format("\nMethod Name: {0}  Pars: {1}  Generic type: {2}",
                            name, pars, gType));
                }
            }
        }
        System.out.println(str);
    }


    /**
     * 获取返回值信息
     */
    public static void reflectiveEmp1(){
//        StringList stringList = new StringList();
        StringBuilder str = new StringBuilder();
        List<String> strings = new ArrayList<>();
        str.append(strings.getClass());
        Method[] methods = strings.getClass().getMethods();
        for (Method method : methods) {
            Type rType = method.getGenericReturnType();
            String name = method.getName();
            name = String.format("%-15s",name);
            System.out.println(name + rType + "\n");
            if (rType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) rType;
                String pars = pType.getTypeName();
                Type[] typeArguments = pType.getActualTypeArguments();
                pars = String.format("%-28s",pars);
                str.append(MessageFormat.format("\nMethod Name: {0}  Pars: {1}  Return type: {2}",
                        name, pars, rType));

                for (Type typeArgument : typeArguments) {
                    Class typeClass = typeArgument.getClass();
                    str.append(MessageFormat.format("\nreturn argument class: {0}\n",
                            typeClass));
                }
            }
        }
        System.out.println(str);
    }

    public static void printMaxMin(Pair<? extends Person> pair){
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }

    public static Pair<String> minmax(String[] strings){
        String min = strings[0];
        String max = strings[0];
        for (int i = 0; i < strings.length; i++) {
            if (min.compareTo(strings[i]) > 0){
                min = strings[i];
            }
            if (max.compareTo(strings[i]) < 0){
                max = strings[i];
            }
        }
        return new Pair<String>(min,max);
    }

    public static <T> T getMiddle(T[] ts){
        return ts[ts.length/2];
    }

    public static <T extends Comparable> T min(T[] ts) {
        if (ts == null || ts.length == 0) return null;
        T smallest = ts[0];
        for (T t : ts) {
            if(t.compareTo(smallest) < 0) smallest = t;
        }
        return smallest;
    }
}
