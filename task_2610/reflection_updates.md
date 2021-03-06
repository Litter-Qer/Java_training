## 接口

### 接口的属性

所有接口的fields全部默认为public static final。尝试在接口的属性前面加入public static final，IDEA会自动暗化修饰符。
并且尝试直接调用属性，可以成功打印。值得注意地是所有的成员变量都必须显示初始化。这里比较好理解，因为所有的变量都是final的，
而接口本身又不能实例化，没有显式初始化等于这个属性没有值。

### 接口中的方法

和属性类似，接口中的方法全部都是默认为public abstract。无法使用任何其他修饰进行修饰。由于接口不能被实例化，所以也不存在构造方法。
但是由于接口可以被不同的类实现，所以实现类可以通过构造器来间接实现一个接口。这个部分后面会通过反编译的结果来进行分析，接口到底有没有被实例化。

### 接口的继承与实现

接口只能被接口继承，而且不同于类的继承，接口可以被多个接口继承，也可以继承多个接口。 同时，接口也可以被多个不同的类实现。
但是接口不能实现一个接口。 这里的逻辑实际上简化一下就是，接口由于本身就是抽象的，所以它无法实现任何接口。但是类可以实例化，于是可以直接实现接口。
由于接口规定了实现它所需要的所有能力（方法），所以一个类必须实现所有方法才可以实现一个接口。一个简单的例子就是，充电插头，我可以使用很多不同的数据线，
但是规格必须能配上这个插头，不然就无法连接。这里还有一个注意点就是，实现类必须实现接口的所有功能，但是抽象类不需要。因为抽象类本身也是抽象的，
它虽然在代码中是实现接口(implements)，但是其实抽象类是由它的实现类来完成实现接口的。
> 1. 这里有意思的点是，抽象类由于不需要实现所有的功能，反而不需要在class中声明任何接口方法。因为实现类不能实现一个抽象类，只能继承。
     > 所以这里java的设计，我个人觉得还挺精妙的。因为这样保证类与接口直接的关系永远是实现，而类与类之间永远是继承，接口与接口永远是继承。
> 2. 不同接口中所有的属性也彼此独立，我尝试了给三个不同的接口使用一样的值和变量名name，三个接口是一一继承的关系，然后使用了一个抽象类实现了底层的接口，
     > 最后用实现类继承了抽象类。结果在实现类中打印所有的name的时候，发现所有的name都没有被覆盖。
> 3. 接口在java 8之后允许加入静态方法。这里有一个思考就是，既然我希望接口是一个抽象的东西，为什么我要在接口中接入一个具体方法，然后具体实现这个方法。
     > 个人感觉意义不大，可能是因为需要完成某些操作。
> 4. 接口在java 9之后甚至允许加入private method，并且可以直接在接口中实现。
> 5. 接口的多继承算是破解了java中单一继承的规则。

### 接口中的默认方法

从java 8之后，接口中可以通过写一个default方法来实现一个具体的方法。并且由于这个方法被实现了，所以实现类可以选择不重写这个方法。
所有实现或者继承这个接口的类或者接口都可以调用这个方法。和前面提到的private方法一样，在接口内部，default方法还可以调用private方法。

### 接口中的运行顺序

由于接口不能写入静态块，所以直接测试接口的顺序意义也不大，而且接口也不能被实例化，所以这个顺序问题算是一个小的分支。

```shell
 0 new #7 <interfaces/MyInterfaceImpl>
 3 dup
 4 invokespecial #9 <interfaces/MyInterfaceImpl.<init> : ()V>
 7 astore_1
 8 aload_1
 9 invokevirtual #10 <interfaces/MyAbstractClass.test : ()V>
12 aload_1
13 iconst_1
14 invokevirtual #13 <interfaces/MyAbstractClass.test2 : (I)V>
17 return
```

这个是实例化一个实现类得到的反编译结果，可以发现和预想的一样，由于接口不会被实例化，所以在具体构造一个实例的时候，并不会调用到接口，基本等于没有参与。

### 小节

接口和抽象类都提供了一种很好的程序设计思路。不同与抽象类，接口更为灵活，无论是多继承还是不需要实现具体类都给接口提供了很多不同的用法。
因为接口只需要规定具备能力，就可以通过很多不同的类去实现。这样做的好处是有效降低了耦合度，代码与代码之间不会过分共享某些变量，而是可以随意切换和插拔。
举一个简单列子，如果我看到某个餐厅（实例），在进店之前我并不知道它是卖什么的（方法），可如果它是某一品牌（接口）的分店，我就可以直接确认我要不要吃这个，
因为它一定具备某个招牌菜或者服务（实现接口）。当运用到编程中，就等于我不要在去考虑某一个类到底是干什么的，只要知道他实现了某个接口，我就可以判断它
一定可以干什么。那么这样不条理清晰而且容易理解。

