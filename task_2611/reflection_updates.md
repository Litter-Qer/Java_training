# 关于异常机制的三个问题

## -XX:-OmitStackTraceInFastThrow 是啥意思？它会省略哪些异常的堆栈

这个部分我查了JVM的源码，太恐怖了（这个文件得有快5000行），根据网上的定位，我看的是github上面最新的源码。

```java
if (treat_throw_as_hot && method()->can_omit_stack_trace()) {
    // If the throw is local, we use a pre-existing instance and
    // punt on the backtrace.  This would lead to a missing backtrace
    // (a repeat of 4292742) if the backtrace object is ever asked
    // for its backtrace.
    // Fixing this remaining case of 4292742 requires some flavor of
    // escape analysis.  Leave that for the future.
    ciInstance* ex_obj = NULL;
    switch (reason) {
    case Deoptimization::Reason_null_check:
      ex_obj = env()->NullPointerException_instance();
      break;
    case Deoptimization::Reason_div0_check:
      ex_obj = env()->ArithmeticException_instance();
      break;
    case Deoptimization::Reason_range_check:
      ex_obj = env()->ArrayIndexOutOfBoundsException_instance();
      break;
    case Deoptimization::Reason_class_check:
      ex_obj = env()->ClassCastException_instance();
      break;
    case Deoptimization::Reason_array_check:
      ex_obj = env()->ArrayStoreException_instance();
      break;
    default:
      break;
    }
```
具体细节我也没看懂，但是从这个switch的情况看应该是只支持5中最常见的Exception形式。  
- NullPointerException  
- ArithmeticException  
- ArrayIndexOutOfBoundsException  
- ClassCastException  
- ArrayStoreException

OmitStackTraceInFastThrow实际上就是一个JVM排除一些重复操作，加快效率的指令。它的一个具体原理就是，如果某一个exception多次的被抛出，那么JVM会忽略它的stacktrace直接抛出这个异常。
这样可以快速抛出。但是问题在于如果我在测试中一直抛出这样的一个很范化的异常，我就无法直接精确定位到异常栈堆。也就是说实际上，虽然节约了时间，但是也造成一些小的麻烦。
这里第二个问题在于如果突然出现了一个完全新的异常，由于JVM还没有来得及优化，所以仍然会打印一段时间，这样也造成了一定程度上的浪费。

## 如何获取当前代码行数位置

当出现了一个异常之后，一般可以通过StackTrace来获取一个StackTraceElement数组。其中的每一个元素都是一个StackTraceElement对象。
StackTraceElement这个类实现了序列化并且是一个final类。观察源码会发现其中包含了一个getLineNumber的方法。只要在抛出，或者处理异常的时候调用即可。
当然这个类中也有很多其他的有用方法，比如fileName，packageName等等。也可以直接使用toString打印所有成员。
在Java 9之前一把使用getStackTrace,之后一般用StackWalker代替。我大概扫一下StackWalker的源码，基本结构和StackTraceElement差不多。
但是很多文章认为它是更安全，有效的方式。而且它们的共同点都是基于线程, 所以后续正式学习线程的时候我准备再仔细思考一下。

## 抛出大量异常，获取异常堆栈，输出日志对性能是否会有很大影响

其实这个问题可以换成两个问题来看，首先是获取异常堆栈，其次就是输出日志。获取异常栈堆就像前面提到的，基本就调用StackWalker即可。
这里的话，因为我没有一个高并发的环境，所以我就用junit Test模拟了两个小的测试（SimpleStackWalkerTest）。在只获取异常栈堆的情况下，跑完全程大概需要103ms
而把这些输出到日志中去，全程则需要1161ms左右。这里就能明显看出写入日志对系统性能的影响了。只获取栈堆可以说对系统的确有影响，而且确实会占用很多CPU，
但是比起写入日志可以说是小巫见大巫。所以无论如何，把大量异常直接抛出并且写入日志显然是不明智的。  
既然这样的话，应该如何解决这样的问题。我根据这三个问题想到了一个思路，首先肯定要保证StackTraceInFastThrow是开启的。然后在输出到日志的时候，
是否可以认为的忽略一些不太需要的异常。（这个部分我还不能完全确认，因为具体的方法我也不知道，但是理论上应该是可行的）或者说可以考虑只捕捉某些特别重要的异常，
而其他的都忽略掉。

# 使用反射获取内部类的研究

由于局部内部类主要存在于某个方法中，而且通过反射取得的意义不是很大，所以这里就省略了局部内部类的反射方法。 本次一共使用了三种不同的内部类，每一种都设置为了private。
毕竟都要使用反射了，就尽量尝试一些可以破坏封装的方式。

## 成员内部类 & 静态内部类

```java
Class clazz = OuterClass.class;
Constructor outCst = clazz.getDeclaredConstructor();
Object outObj = outCst.newInstance();

for (Class cls: classes) {
            String mod = Modifier.toString(cls.getModifiers());
            String name = cls.getName();
            Field field;
            String fVar = "";
            if(mod.contains("static")){
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
        }
```

以上的代码省略了所有打印的部分。和之前使用反射几乎是一样的，无论是获取成员还是静态内部类，都需要要先找到它们的外部类。但是它们的区别在于是否需要外部类对象。
从上面的代码可以很明显地看出，成员内部类需要外部类对象作为媒介，而静态内部类不需要。这里侧面证明了之前所说的静态类不过是一个类的属性，而成员内部类是一个对象的属性。
如此当我需要实例化成员内部类的时候，则必须使用一个外部类实例，因为成员内部类依附于外部类而存在。反之，当我需要一个静态内部类的对象时，直接找到构造器即可。
因为静态类并不受外部类对象管制，而是作为类这个抽象概念的附属。
