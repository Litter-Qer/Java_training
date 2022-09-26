# 注解开发

## Component

在开发bean类的时候，spring支持直接使用注解的模式。也就是说，程序猿们不需要在通过复杂的配置和一大堆的方法来生成一个类。相反地，可以只通过简单的注解来定义一个bean和注入依赖。
最为简单的方式就是`@Comopnent`注解。不过为了语义表达上的可读性，spring还提供了三个额外的注解来完善bean的层级。它们分别是`@Repository`。`@Service`，`@Controller`，
开发者可以直接使用这三个类来注释一个bean，从而达到分层的效果。但是特别需要注意的是这三个注释和前面的component是完全一样的作用，只是名字不一样。

下面来看一个实战的开发

```java
@Configuration
@ComponentScan("spring2")
public class SpringConfig {
}
```

仅仅通过两个简单的注解就完成了配置文件的实现，spring真的太方便了。当然除了配置文件，bean也可以通过下面的方式来进行注解开发。

```java
@Component("bookDao")
public class BookDaoImpl implements BookDao {
    @Override
    public void save() {
        System.out.println("Book Dao save  ----- Done");
    }
}
```

括号中的内容就是这个bean的id，注意是id，所以取bean的时候要通过这个id来取。

## Bean 管理

### 初始化和销毁注解

```java
@PostConstruct
public void init() {
    System.out.println("Init  ...");
}

@PreDestroy
public void destroy() {
    System.out.println("destroy ...");
}
```

这个地方还要额外导入以下maven的依赖，因为spring核心依赖里面没有包括这个部分。上面的两个注解没什么特别需要讨论的地方，就按照顺序用就好了。

### 自动装配

在spring中可以使用autowired注解来直接注入bean，这样就彻底省掉了xml配置文件。只需简单的配置以下不同的properties，也就是可能用到的一些值，魔数等。
不过autowired的具体注入过程我还没细看，这里就先略过了。后面有时间会取探究以下源码到底是怎么实现自动注入的。

### 第三方bean的管理

比较主流的方式好像是把每个单独的三方配置写成一个`{name}Config.java`，然后统一通过`@Import`在SpringConfig里导入。如果出现类型引用等依赖注入的情况，
就在方法中直接加入形参，如果IoC中有这个bean就会自动注入。具体例子看下面

```java
public class JDBCConfig {
    @Bean
    public DataSource dataSource(BookDao bookDao) {
        System.out.println("BookDao Address " + bookDao);
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("${jdbc.driver}");
        ds.setUrl("${jdbc.url}");
        ds.setUsername("${jdbc.username}");
        ds.setPassword("${jdbc.password}");
        return ds;
    }
}
```

上面的代码模拟了一个Druid连接池的导入。后面几行只是基本设置，不用特别注意。就是利用的properties文件简化了值引用。这里主要模拟的是依赖注入的情况。
加入Druid连接池需要BookDao才能使用，那么就需要在这个方法中加入BookDao作为形参，并且直接使用。这个听上去很神奇，其实就是spring会自动在容器中找**类型**相同的bean。
除此之外，如果需要这个三方的bean必须在创建对象后返回这个对象。并且在方法上加入@Bean表示这个bean会被容器直接管理。