## Comparable

Comparable我个人的理解就是一个开放的接口，使得非primitive类型的object变量可以相互比较。观察它的一个源码会发现，其实comparable接口只存在一个
方法就是compareTo。这个方法的输入为一个对象，输出为一个integer。具体的重写方法需要注意一下几点
> - 由于输入是Object，所以重写的时候要进行类型转换。一般如果是在一个类的内部改写，就直接向下转置即可。
> - 由于compareTo方法只有一个且参数固定，所以这个方法在一个具体的类中不会出现多态。可以看成唯一的存在。
> - 在重写方法的时候，需要自己定义比较的内容并且输出一个integer，这个输出的integer必须满足三个条件：
> - 输出正，当自己大于外部object
> - 输出0，当自己等于外部object
> - 输出负，当自己小于外部object
> - 这里所谓的大小都是通过认为的定义，比如其中的某一个属性，或者地址之类的。

所有实现了Comparable的类都默认可比，也就可以排序。这里就是comparable的字面意思，可比。而且由于它是在Collection内部实现的排序
所以可以通过Collections.sort()实现排序。

## Comparator

和comparable最根本的区别就是位置不一样，comparator是在Collections外部实现的排序，属于java.util包。
观察源码会发现comparator底下主要存在的两个需要实现的方法：```compare```,```equals```。这两个方法的实现基本和之前一致，
compare需要保证输出一个integer，基本逻辑和compareTo一样。equals则是输出一个Boolean，需要自行设计逻辑。<br>
通过以上的定义会发现既然我都可以用comparable来实现sort了为什么要还需要comparator呢？<br>
这里我个人觉得原因还是在内部改动的问题上。因为comparable虽然可以对比，但是必须保证类是可以访问并且修改的。如果我使用一个外部的
API，且无法修改类的内容的话，比较器就提供了一个完美的解决方案。我可以通过加入比较器的方案来进行排序，而不需要类进行自比。
我想到的例子是：

- comparable可以看成是自比用的，比如钱本身可以对比价值。也就是类中属性的对比。
- comparator则是认为的对比，比如两首歌，认为的认定谁好谁坏这样。更多情况下是主观对比，根据需求进行修改。

## 关于comparator的补充

由于comparator被定义为了函数式方法，所以其实真正需要实现的只有compare这一个抽象方法。但是因为大部分情况下实现equals也会比较有用，
所以一般也会选择实现。

## lambda 表达式

关于函数式编程我还是有一定了解的，刚学完基础的prolog和haskell，也用这两个写过两个小的项目。Java中的一个函数式编程我个人的理解更像是为了简化编程，
其实可读性反而没有传统的函数式语言可读。在Java中函数式编程，实质上就是传递一个代码块。

### 核心原则

可推导则可省略 <br>
当我们具体实现某一个接口的方法的时候，其实我们并不是很关心它的一个对象，反而我们更关注对数据进行了什么样的操作。
因此我们完全可以通过某种方式省略对象，直接对数据进行操作，也就是所谓的把方法当作参数。

### 基本格式

```shell
(paramters)->{
  expression1;
  expression2;
  ...
 };
```

一般可替换成代码块的接口必须是函数式接口，由```@FunctionalInterface```注释来声明。并且一般一个函数式接口只有一个方法。
它的实现类也必须只实现这个一个方法。如果以上条件都满足，则可以通过lambda表达式的办法来实例化一个实现类。并且直接使用。不过源码中也提到，
如果不注解，compiler也会自动把符合的interface作为一个函数式接口来使用。注释了的话会更严格一点，如果写的不对则会直接报错。

### 反编译结果

```shell
// 通过实例化的方式
 0 new #7 <interfaces/LambdaImpl>
 3 dup
 4 invokespecial #9 <interfaces/LambdaImpl.<init> : ()V>
 7 astore_1
 8 aload_1
 9 invokevirtual #10 <interfaces/LambdaImpl.compare : ()V>

// lambda表达式的方式
 0 invokedynamic #7 <compare, BootstrapMethods #0>
 5 astore_1
 6 aload_1
 7 invokeinterface #11 <interfaces/LambdaInterface.compare : ()V> count 1
12 return
```

非常明显地，实例化的方式需要先通过new来获取一个实例。然后在通过```invokespecial```在编译时期就确定要调用init，
再通过```invokevirtual```动态调用compare。<br>
如果使用lambda就会发现。实际上，我们并没有实例化一个实现类，相反的java告诉JVM使用```invokedynamic```来进行动态语言的调用，
也就是直接调用某一个调用点所连接的语句。那么我们虽然没有在这里看到一个实例化的结果，但是JVM会不会在后台自动实例化了呢？
这里继续查看反编译文件，发现了下面这段代码

