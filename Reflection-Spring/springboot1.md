# SpringBoot 预备知识

## REST风格

之前已经提到过了，其实就是针对原来网页资源描述的简化。让新的网页资源描述变得间接而且私密性更好。基本的风格如下

| 请求方法   | 网页资源描述              | 作用          |
|--------|---------------------|-------------|
| GET    | /users              | 获取所有的用户信息   |
| POST   | /users/ID           | 新建一个用户      |
| GET    | /users/ID           | 获取指定用户信息    |
| PUT    | /users/ID           | 更新指定用户的全部信息 |
| PATCH  | /users/ID           | 更新指定用户的部分信息 |
| GET    | /users/ID/orders    | 获取指定用户的全部订单 |
| DELETE | /users/ID/orders/ID | 删除指定用户的指定订单 |

可以看到，但看URL是无法得知具体的操作的，所以它的私密性得到了保障。

## Controller

其实就是网页开发中的控制层，主要用来控制具体模块的控制。在Springboot的项目中使用REST风格进行开发的话，其实主要会用到三个注解

```java
@RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
@ResponseBody
public String delete(@PathVariable Integer id) {
    System.out.println("book deleted ..." + id);
    return "user deleted";
}
```

`@RequestMapping` 这个注解的主要作用就是确认访问的资源路径和接收的请求方式，比如上面代码中它规定了，当请求方法是POST并且页面路径为/users的时候，调用一个save方法，返回一个String。
RequestMapping里面也有很多可调的参数。这里浅尝一下它的源码

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
```

忽略前面的一大堆注解，会发现这个注解接口中定义了6个变量。其中value就是实际路径，name则是请求的名称，就不多提了。请求方法则是method，这个也没有什么特别的。
这里不太常见的两个是consumes和produces，一个是请求的媒体类型(也就是文件类型)，一个是返回的媒体类型。一般会使用映射来规定不同的媒体类型。
剩下的两个就比较常见了，首先params就是这个请求必须传入的参数，可以看到是一个String数组，所以传入的时候必须也是数组的形式，而headers则是请求头必须包含的信息，
比如使用`headers = "content-type=text/*"`就表示content-type的匹配类型必须是text。其实这个部分官方文档做得还挺详细的，不过需要特别注意的地方不是很多，
所以就大致了解一下，具体等使用的时候在继续总结。

在上面的例子中还出现了一个占位符叫做`{id}`，它的主要作用就是传入些参数到具体的业务逻辑中。比如这里的id，但是这个部分必须配合`@PathVariable`使用。
其实就等于用注解声明一下变量。这个注解只接收路径参数，也就是占位符描述的路径参数，如果想要接收Json那么最好使用`@RequestBody`。如果发送的是非json格式，
则可以使用`@RequestParam`来简化。一般开发估计用body的比较多，因为大部分的前端参数传入后端还是以json的格式为主。


在这个controller里面最后一个基础的注解就是`@ResponseBody`，当然它也属于`org.springframework.web.bind.annotation`包。它的主要作用就是表示这个请求是返回值的。
并且这个返回值应该是符合web开发的响应体。

### 精简开发

既然大部分的method都会用到@ResponseBody和@RequestMapping那么肯定得有方法解决重复注解。这也是SpringBoot中的一个增强功能。使用@RestController来定义控制器。
话不多说，直接上源码

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {
    @AliasFor(annotation = Controller.class)
	String value() default "";
}
```

前面3个注解是一样的，继续暂时忽略，直接看后两个，@Controller告诉spring这个类是一个控制器，并且直接被容器管理，而后面的@ResponseBody则是告诉spring每一个方法返回都必须符合响应体。
这个类中的value()方法则不需要特别注意，它实际上作用没有研究的必要，就是告诉spring一个比较合乎逻辑的bean name，便于spring自己进行管理。

