## 内部类

内部类，顾名思义就是把一个java类放在另外一个java中。但是根据不同的使用场景，一般把内部类分成一下四种类型。

- 成员内部类
- 局部内部类
- 静态内部类
- 匿名内部类

无论是哪一种内部类都共同符合一下的访问规则

- 内部类可以直接访问外部类的成员，包括private fields
- 外部类如想访问内部类的成员，则必须要实例化一个对象

以上的规则在碰到static的时候会稍有所变化，但是基本原则仍然一致。后面会给出详细的解释

#### 成员内部类

这种类型的内部类，使用场景应该是相对最多了.也是相对来说最好理解的。实际上这种类型就是把一个class直接放入另外一个class。
这里最明显的注意点就是修饰符的影响。既然讨论的是成员内部类，那么一般来说就是private和public。具体使用上区别也很简单，
private修饰的内部类只能在内部使用，无法在其他类中使用外部类来构造和调用。而public修饰的则可以直接通过外部类来调用，
并且可在别的类中实例化。虽然private修饰的内部类无法直接被调用，但是仍然可以通过外部类中的public method来进行间接调用，
这样的一个好处就是用户或者攻击者无法直接访问或改动内部类的数据，只能通过public method获取。

```java
public class OuterClass {
    final static String a = "final static string a";
    private String c = "private string c";

    public class InnerClass {
        int c = 5;
        int a = 10;

        public void info() {
            System.out.println("外部c变量： " + OuterClass.this.c);
            System.out.println("内部c变量： " + InnerClass.this.c);
            System.out.println("外部a变量： " + OuterClass.a);
            System.out.println("内部a变量： " + InnerClass.this.a);
            System.out.println("This is an inner class");
        }
    }
```

这里尝试变量同名的情况下，访问是如何进行的。得出一下规则，这里的同名变量使用规则和子类继承父类的规则非常的像。当没有同名变量是，可以直接使用变量名进行调用，
当出现了同名变量的时候，则是按照隐藏的规则。也就是说，直接使用变量名会调用内部类中的变量，而要想要外部类的则需要使用OuterClass.this.name。
因为是隐藏而不是替换，所以即便是外部类中final和static修饰的变量依旧可以重写并且使用。这里还牵扯到一个就近原则，如果方法中又出现了同名变量的话，
那么即使在内部类中也要加上this来保证调用到类的那个变量，而非方法中定义的。

```java
OuterClass out=new OuterClass();
OuterClass.InnerClass in=out.new InnerClass();

OuterClass.InnerClass in2=out.createInner();
```

内部类的实例化也和上面所说一样，需要依附于一个具体的外部类对象，不能直接进行实例化。这里特别要注意的就是内部类的构造器可以当成是外部类的一个方法。
当然也通过在外部类中实现一个返回值为内部类对象的方法来进行构造。这两种方法的结果是一样的，但是在编译器中的表现会有所不同，查看了反编译结果后会发现
外部类使用方法调用的时候，JVM使用的```invokevirtual```也就是动态调用,但是使用构造器时则是```invokespecial```，实际上等于在编译阶段已知结果。
如果追求极致的效率的话，那么构造器无疑还是能快一点点，因为编译阶段就已知结果。

```shell
0 new #7 <pojo/OuterClass>
3 dup
4 invokespecial #9 <pojo/OuterClass.<init> : ()V>
7 astore_1
8 new #10 <pojo/OuterClass$InnerClass>
11 dup
12 aload_1
13 dup
14 invokestatic #12 <java/util/Objects.requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;>
17 pop
18 invokespecial #18 <pojo/OuterClass$InnerClass.<init> : (Lpojo/OuterClass;)V>
21 astore_2
22 aload_1
23 invokevirtual #21 <pojo/OuterClass.createInner : ()Lpojo/OuterClass$InnerClass;>
26 astore_3
27 return
```

基本上原则就是把成员内部类当作是外部类的一个field就比较容易理解。

#### final static的影响

这里我还是没搞懂没什么，我尝试了观察反编译，排除了JDK版本的影响，网上找大神的思路，但是还是不能解释这个部分。先看下面这段代码。

```java
public class OuterClass2 {
    String a = "string a";

    public class InnerClass2 {
        public static final String a = "Success";
        public static Integer b = 10;
        final static Integer c = 5;

        public static void show() {
            System.out.println("This is a static method ---- InnerClass2");
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
        }
    }
}
```

