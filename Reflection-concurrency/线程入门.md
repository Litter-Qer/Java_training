# 进程和线程

一个程序由两个部分组成——指令和数据。一般情况下，读写数据时，需要把指令加载到CPU，数据加载到内存。

## 进程

进程实际上就是用来加载指令，管理内存和输入输出的。Oracle官方对进程是这样描述的

```text
A process has a self-contained execution environment. A process generally has a complete, private set of basic run-time resources; in particular, each process has its own memory space.
```

java中的进程一般表示一个独立的，拥有完整私有资源的一段程序。我自己的理解就是等于进程可以占用CPU和内存的一个部分，并且只供自己使用。进一步的用java的语言描述，
可以把进程看作一个实例。那么同一个时间段我可以开启很多个一样的实例，又或者只能由一个一样的实例。对应到计算机就是不同的内存位置和CPU的核心。

## 线程

```text
Threads are sometimes called lightweight processes. Both processes and threads provide an execution environment, but creating a new thread requires fewer resources than creating a new process.
Threads exist within a process — every process has at least one. Threads share the process's resources, including memory and open files. This makes for efficient, but potentially problematic, communication.
```

java中的线程其实就是进程的附属，可叫做轻量级进程。它也提供了一个执行环境，但是它会和其他线程共享数据，并且很容易通信。由于这种通信反而导致了使用线程可能会带来很多问题。但是无疑线程的使用可以更合理的分配资源。
其实简单的来看线程，感觉就像是把一个进程分了无数个小的进程，每一个执行一个小的任务，然后最终完成大的任务。不一定恰当的比喻，可以把进程看成是老师，而线程其实就是学生，学生互通信息，协作完成各自独立的任务，最终把老师布置的大任务完成。

## 小结

进程一般作为资源分配的单位，而线程则是资源调度的单位。

# 并行和并发

操作系统中一般会包含一个任务调度器，而任务调度器会把CPU的使用权调度不同的线程使用。

## 并发

实际上就是一个CPU核心处理多个任务，但是不必同时。比如说我在写这个文件的时候，中途去吃了个饭，又回来接着写。我其实处理了两件事，但是我并没有同时做。这证明了我拥有处理不同事情的能力，但不代表我可以同时做。

## 并行

同时处理多个任务的能力。比如我一边打电话，一边看柯南，还一边吃饭。实际上我在同时处理三件事。既然这样的话，如果我可以并行的做事的话，那么我一定具备并发的能力。所以这样看来并行似乎是并发的子集。

## 小结

虽然在实际应用的时候，我们感受到的是多个任务在同时进行（一个CPU的情况下），但是实际上是CPU中，是不断切换线程来达到模拟的效果。不同的操作系统会使用不同的调度方式来解决问题。
比如windows，目前还是非实时的操作系统，也就说利用时间片控制不同的进程。而一个时间片非常小，小到人类无法感知，因此看上去是并行的。但是对于多核的CPU这个结论就不一定适用了，因为CPU的调度可能会是一个任务一个核心，也可能多个核心协同使用。
大部分情况下，微观上是串行运行的，而宏观上是并行运行的。所以我们会把这种轮流使用的方式叫做并发也就是concurrency。个人角度来说，一心无法二用，实际上真正并行必须得有多个CPU。

# 同步与异步

## 同步

简单来说就是需要等待结果返回，才能继续运行。在多线程语境下，一般表示线程的步调一致。

## 异步

正好相反，不需等待结果返回就继续运行。

## 小节

显然异步操作更适合多线程并发的情况，因为我无需等待一个特定的线程结束工作就可以开始我的其它工作。但是当某一个线程需要特定的值的时候，同步调用会更好。
我想到的例子就是，假设我在写一个抢票的逻辑。其实每个抢票的人就是一个线程，它们都在同时完成抢票这个进程。为了保证公平性，我就需要使的票的总数是同步的。其中一种解法就是使用同步的方式，
每次有人抢到票就相当于一个单独的线程。那么所有的其他线程必须等这个线程完成以后，才可以继续抢夺这个线程的使用权。

# 源码初探

## fields

