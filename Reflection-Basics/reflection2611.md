# 异常处理机制
java中的异常处理机制虽然很清晰，但是分支众多。这次的反思可能只谈到一些用的比较多的。

## 简单的异常处理

```java
public void divide(){
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter numerator: ");
    int nm = scanner.nextInt();
    System.out.print("Enter denominator: ");
    if(scanner.hasNextInt()){
        int dm = scanner.nextInt();
        if (dm==0){
            System.out.println("Denominator cannot be 0, safely exit...");
            System.exit(1);
        } else {
            System.out.println(MessageFormat.format(
                    "{0}/{1} = {2}",
                    nm, dm, nm / dm));
            System.out.println("Thanks");
        }
    } else {
        System.out.println("Denominator is not an integer, safely exit...");
        System.exit(1);
    }
}
```

上描述的例子并没有使用任何的throw或者try catch, 而是单纯的预判可能出现的错误并且使用类似于硬编码的形式来解决问题。同时也没有提供任何解决方案。
这里使用了System.exit()来模拟error code。  
这种方法也不能说完全的不好，我细想了一下，对于比较简单的异常处理其实使用这个反而更便捷，因为有可能的异常其实都已经提前知晓了。但是确实，使用这种方案如果有遗漏的异常依旧无法解决。
而且可能整体的代码一致性也得不到保障。

## 异常分类

Java中的异常体系可以说还是很清晰的。所有的异常都继承Throwable这个类，而具体异常又会被分为Error和Exception。下面我会分别研究这个两个异常。这里我们先看Throwable这个类。

### Throwable

为了深入交接java中的异常处理机制，搞懂throwable的原理是必要的。

#### 内部结构

首先throwable实现了序列化接口，保证了它本身可以直接被存储或者转移。这里的主要原因还是说throwable在大部分情况下需要被直接输出，方便程序员进行检测。
然后今天才发现了一个新的关于JVM的编译规则。如果一个类没有继承任何类，那么JVM就会自动给他分配成Object类的子类。（之前一直都疏忽了）
同时它也被error和exception这两个类继承。error一般指的是java或者JVM内部的异常信息，而exception更多是程序本身的一些异常（比如设计缺陷之类的）。

#### 运行逻辑

在throwable的源码中可以看到这样的语句

```java
private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];
private StackTraceElement[] stackTrace = UNASSIGNED_STACK;
```

这里实际上解释了throwable是如何追踪异常的。throwable内部通过使用```getOurStackTrace```来获取栈上的异常信息，并遍历所有的异常信息。

#### 构造器

throwable的构造器并不复杂，主要影响构造的是这两个参数————```detailMessage```,```cause```。  

1. ```detailMessage```
这个变量主要记录了throwable的信息，可以是人为定义的信息，比如第一个例子中的输出。一般来说这个信息是程序员读懂异常的关键。

2. ```cause```
这个变量主要是记录异常出现的原因，这里的原因通常指的是其他的异常或者null。

在构造器中，一般把以上的两种变量放入就足够了，但是java 7之后又提供了一个新的构造器，其中可以调整Suppression和StackTrace的状态。不过目前还没有使用到涉及这两个功能，所以暂时没研究。

#### 方法

基本上throwable中的方法都是一些打印和获取的工作，没有什么特别的地方。相对值得一提的就是initCause这个方法，它主要初始化了一个throwable的起因为定值。

### Error

Error描述了java运行时系统的内部错误和资源耗尽错误。也就是说，实际上error不应该存在于程序。而且如果出现error那么java也时无法处理的。
error的源码就是throwable的再利用。基本上就是把throwable所有的构造器继承了一遍，向下转置成了Error类型。目前基本没怎么碰上过，除了测动态数组那次因为JVM初始内存的问题，
调整过一次。

### Exception

Exception就是日常会出现的异常了。它也主要分为两类————IOException和RuntimeException。它的基本源码和Error完全一致，就是向下转置成Exception，所以就略过了。

#### IOException

顾名思义，就是输入输出时会出现的异常。一般表现在编译阶段就会被要求解决。常见的有IO，SQL异常，也就是由语法或者外部因素导致的异常。
基本上这类异常应该在程序运行前就被处理好，事实上javac也有类似的检测机制，强制要求解决。

2. FileNotFoundException 文件找不到（文件名或者path不对）
3. ClassNotFoundException 类找不到 （类不存在或者包没有导入）
4. SQLException （SQL语句不对，数据库链接失败等）
5. NoSuchFieldException 文件不存在
6. NoSuchMethodException 方法不存在

以上的这些异常都属于IOException，而javac在编写程序阶段就会提示开发者必须处理或者抛出这些异常。

#### RuntimeException

这种类型的异常基本上写程序一定会碰上，有时候可能就是代码里的bug或者是程序设计时的问题。常见的种类有很多很多，
比如NullPointerException,IndexOutOfBoundException等。此类异常无法被javac检查出来，所以需要开发者自己进行处理。

