# 泛型程序设计

## 通配符

一般使用T，E，K，V这类具有明确意义的字母，但是也可以使用其他字母，只要表达出意思即可。使用的时候一般就是用<>括起来。
比如可以限制一个ArrayList内部元素种类，通过```<String>```就可以保证里面只包含String。这里需要注意的就是内部的元素必须为类名，
不可以是primitive类型。但是由于java中的语法糖会帮助我们自动装箱和拆箱，所以直接使用wrapper的类即可。当然除了这些以外，
在设计一个泛型程序的时候也可以使用多个这样的通配符，比如我可以设计一个键值对，那么它的定义方式就应该是<K, V>。这里K表示key，而V表示value。
虽然我没有明确定义他们的具体类型，但是我可以保证key的类型是一样的，同样value的类型也是一样的。

## 泛型方法

和前面的思路一样，一般用于处理无法预测的入参。比如设计的程序中出现了要找到某一个数组的中间位置。由于不清楚这个数组的具体类型，
在设计的时候就可以直接只用泛型来定义。

```java
public static <T> T getMiddle(T[] ts){
    return ts[ts.length/2];
}
```

在定义泛型方法的时候要注意在修饰符后面加入```<T>```表示这是一个泛型方法。由于这里是找某一个数组的中间位置的值，所以输出也要调整为T。
从这个例子中就可以看出泛型设计的优势，因为在使用这个方法前我并不知道具体的数组类型，按照没有泛型的写法。我需要设计很多个相对应方法，并且每个方法除了参数不同，
方法体完全一样，这无疑是浪费时间，而且不够优雅。但是使用泛型我就可以把他们直接整合在一起。  
使用泛型方法的时候也要注意一些小的细节。比如下面的例子

```java
String[] names = {"hash","jon","jeff","ryan","ashe","zood","tom"};
String middle = GenericDesign.<String>getMiddle(names);
```

严格意义上说，我们需要在调用方法前加入要使用的类型名。但是这样一来就失去了泛型所带来的意义了，因为本来我就希望他是泛化的东西，现在我又让他强行地变成一个具体的类型。
所以这里有一个体现了java的美好，这里编译器其实会提示不需要写入```<String>```，因为放入方法中的东西本身就是string数组，所以IDEA和编译器会自动把string带入方法，
如此就又保证了泛型的设计理念。虽然我知道输出的一定是string，但是调用方法时候，我不需要知道。但是这样的做法并不是每次都能成功，比如下面这个例子。

```java
double middle2 = GenericDesign.getMiddle(1.2,5,0.5);
```

尝试把一个这样的数组放入泛型方法中，IDEA会直接报错，告诉你编译不可通过。这里的原因也很简单，因为编译器打包参数是，会尽量选择最合适的。如此5就会直接被改成Integer，
而且剩下的则是Double。泛型方法虽然规定了可以又不用的参数类型，但是同一个通配符的类型必须一致，这也就说明了这里Integer和Double不一致，所以导致了编译不通过。
那么解决的方案也很简单，只需要强制向上转置成double即可。

## 类型变量的限定

```java
 public static <T extends Comparable> T min(T[] ts) {
    if (ts == null || ts.length == 0) return null;
    T smallest = ts[0];
    for (T t : ts) {
        if(t.compareTo(smallest) < 0) smallest = t;
    }
    return smallest;
}
```

在定义上述的泛型方法时，我采取了一个```<T extends Comparable>```的方式。这里主要是因为需要比较，而我又不想带额外定义一个comparator。
这种情况下，为了使用compareTo这个方法，我就必须保证我输入的类型必须可以被比较。那么根据之前使用的经验，直接继承Comparable这个类，由于comparable这个接口本身就是一个泛型类。
更是完全契合了这里的方法。但是这里仍然要注意，如果把一个没有时间comparable的类尝试放入这个方法，编译器会直接报错。当然这里也用了extends关键字而非implements。
这是java的一个小设计吧，所有这种方式的泛型限定都只使用extends来作为限定。如果实现接口的话，只需要使用&隔开即可。这里特别需要注意的是，如果使用的是类，因为java中的单一继承原则。
只能放入一个类，且这个类必须放在第一个。

## JVM如何看待泛型

其实在JVM中压根就没有泛型的概念，它不过只是把泛型当成其他的普通类来处理。我们可以看下面的反编译结果

```shell
 0 aload_0
 1 aload_1
 2 invokespecial #13 <Pair.<init> : (Ljava/lang/Object;)V>
 5 aload_0
 6 aload_2
 7 putfield #16 <Pair.second : Ljava/lang/Object;>
10 return
```

这段代码是对Pair中的构造器进行的反编译结果，可以很明确地看出，JVM并没有把泛型当作是一个类型使用，而是直接使用Object作为变量的类型。实际上使用的使用的也是这样，
JVM会把原始类型（raw type）直接替换为限定的类型（Object）。这个过程被叫做擦除。这里具体可以看下面的结果

```shell
16 invokevirtual #22 <Pair.getFirst : ()Ljava/lang/Object;>
19 checkcast #26 <java/lang/String>
22 aload_1
23 invokevirtual #28 <Pair.getSecond : ()Ljava/lang/Object;>
26 checkcast #26 <java/lang/String>
29 invokedynamic #31 <makeConcatWithConstants, BootstrapMethods #0>
34 invokevirtual #35 <java/io/PrintStream.println : (Ljava/lang/String;)V>
37 return
```

这里是对GenericDesign类中的第一部分反编译的结果。可以看到JVM在返回类型的时候，其实还是先返回了Object类，再强制转成String。而并非一开始就转成String。
虽然编译器可以直接预测结果类型，但是JVM压根就不理解，它只能通过先**擦除**raw type的方式把输出转为object，然后再改写成string类。

## 通配符扩展

虽然一般情况下直接使用```<T>```就可以解决大部分的泛型方法设计问题，但是如果遇上了以下问题的话，那么普通的泛型设计就无法使用了。

```java
Student c = new Student(300);
Student d = new Student(400);
printMaxMin(new Pair<Student>(c,d));

public static void printMaxMin(Pair<Person> pair){
    System.out.println(pair.getFirst());
    System.out.println(pair.getSecond());
}
```

当我尝试使用把Student作为Pair的类型变量放入这个方法中时，编译器告诉我这里是不对的。即使我规定了Student已经是Person的子类，但是编译器仍然无法通过。
这里的原因其实就是刚才对JVM的分析,虽然看上去它是泛型的，但是实际上JVM并不理解这个概念。那么这里java也有解决方案——通配符的变化。直接将上面的方法入参改为```Pair<? extends Person> pair```,
这里？实际上就表示所有可能的子类，也就是说如果我再使用一个继承了Person的类，仍然可以直接使用。当然解决这个问题方法有很多，也可以在调用的时候直接省略<>中的内容。
那样的话JVM就会自动找寻匹配的项，如果可以保证Pair中的类型正确的话，那么结果就是正确的。  
既然可以出现子类的限定，那么肯定也有父类的限定。一般父类的限定使用<? super ClassName>。其他和前面一致。