```java

private volatile String name;   // 线程名
private int priority;   // 优先级
private boolean daemon=false;   // 线程保护 暂时理解为后台线程
private volatile boolean interrupted;   // 是否被中断 自然死亡或者被其他线程打断

private boolean stillborn=false; // JVM使用，具体等后面结合JVM在详细了解
private long eetop; // JVM使用

private Runnable target;    // 需要执行的任务，其实就是run这个方法
private ThreadGroup group; // 线程组，应该和线程池不同，后面也会细看
private ClassLoader contextClassLoader; // 上下文的类加载器，上下文就是切换线程的这个行为
private static int threadInitNumber; // 线程的默认名字一般是Thread-threadNumbe

// 线程的本地局部变量，这个也要到应用的时候才能详细看懂        
ThreadLocal.ThreadLocalMap threadLocals=null;
ThreadLocal.ThreadLocalMap inheritableThreadLocals=null;

private final long stackSize; // 初始化线程后的栈的大小，由JVM进行分配

private final long tid; // 线程的id，唯一且不可变。非常重要，定位一个线程最靠谱的方式。
private static long threadSeqNumber; // 线程初始化后的id，就是按照顺序排下来的
private volatile int threadStatus;  // 线程的状态，使用数字表示的

volatile Object parkBlocker; //给lockpark使用的，目前还不了解
private volatile Interruptible blocker; // 同上，是一个对象
private final Object blockerLock=new Object();  // 同上，是一个锁

// 线程的优先级，为了避免magic number
public static final int MIN_PRIORITY=1;
public static final int NORM_PRIORITY=5;
public static final int MAX_PRIORITY=10;
```

## 内部类

### Thread.State

直接看源码和Oracle的官方API解析。实际上State就是一个静态内部类，用来表示一个线程的状态。一般来说一个线程的状态分为6个部分——new，runnable,blocked,waiting,timed
waiting,terminated。
Java通过枚举限定了State只能从这6中选择，所以它们也成为线程的生命周期。下面是每一个状态的初步解释

| Name          | Explanation                                  |
|---------------|----------------------------------------------|
| new           | 初始化一个线程，并且此线程还未开始执行任何任务。（就看成是初始化             |
| runnable      | 可运行状态。只要这个线程是可运行（或者正在运行）那么它就属于runnable       |
| blocked       | 线程阻塞。等待一个监视器锁，然后进入一个同步块或者重新进入一个同步块。一般是由于锁导致的 |
| waiting       | 等待。等待别的线程完成一系列的操作，也有可能是被别的线程打断的              |
| timed_waiting | 时间等待。等到特定的时间后即可自行返回，无需等待别的线程                 |
| terminated    | 线程执行完毕，或者被别的线程打断后抛出异常                        |

其他也没啥特别重点的了，基本上都是枚举的实现方法。

### UncaughtExceptionHandler

就是一个异常接口，用来捕获未捕获的异常。

## 构造器

一共共有8个构造器。其实只需要看最长的那个就好了，其他的都是相同的。

```java
 private Thread(ThreadGroup g, Runnable target, String name,
                   long stackSize, AccessControlContext acc,
                   boolean inheritThreadLocals) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;

        // 获取当前的线程，其实这里比较tricky，当前的线程应该是即将要创建的这个线程的父线程。因为运行constructor也需要一个线程。
        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        if (g == null) {
            // 从安全管理手中得到线程组，因为没有限定线程组，所以就会直接把它归到当前的线程组中
            if (security != null) {
                g = security.getThreadGroup();
            }
            
            // 如果安全管理没有特别好的选择，那么就直接归为当前线程组
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }

        // sanity check 检查一下线程组是否存在或者是否有权限
        g.checkAccess();

        // 权限检查，主要限制了子线程修改一些方法
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(
                        SecurityConstants.SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }
        
        // 和JVM的垃圾回收机制相关，把一个未启动的线程放入线程组
        g.addUnstarted();

        // 参数赋值，没有什么特别复杂的
        this.group = g;
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority); // 设置一下线程的优先级
        if (inheritThreadLocals && parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        this.stackSize = stackSize; // 初始话一下JVM中的栈的大小

        /* Set thread ID */
        this.tid = nextThreadID();  // 线程的id，按照顺序排下来的
    }
```

