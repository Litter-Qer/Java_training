## SQL注释问题

```shell
mysql> show full columns from hotpots;
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
| Field      | Type        | Collation          | Null | Key | Default | Extra | Privileges                      | Comment |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
| id         | int         | NULL               | NO   | PRI | NULL    |       | select,insert,update,references | Second  |
| name       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references | First   |
| type       | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |         |
| soupBase   | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |         |
| price      | double(5,2) | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
| ingredient | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
| dish       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
7 rows in set (0.00 sec)

mysql> alter table 'hotpots' modify column id int comment 'abcddef';
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''hotpots' modify column id int comment 'abcddef'' at line 1
mysql> alter table hotpots modify column id int comment 'Second';
Query OK, 0 rows affected (0.01 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> show full columns from hotpots;
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
| Field      | Type        | Collation          | Null | Key | Default | Extra | Privileges                      | Comment |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
| id         | int         | NULL               | NO   | PRI | NULL    |       | select,insert,update,references | Second  |
| name       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references | First   |
| type       | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |         |
| soupBase   | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |         |
| price      | double(5,2) | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
| ingredient | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
| dish       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |         |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+---------+
7 rows in set (0.00 sec)

mysql> alter table hotpots modify column name int comment 'asdfgzxcasdasdadadwadczxczxcawda';
Query OK, 0 rows affected (0.01 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> show full columns from hotpots;
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+----------------------------------+
| Field      | Type        | Collation          | Null | Key | Default | Extra | Privileges                      | Comment                          |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+----------------------------------+
| id         | int         | NULL               | NO   | PRI | NULL    |       | select,insert,update,references | Second                           |
| name       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references | asdfgzxcasdasdadadwadczxczxcawda |
| type       | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |                                  |
| soupBase   | varchar(20) | utf8mb4_0900_ai_ci | YES  |     | NULL    |       | select,insert,update,references |                                  |
| price      | double(5,2) | NULL               | YES  |     | NULL    |       | select,insert,update,references |                                  |
| ingredient | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |                                  |
| dish       | int         | NULL               | YES  |     | NULL    |       | select,insert,update,references |                                  |
+------------+-------------+--------------------+------+-----+---------+-------+---------------------------------+----------------------------------+
7 rows in set (0.00 sec)
```
除此之外我还尝试了```ALTER TABLE `hotpots` CHANGE `name` `name` INT COMMENT 'name of user';```。  
无论是哪一个我都没法会自动换行的问题。。。。我用的版本是8.0。我不知道是不是我的操作有问题，我没办法复现这个问题。
我尝试排除了一些可能的干扰原因，比如table的format。换了不同的format，结果都是一样的。我找不出问题可能在哪儿了。。

## 反射机制

研究以下泛型反射的获取问题

```java
public class StringList extends ArrayList<String> {

    public static void main(String[] args) {
        //如何通过反射获取形参类型 String.class ?
        StringList stringList = new StringList();
        //如何通过反射获取形参类型 String.class ?
        List<String> strings = new ArrayList<>();
    }
}
```

### 返回值获取

通过已下代码获取泛型返回值,省略了打印的部分。

```java
public static void reflectiveEmp1(){
    List<String> strings = new ArrayList<>();
    Method[] methods = strings.getClass().getMethods();
    for (Method method : methods) {
        Type rType = method.getGenericReturnType();
        String name = method.getName();
        Type[] types = method.getParameterTypes();
        
        if (rType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) rType;
            pars = pType.getTypeName();
            Type[] typeArguments = pType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                Class typeClass = typeArgument.getClass();
            }
        }
    }
}
```

直接对strings这个String数列进行反射得到class。 通过class得到所有的方法。使用`getGenericReturnType()`直接获得返回值，如没有返回值则是void。
由于泛型的返回值的特性，可以使用instanceof ParameterizedType来过滤非泛型的返回值。这里有个非常奇怪的现象，比如get和remove的返回值都是E，却没有被归类为ParameterizedType。

> 这里我想的一个主要原因是虽然我在定义strings的时候已经使用String作为<>中的类。那么编译器自己其实可以明白，get的值一定是一个string。但是类似sublist，iterator这样的返回值都是`List<E>`或者`iterator<E>`，
> java编译器只会看最外层，也就是List<>这一层，所以java任然认为它是一个泛型值。  

最后通过`getActualTypeArguments()`来调取所有的type arguments。这里有一个机制就是java还是只会调取最外层的type，比如我嵌套一个Map<K,Set<E,V>>。
那么java还是会告诉我一共两个泛型变量，一个是TypeVariable，一个是Set。

### 成员变量信息

```java
public static void reflectiveEmp2(){
    List<String> strings = new ArrayList<>();
    Field[] fields = strings.getClass().getDeclaredFields();
    for (Field field : fields) {
        Type gType = field.getGenericType();
        String name = field.getName();
        if (gType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) gType;

            Type[] typeArgs = pType.getActualTypeArguments();
            for (Type typeArg : typeArgs) {
                Class fieldArgClass = (Class) typeArg;
                String pars = fieldArgClass.getName();
            }
        }
    }
}
```

基本思路和之前一致，先获取所有的成员变量。因为ArrayList中没有成员变量是泛型的，所以就没有任何打印结果。其它步骤都是一样的。

### 抽取成员方法

```java
public static void reflectiveEmp3(){
        List<String> strings = new ArrayList<>();
        Method[] methods = strings.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Type[] types = method.getGenericParameterTypes();
            String name = method.getName();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType) type;
                    Type[] typeArgs = pType.getActualTypeArguments();
                    for (Type typeArg : typeArgs) {
                        Class fieldArgClass = typeArg.getClass();
                        String pars = fieldArgClass.getName();
                    }
                }
            }
        }
    }
```

还是一样的思路，直接从class中抽取方法。然后找class中的泛型参数，如果有就直接进行泛型转置在看具体的class名。