根据非静态内部类的规则，按理来说我无法在内部类中定义static Integer b。 因为这个内部类不是static的，但是这里IDEA不仅没有报错，
而且无论使用jdk 17还是11都可以编译通过还可以正常运行。这里IDEA会提示使用static内部类，但是不是error。我可以理解final static在这里不会被影响，
因为final static一般是放入常量池的，尤其是String更是这样。所以final static修饰的变量不会牵扯到类的加载中去。但是这个Integer为什么依旧可以不报错。
这里的原因实在是分析不出来了。按理来说一个费劲太的内部类应该不能直接使用static的。我的猜测是javac对于没有直接定义成static的class会自动尝试匹配，
如果符合就会自动把他编译成一个static的类。虽然明显不靠谱，但是确实想不出怎么解释。

#### 局部内部类

由于它也是一个非静态的内部类，所以限制基本和成员内部类一致。不过它的作用域相对来说还要更小一些。基本仅限于在定义它的方法中使用。

```java
public void showInfo(){
        System.out.println("调用showInfo方法------1");

        class SmallClass {
            public static void suse() {
                System.out.println("这是sc的suse");
            }
        
            public void puse() {
                System.out.println("这是sc的puse");
            }
        }
        
        SmallClass sc = new SmallClass();
        sc.puse();
        SmallClass.suse();
}

public void showInfo2(){
        System.out.println("调用showInfo方法-------2");
        SmallClass.suse();  //会爆红 无法编译
}
```

基本和上面类似，因为是局部使用，所以甚至无法使用public，protected，private和static进行修饰。但是之前的问题仍然存在。没有定义static却可以写static方法，
而且还可以直接调用。不过这里作用域倒是符合预期的，无法在同一个class下的其他方法中调用。

```java
String a = "string a";
public void showInfo(){
        System.out.println("调用showInfo方法------1");
        final int b = 20;
        
        class SmallClass {
            public static void suse() {
                System.out.println("这是sc的suse " + a); //爆红，无法编译
                System.out.println("这是sc的suse " + b);
            }
        
            public void puse() {
                System.out.println("这是sc的suse " + a);
                System.out.println("这是sc的puse " + b);
            }
        }
}
```
这里要注意，所有方法中定义的变量必须使用final修饰，不然无法在局部内部类中直接使用。这里的原因我综合了不用人的观点，
感觉还是和java的栈堆的机制有关系。这里虽然看上去是直接访问方法中的变量，应该都在栈中。但是实际上，局部变量的调用应该是和方法捆绑在一起的，
也就是说当方法结束的时候，局部变量就会消失，但是同时堆中的数据不会立即消失。这样一来，虽然该变量已经没有了，但是堆依然在调取这个变量，
所以为了保证不会出现错误，就要加上final修饰符。如此就可以保证堆内存中直接储存变量的值而不是名字。

#### 静态内部类

静态内部类的规则完全不同于前面两种，基本上单纯地继承了static的基本规则。由于使用了static修饰，所以静态内部类应该看成外部类本身。
也就是说可以把它看成是外部类的一个静态代码块（只是类似，并不完全一样）。既然它属于外部类本身，那么它也无法直接访问外部类的非static变量，只能调用static变量。
但是因为静态内部类仍然是一个class所以在外部类中依旧可以直接使用类名或者实例化的方式去访问具体的变量和方法。这里没有把具体的代码放上来，
因为基本上没有观察到和预想特别不一样的地方。就只是做了简单的几次尝试。

#### 匿名内部类

基本上这种处理办法大多数情况下都被lambda取代了。我大概研究了一下具体的用法，也和lambda表达式非常的像。

```java
public static void main(String[] args) {
    Demo demo = new Demo() {
        @Override
        public void run() {
            System.out.println("这是一个匿名内部类的demo");
        }
    };
}
```

特别需要注意的就是虽然在形式上非常像lambda表达式，但是它的内部其实可以支持大部分类的特性，比如多态甚至不同的成员变量。

### 小结

实际上，JVM在编译所有类型的内部类时，还是把内部类当成了普通类来处理，但是为了实现内部类的效果，JVM又会使用一个指针，将内部类指向外部类。
也就是在代码中的OuterClass.this。基本上内部类的好处大多在于不需要单独开辟一个文件来实现一个类，而是即插即用，需要的时候写就可以。
但是这样做可能会导致程序中的重复代码稍多，所以要根据不同的需求来进行利用。实际上，现在很多项目都会使用lambda表达式来代替匿名内部类。