那么下面我们继续简化这个操作，既然@RequestMapping中只要求写入一个请求方法了，那么为什么不直接用一个注解来表示请求方法呢？不仅更加简洁，而且可读性还高。
所以SpringBoot有一次帮助spring整合了注解，简单点开注解`@PostMapping`的源码

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.POST)
public @interface PostMapping {
	@AliasFor(annotation = RequestMapping.class)
	String name() default "";
    
	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};
    
	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] params() default {};

	@AliasFor(annotation = RequestMapping.class)
	String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class)
	String[] consumes() default {};
    
	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};
}
```

继续忽略前三个，剩下的参数其实就是最前面@RequestMapping中的内容，而它调用的也就是RequestMapping中的注解。所以这也是为什么说SpringBoot是在spring基础上做的增强，
而不是改动。这个实现完全没有任何的入侵。

## 配置信息

之前提过现在大部分用的都是yml文件了，所以properties文件类型就不在赘述了。需要的配置包括可能会用到的部分，我会在后面遇到的时候在分析。

## 整合Mybatis-Plus

理解大致的流程倒是不复杂，但是mybatis-Plus的功能其实也挺多的，很多地方我也是用到了才知道。具体的整合方式也比较简单。

首先是依赖导入，就是mybatis-plus-boot-starter。按照最简单的开发思路，一般整合数据库需要三层，数据层，表现层和业务层。
因为我只是模拟一下MP所以就不要表现层和业务层了，直接就是一个Mapper加一个pojo来测试。那么springboot整合其实也就是注解开发。这里会用到的注解主要有三个

`@Mapper`这个注解如果点进去看它的源码的话，非发现很简单，它其实是Mybatis开发的注解

```java
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface Mapper {
  // Interface Mapper
}
```

他唯一的作用就是声明这是一个mapper，让spring容器可以管理这个bean，就这么简单。

`BaseMapper<T>`才是MP自己开发的部分，它可以说功能异常强大，而且扩展性非常好。首先使用它的话，就是把pojo直接配给通配符，并且让mapper类直接继承
这个BaseMapper。要注意的是，BaseMapper它本身就提供了CRUD的所有操作，基本上不需要在怎么改动，只需要根据业务逻辑稍微变化即可。而且它自己封装的QueryWrapper也算是高度解耦的。
不过它的坏处也在这，由于使用的sql是提前就配置好的，所以可能会有sql效率低的情况。

最后一个注解其实是一批注解，主要就是用来解决实体类映射到表上的问题，比如表名和类名对应，字段和字段对应等。这些都可以用@TableFiled，@TableName等注解。
具体用法也比较简单就不看源码了，就是注意把需要用到的字段注解加上即可。

### 分页查询

这个查询比较有意思，所以就放上来讨论一下，在mp中分页查询的思路是通过IPage和queryWrapper来实现的。那么具体它们是做啥的呢？

首先是IPage，这个接口也是MP自己规定的接口，它的主要作用就是分页，内部包括了一些规定好的方法，一般来说有Page类来实现。如果细看Page类会发现也没有什么东西，
其实MP的优秀指出就在这，大部分复杂的代码全部都整合好了，真正看到的都已经是上层API的逻辑了。Page类内部的字段比较简单就不多分析了，唯一需要注意的是构造器，
一般使用就用最简单的那个(current，size)。无论是current是0还是1都表示第一页，之后就正常分页。size则是每一页的大小。

分页查询中还有一个特别需要注意的是拦截器。对于Mybatis来说如果不适用拦截器是无法直接进行分页的。虽然MP提供了接口和创建动态sql的方法，但是依旧无法直接进行分页。
拦截器的作用就是把分页的Limit打开，是得动态sql中会自动加入。基本上高版本的mybatis目前都是使用MybatisPlusInterceptor来分页，和之前那个Pagination已经不一样了。
原因还是充分解耦，因为新的拦截器还是加入不同的拦截器作为字段来使用，更加像一个接口。

### queryWrapper

MP自己封装的操作类，作用就是补齐sql，让所有的query变得可读且易编写。里面封装了很多API，但是没有什么很复杂的用法，而且大部分API的名字就准确的表达了用法，所以就不讨论了。

#### lambdaQueryWrapper

也是MP封装的类，就是把原先传参的时候使用的确定参数换成了lambda表达式，也没有什么特别的，不过使用这种方案有一个好处是，在语法检查阶段就可以查出sql的问题。

## 业务层 Service

具体代码很简单，就是一些CRUD的方法。调用的也是数据层的方法，如果真的需要对某些数据进行操作，那么在写入业务逻辑。一般来说这些方法都应该return需要的对象或者一个Boolean。
因为对于前端来说，这些API的调用应该遵循成功则返回true的原则。利用springboot和直接使用spring也没什么区别，都是在业务实现类上加入@Service，让spring管理这个类。

### MybatisPlus 简化业务层开发

之前数据层的时候，使用BaseMapper可以大幅度简化开发。那么MP当然不会只在数据层做了简化，到了业务层它也可以大展身手。MP提供一个叫做IService的接口来完成这项任务。
这个接口提供了业务层所需要的大部分方法，比如基础的CRUD，不过它的方法名稍有不同，所以要注意一下。使用方法很简单，在原先的业务层接口上继承IService泛型给定pojo类。
然后实现这个接口，不够它的实现类需要继承一个ServiceImpl<?, ?>的类，这个类其实就是帮助实现接口方法的。第一个泛型给mapper，第二个泛型给pojo即可。具体测试放在
BookServiceMpTest里面了，也没有啥特别的，就直接略过了。