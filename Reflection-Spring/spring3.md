# Aspect Oriented Programming 面向切面编程

## AOP 入门

AOP就是一种编程范式，和OOP一样就是一种编程思想。基本思想就是在不改变的代码的情况下，对功能进行增强。个人感觉刚开始有点难理解这个思想，如果需要增强功能必须要增加代码量。
下面的理解是按照网上的一些说法，结合我自己的一点理解。

AOP描述的是，当程序中出现了共性的功能，比如相同的循环等，那么代码设计的时候就可以定义一个通知类来收集这些功能，并且把共性功能当作一个方法来使用。
当一个方法需要使用通知类中的方法时，就可以把这个方法定义成一个切入点，连接这些切入点和通知方法的部分就叫做切面。所谓的连接点则是程序执行过程的任意位置，
所谓的粒度则是执行方法，抛异常等。切入点其实跳开编程，就用最基本的中文来理解反而比较容易，就是找一个东西的条件，比如我可以按照名字去找，可以按照异常去找等。
所以切入点可以单个也可以多个，就看需要条件是什么。通知则是在切入点执行的操作。

## 实战入门

```java
@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void com.jon.dao.BookDao.update())")
    private void pt() {}

    @Before("pt()")
    public void method() {
        System.out.println(System.currentTimeMillis());
    }
}
```

上面是一个简单的通知类。先来看下面的method，这个其实就是所谓的切入点要执行的方法，这里写了一个输出系统时间，所以就等于是输出一下时间。先不管@Before，看前面的pt方法。
这个方法的名字无所谓，主要是看注释。这里的`@Pointcut`意思就是切入点，刚才我的理解中说了，想要定位到一个切入点就需要给出足够的条件，因为我希望切入点只有一个，所以我把整条路径全部给出了，
保证一定是调用的BookDao的无参update方法。这个时候再来看刚才的`@Before`，他的作用就是保证在pt的前面加入method中的代码。当然这样还没完全写好一个aspect类。
还需要在类的前面加入`@Component`和`@Aspect`来保证Spring会把它当作一个切面来处理。

> PS: Spring容器在读取切入点的时候，只有使用到的切入点会读取，而没有使用到，但定义了的会被暂时忽略。

Spring AOP的核心概念中还有目标对象和代理。

目标对象：去掉共性功能后的对象，也就是无法直接完成所有工作的对象  
代理：目标对象无法直接完成全部工作，这个时候会用代理对象往原始对象中填入通知，使其具备完整功能。然后在执行

所以AOP的基本实现模式是代理模式。

## 切入点表达式

简单的理解就是用来找哪一个或几个具体方法的筛选条件。这里spring官方就给了个网站让自己去看，也没个教程，真的差评。不过好在是找到了

```java
MethodPattern = 
  [ModifiersPattern] TypePattern 
        [TypePattern . ] IdPattern (TypePattern | ".." , ... ) 
        [ throws ThrowsPattern ]
ConstructorPattern = 
  [ModifiersPattern ] 
        [TypePattern . ] new (TypePattern | ".." , ...) 
        [ throws ThrowsPattern ]
FieldPattern = 
  [ModifiersPattern] TypePattern [TypePattern . ] IdPattern
ThrowsPattern = 
  [ ! ] TypePattern , ...
TypePattern = 
    IdPattern [ + ] [ [] ... ]
    | ! TypePattern
    | TypePattern && TypePattern
    | TypePattern || TypePattern
    | ( TypePattern )  
IdPattern =
  Sequence of characters, possibly with special * and .. wildcards
ModifiersPattern =
  [ ! ] JavaModifier  ...
```

其实所谓的表达式就是利用一个叫做AspectJ的语言写的，这个本身不是我关注的内容，直接看结果。简单的翻译一下就是，先修饰符，再返回值，然后类，接口，方法名+参数，最后异常。
这样一想，如果我需要大量的这种切入点方法，那岂不是每个我都需要使用一大堆的方式来限定？所以spring给出了它的解决方案——通配符。

主要的通配符有三个`*`,`..`,`+`。其中星号表示任意，这个是老朋友了。然后双点表示多个连续的任意，比如从某个包到某个包之间的所有包都可。而最后加号表示的匹配子类型，比如某个接口的实现类。

## AOP通知类型

AOP通知描述的是不同方法间的共性功能，而根据位置的不同，重新切入的时候，位置也不一样。一般会把AOP的切入位置分为5种

- 前置通知
- 后置通知
- 环绕通知
- 返回后通知
- 抛出异常后通知

虽然看上去有很多种类型，但是会用到的可能就是环绕通知这一种。因为环绕通知其实就可以模拟其它四种的情况了。基本的使用来看下面的这个例子

```java
@Around("pt2()")
public Object aroundSelect(ProceedingJoinPoint pjp) {
    System.out.println("around before advice ...");
    try {
        return pjp.proceed();
    } catch (Throwable e) {
        System.out.println("around proceeding failed");
        e.printStackTrace();
    }
    System.out.println("around after advice ...");
    return 200;
}
```

和刚才的@Before类似，先是用注解的形式告知针对的是哪一个切入点。由于这里切入点有返回值，所以为了获取到返回值，就是用ProceedingJoinPoint。
当然使用pjp不完全是因为具有返回值。它还规定了切入点的位置，比如我希望切入点在我打印的两句话之间输出，所以就把`pjp.proceed()`放在了中间。
注意这里还有一个问题是，由于切入点是否会抛出异常是未知的，所以必须设计异常应对。

# Spring事务介绍

## 转账案例分析

要求：实现任意两个账户间的转账操作,保证一致性。利用spring的事务管理来实现。

基本方法就是在需要保证一致性的事务上加入`@Transactional`，这样spring就会自动管理这个事务，但是有一点也需要注意，加入注解的事务中出现了很多不同的小事务，
spring的逻辑是把他们全部合并成一个大事务然后全部提交或回滚。

所谓的事务管理员就是开启事务的方法，而事务协调员则是加入事务的方法。

## 事务相关配置

Spring的事务管理提供了很多不同的配置选项。所有的方法都可以在注解层面开启或者关闭。比如rollbackFor等常用的配置。

由于这个部分大部分还是要靠实践经验，所以这里就是稍微了解一下，等到Spring Boot的时候在做更全面的总结。