基本上构造器都是调用的这个，但是这个构造器在JDK 17被标注为removal。其实主要是那个AccessControlContext要被弃用了。主要是由于安全管理要被弃用了，
具体的弃用原因我还没看。这里只是初探，后面也会在分析一次。

## APIs

因为是初探，所以我只谈论了几个常用的。

### start

开启线程的方式，主要是通过与JVM的交互实现的。

```java
public synchronized void start() {
    if (threadStatus != 0)  // 检查线程是否已经开启
        throw new IllegalThreadStateException();

    // 告诉线程组这个线程准备起飞，给线程组中的数量减一
    group.add(this);

    boolean started = false;
    // 请求资源，尝试起飞
    try {
        start0();
        started = true;
    } finally {
        try {
            // 起飞失败，告诉线程组这个线程飞不了，把它重新加回到unstarted，等待被垃圾回收
            if (!started) {
                group.threadStartFailed(this);
            }
        } catch (Throwable ignore) {
            /* do nothing. If start0 threw a Throwable then
              it will be passed up the call stack */
        }
    }
}
```

### interrupt

中断一个线程的方式

```java
public void interrupt() {
    if (this != Thread.currentThread()) {
        checkAccess();  // 检查权限，除非是我自己打断自己

        // thread may be blocked in an I/O operation
        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupted = true;
                interrupt0();  // inform VM of interrupt
                b.interrupt(this);
                return;
            }
        }
    }
    interrupted = true;
    
    // 告诉JVM线程已经中断了
    interrupt0();
}
```

源码中给出了一下的情况
- 除非线程自己中断自己，不然必须做权限检查
- 如果线程是被wait，sleep，join唤醒的话，则直接抛出异常
- 如果线程是被IO阻塞的话，则直接抛出‘被中断异常’
- 如果线程是被NIO阻塞的话，则会通过并且返回一个非零值（这里还没有仔细研究）
- 如果以上都不适用，则会直接通过（中断成功）

### sleep

细细观察会发现和java object包里的wait非常相似，都是直接调用native方法。这里要注意的就是sleep是让现在运行的这个线程直接休眠。
不过细品一下会发现其实sleep并不会释放监视器锁，也就是说sleep的线程任然拥有对监视器锁的控制。而wait则会直接释放锁。

### join

```java
public final synchronized void join(final long millis) throws InterruptedException {
    if (millis > 0) {
        if (isAlive()) {    // 线程属于runnable，blocked，waiting,timed_waiting状态
            final long startTime = System.nanoTime();
            long delay = millis;
            do {
                wait(delay);
            } while (isAlive() && (delay = millis -
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)) > 0);
        }
    } else if (millis == 0) {
        while (isAlive()) {
            wait(0);    // 一直等待，直到获得权限
        }
    } else {
        throw new IllegalArgumentException("timeout value is negative");
    }
}
```

这里和java 8中不太一样，虽然结果是一样的，但是实现的时候删除了没有意义的行数，比如long，base等。我也是学习了一下它这个简化的方式，感觉就为了减少行数。
然后join这个方法本身有个重点就是因为使用的wait方法，所以会释放监视器锁。别的和sleep其实可以算是一致的。

### yield

告诉调度器，我可以主动放弃自己的CPU占用，让给别的线程。但是调度器可以选择直接无视，源码中也表示不推荐使用。主要用于debug。因为这个放弃占用是自愿的并且结果也无法直接预知，所以不推荐。
这里还有一个小细节就是yield并不会放弃已占用的资源，它只是释放了CPU占用，并没有释放内存占用。

### 其他方法

大多类似getter和setter，感觉直接使用即可，没有什么需要特别关注的地方。如果我后续发现了什么特别有意思的，我在补充。

### 弃用方法

这个部分很多方法视频里面也介绍了，但是源码中都是说最好别用，所以我就大概看了一下，然后稍微试了试，没有过多了解。

# 实践部分

## 简单的开启和关闭线程

```java
MyThread testThread = new MyThread();
testThread.start();
```