```shell
0 getstatic #20 <java/lang/System.out : Ljava/io/PrintStream;>
3 ldc #34 <通过lambda表达式实现的compare>
5 invokevirtual #28 <java/io/PrintStream.println : (Ljava/lang/String;)V>
8 return
```

的确，JVM会在后台自动把lambda表达式中的代码编译，但是并没有实例化，这里其实就是直接打开了Stream。如此就可以证明，使用lambda表达式的便捷性，
因为没有实例化，所以在内存中也不需要分配很大的空间，而且现在反编译结果中，行数也相对少一些。

### 参数与返回值

实际上写法没有什么太大的区别，要注意的就是如果又返回值则需要定义return的对象。

### 一些限制

1. 已经实例化的对象无法在通过lambda表达式改写方法。
2. 可以直接改写非实例化的对象（这里比较有意思，因为通过lambda表达式，实际编译中，JVM是指向了某一个调用点，所以通给非实例化的对象二次改写
   lambda表达式，可以直接运行）具体参考下面的代码

```shell
// 通过lambda表达式实现的compare
LambdaInterface lpl1 = ()->{
  System.out.println("通过lambda表达式实现的compare");
};
lpl1.compare();

// 通过直接赋值的方式来改写compare
lpl1 = ()->{
  System.out.println("通过lambda表达式实现的compare --- 2");
};
lpl1.compare();
```

3. 只有一个参数的时候，可以省略小括号。
4. 方法体只有一句话的时候可以省略大括号。
5. 如果返回值类型可以确定（也就是只有一个表达式）。那么可以省略return
6. lambda表达式中的变量必须为一个常量

## 方法调用

### object::instanceMethod

直接调用对象的实例方法。还是和之前一样要注意接口最好是注解一下，然后只能有一个method。
下面就是具体的使用方法，其实这种使用方式就是相当于简化了一个本来复杂的实现过程，在本次任务的代码可能不能完全体现，
但是如果结合java的一些特性比如stream，foreach之类的，就可以很优雅的解决一些问题。

```shell
Student a = new Student();
// 使用object::instanceMethod 来简写
MyObjectSetName myObjectSetName2 = a::setName;
myObjectSetName2.set("c");
System.out.println(a.getName());
```

本次实现中可以看到，我可以通过创建不同的MyObjectSetName接口的lambda对象来进行不同的操作。

### Class::staticMethod

和方法调用的名字一致，这里实际上就是尝试直接通过lambda调用静态方法。主要需要注意的就是在类里面要规定静态方法，不然就无法调用。

### Class::instanceMethod

这种方式的引用可以说是比较不一样的，因为对于java来说如果想调用非静态方法，则必须实例化一个对象。然而这里通过lambda表达式，
就可以在没有对象的情况下直接调用非静态方法，这样做的好处也比较明显，通过某一个特殊的接口，我就可以设计一套流程，在我需要对某个特定对象
进行操作的时候，直接调用接口的方法就可以实现结果。

```shell
Student a = new Student();
MyClassSetName myClassSetName = Student::setName;
myClassSetName.set(a,"abc");
System.out.println(a.getName());
```

但是在编写这个的时候也需要注意一些细节。这里由于是直接调用非静态方法，其实最终还是需要一个实例化对象的。那么java提供一种不需要把
对象写在lambda表达式，但是却可以作为的参数的方式，这种方式类似于隐式参数。在规定接口方法的时候，要注意第一个参数必须是想要传入的对象，
然后后面的参数为想要处理的值。在写lambda表达式的时候，由于对象会被隐式的传入方法中（类似于this），所以直接声明调用的方法。
但是最终使用的时候，仍然需要声明要修改的对象。

### 一些特殊的用法

既然一个lambda表达式既可以使用static又可以使用non-static那么是否我们也可以通过一些方法来直接调用构造器呢。
答案显然是可以的

```shell
MyConstructor stuNew = Student::new;
Student a = stuNew.StudentNew();
System.out.println(a);
```

当然这里比较有意思的是虽然我使用的是无参的构造器，但是即便要使用有参的构造器，lambda表达式并不会改变。只需在接口方法中定义参数就好了。
扩展一下，既然都可以直接调用构造器，那么直接使用this::method当然也是可以的。不过要记得和之前一样在接口方法中定义传入的对象为参数。

### 常用的接口

JDK真的太良心了！！！！居然有提供一大堆常用的接口，我粗略过了一下大概有10个左右，基本上就是已经帮我们写好了方法，规定了参数数量和返回值。
直接用lambda实现即可。感觉最常用的就是Runnable, Function, Predicate这几个。