## 异常关键字

基本上java有五种异常关键字，其中try，catch，finally一般归为一类，而throw和throws可以归为一类。

- try用于监听异常，一般是把可能出现异常的代码放入try代码块中。如果出现了异常就抛出或者使用catch来处理。
- catch用于捕获和处理异常，一般是把在try代码块出现的异常找到并且在catch的代码块来解决这个问题。同一个try可以对应很多个不同的catch。
- finally由于回收在try和catch中使用的资源，因为finally代码块无论什么情况一定会被执行。也就是说如果try和catch中出现了return或者throw，但是finally中也有的话，
那么finally就会直接打断前面的return。这里下面给出一个具体的例子

```java
try {
    System.out.println(MessageFormat.format(
    "{0}/{1} = {2}",
    nm, dm, nm / dm));
} catch (Exception e) {
    System.out.println("Exception happened");
    System.out.println(e.getMessage());
    throw e;
} finally {
    System.out.println("This is a finally block");
}
```

故意输入10和0，触发ArithmeticException。打印的结果如下

```shell
Exception happened
/ by zero
This is a finally block
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at SimpleMath.divide2(SimpleMath.java:21)
	at SimpleMath.main(SimpleMath.java:8)
```

这里会发现由于finally必须优先于return和throw执行，所以输出中先出现了exception message然后在打印finally里的东西，最终抛出了这个异常。
当然反编译结果也可以证明这个过程。

```shell
112 getstatic #24 <java/lang/System.out : Ljava/io/PrintStream;>
115 ldc #59 <This is a finally block>
117 invokevirtual #56 <java/io/PrintStream.println : (Ljava/lang/String;)V>
120 aload 5
122 athrow
123 getstatic #24 <java/lang/System.out : Ljava/io/PrintStream;>
126 ldc #69 <Thanks>
128 invokevirtual #56 <java/io/PrintStream.println : (Ljava/lang/String;)V>
131 return
```

- throw 和 throws

throw和throws的用法比较简单。throw就是直接抛出一个异常，其实就是把异常的信息打印到console。throws的话其实是抛出可能会有的异常，一般放在方法签名中。
其实使用英文来理解会比较简单，throws就是方法名的谓语，表示这个方法可能会抛出一些异常（加s表示第三人称单数）。throw就是我自己抛出一个已经发现的异常，
直接在后面跟上异常的实例就好了。  
这里比较常见的就是throws的继承问题，如果子方法（被某一个其他方法调用的方法）需要抛出异常的话，则父方法也必须抛出异常。一般来说抛出异常还是比较少见的，
因为如果我都已经发现异常了，为什么不想办法直接解决呢？这里就又出现了新的问题，如果我的程序中出现了不同于java已经定义好的异常的话，我该如果去做。

### 关键的使用

基础的try-catch-finally已在上文中展示过了。下面会使用一种可能在多线程中运用较多的使用方式try-finally。因为还没有正式进入多线程的学习，所以这里只是做了一个小的例子感受一下。

```java
ReentrantLock lock = new ReentrantLock();
try {
    System.out.println("Something needs to be locked");
} finally {
    lock.unlock();
}
```

上面的代码无疑会直接抛出异常，但是这里给了一个很好的提示。在多线程的任务中，如果需要确保某段数据不会被改写，那么使用这种try finally的方式是可以解决的。
因为只有try中不抛出任何异常的情况下，才会解锁，并且这里还保证了一定会解锁。但是这种并不代表完全解决了多线程的问题，因为如果finally中的代码抛出异常怎么办？
这里java 7之后提供了一种解决方案——AutoCloseable。这个一般结合try-with-resource使用。具体实现可以参考一下例子

```java
try (Scanner scanner = new Scanner(new FileInputStream("a"), StandardCharsets.UTF_8)) {
    System.out.println("Something happens here.");
} catch(Exception e) {
    System.out.println("Exception handling here.");
}
```

由于Scanner实现了AutoCloseable这个接口，所以Scanner会自动关闭。并且如果scanner.close()出现了异常，那么则会被抑制。当系统抛出异常的时候，
会直接抛出原有的异常，比如FileNotFoundException。而不会变成IOException。这样的一个好处就是，不会是得异常变乱。比如也许问题本来是IO的，由于抛出的时候不准确，
最后导致，一直在想办法解决不想关的异常。当然如果需要看到被抑制的异常，只需要使用```getSuppressed```即可。

## 自定义异常

实际上由于java的单一继承机制，导致自定义异常变得无比容易。我只需要定位我自建的异常大致属于哪一类，就可以通过继承对应的父类来实现自定义。
一般情况下，遵循异常的构造规则会在自定义的异常里面放入两个不同构造器——无参和msg的构造器。当然我们也可能改写或者加入一些自己需要的方法来处理或者解释这个异常。