这里我就尝试了一下线程的开启，然后看看了一下反编译的结果。

```oracle
 0 new #24 <MyThread>
 3 dup
 4 invokespecial #26 <MyThread.<init> : ()V>
 7 astore_0
 8 aload_0
 9 invokevirtual #27 <MyThread.start : ()V>
12 return
```

JVM实际上还是把线程当作了一个类来处理，就直接初始化了MyThread类，然后建了一个对象。给对象赋值，分配储存空间。
线程如果执行完所有的工作就会自动关闭，所以基本不需要特别使用exit来关闭线程。还有需要注意的就是要使用start来开启线程而不是run，因为run并不是开启一个并发的线程，而是一条执行路径。
使用start就是直接并行交替执行，多条执行路径。

## Interrupt测试

```java
public static void interruptTest(){
    User user = new User();
    Thread thread1 = new Thread(() -> {
        synchronized (user) {
            try {
                user.wait();
                System.out.println("user gets it！");
            } catch (InterruptedException e) {
                System.out.println("Interrupt gets the lock! ");
                Thread.currentThread().interrupt();
            }
        }
    });

    thread1.start();
    new Thread(() -> {
        synchronized (user) {
            thread1.interrupt();
            user.notify();
        }
    }).start();
}
```

这个例子是根据网上的一些方式改进的。这里尝试用lambda表达式的方式来定义线程。这里使用了synchronized关键字，这个关键字的部分我准备后面具体分析一下。
由于被中断后的线程需要复位，所以再次中断了它。以上代码出现的结果每次都不同，因为调度器也无法预知谁会抢到使用权。

```shell
Interrupt gets the lock! 
user gets it!
Interrupt gets the lock! 
user gets it!
user gets it!
```

## join 测试

```java
public static void joinTest() {
    Thread t1 = new Thread(() -> {
        log.error("Starting t1 (self-defined name)");
        try {
            log.error("t1 首次休眠 5s");
            Thread.sleep(5000);
            log.error("t1 开始第二次休眠 1s");
            Thread.sleep(1000); // 休息1秒
            log.error("t1 结束休眠 1s");
        } catch (InterruptedException e) {
            System.out.println("t1 gets interrupted");
        }
        log.error("结束t1");
    });

    Thread t2 = new Thread(() -> {
        log.error("Starting t2 (self-defined name)");
        try {
            log.error("t1 开始join");
            t1.join(); // 阻塞t2让它必须等待t1结束
        } catch (InterruptedException e) {
            System.out.println("t2被中断");
        }
        log.error("结束t2");
    });
    t1.start();    // 开启线程
    t2.start();
}
```

```shell
16:41:12 [Thread-0] c.mainTest - Starting t1 (self-defined name)
16:41:12 [Thread-1] c.mainTest - Starting t2 (self-defined name)
16:41:12 [Thread-0] c.mainTest - t1 首次休眠 5s
16:41:12 [Thread-1] c.mainTest - t1 开始join
16:41:17 [Thread-0] c.mainTest - t1 开始第二次休眠 1s
16:41:18 [Thread-0] c.mainTest - t1 结束休眠 1s
16:41:18 [Thread-0] c.mainTest - 结束t1
16:41:18 [Thread-1] c.mainTest - 结束t2
```

这里测试了join()对结果的一个影响，当不使用任何参数的时候，join会一直等待线程完成。这里为了排除t1可能早就运行完成的影响，刻意让t1休眠两次保证一定会完成。
很明显，t2是会一直等待t1完成所有的任务才继续运行。

# 初探总结

线程是一个非常复杂的部分，不光是API的使用，更多的是线程中的生命周期，每个线程之间的关系，JVM的参数以及内存分配。这些不仅在设计的应该考量，在后期维护的时候更是需要思考。所以本次初探只是从最基础的原理开始，
先对thread有一个概念，并且对源码中的一些部分进行理解。这次基本上就是认识了线程，并且知道了CPU和JVM对线程大概是一个什么态度。包括其中一些简单API的使用。那么下一项就是开始完成简单的多线程设计，并且深入理解不同的关键字的影响。

