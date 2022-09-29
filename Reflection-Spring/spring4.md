# Spring MVC

## 实战入门

详细代码我放在Spring-MVC目录下了。基本就是使用tomcat起一个服务器，然后通过各种注解来生成servlet对象和响应。

```java

@Controller
public class UserController {

    @RequestMapping("/save")
    @ResponseBody
    public String save() {
        System.out.println("user save ...");
        return "{'module':'springmvc'}";
    }
}
```

通过上面的这个简单的代码设置controller。这个控制器的主要作用就是对每个具体的request来进行处理。这里只限定了/save请求的处理方法。实际上，一个简单的springMVC的网页它的运行流程应该是，
先有网页发送请求，web容易发现请求，并且按照配置找到被springMVC管理的请求，解析请求路径，通过controller执行对应方法，最后返回响应。
下面的总结中会一个一个来研究。

## 请求与响应

这里可以聊的东西很多，但是由于后期Spring Boot其实会简化不少开发，所以这里还是已入门为主。后面有机会我会把Spring的源码慢慢看过去的。

想说利用一下`@RequestMapping("/xx")`来设置Servlet的模块路径，然后非页面的就加入`@ResponseBody`
来表示自动转入JSON。虽然对于程序员来说只是一个注解，
但是实际上是有spring中的接口来实现的，具体源码暂时先不看。队医controller里面return的所有内容，其实最后都会自动转成JSON来处理。

# REST

## 是个啥

Representational State Transfer，译为表现形式状态转换。不得不说字面意思完全看不懂，不过我个人的简单理解是访问一个网络资源的方式和某种规范。
感觉大部分文章对这个想法是REST很大成都少简化了书写，但是最重要的是无法通过地址得知资源是什么操作。所谓的RESTful就是对REST风格的资源进行访问

> 冷知识：REST表示它们这个并不是规范，可以不遵守，但是推荐大家使用。

## 入门案例

```java

@Controller
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public String save() {
        System.out.println("user save ...");
        return "{'module':'usr save'}";
    }
}
```

和前面几乎没有区别，只是把RequestMapping中的模块路径改为了/users。这种写法在REST风格中表示保存，并且还规定了只能用POST发送这个请求。
当然居于不同的需求，RESTful一共给出了8种不同的请求方法。

## 进一步简化

由于刚才的案例中所有的方法都是返回JSON，并且模块一致，所以可以把注释的为止改变一下放到类的上面来简化

```java

@Controller
@ResponseBody
@RequestMapping("/books")
public class BookController {

    @RequestMapping(method = RequestMethod.POST)
    public String save() {
        System.out.println("booking save ...");
        return "{'module':'book save'}";
    }
}
```

又因为Controller和ResponseBody都是REST风格的，所以Spring就更深一步的简化了注释，全部用@RestController即可。既然Spring都这么支持REST风格了，
那肯定RequestMethod也不要啊，spring提供了又一个新注释叫@PostMapping。所以最终的样子就非常的简洁了。

```java

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String save() {
        System.out.println("booking save ...");
        return "{'module':'book save'}";
    }
}
```

不得不说，只用spring没利用boot就已经可以这么间接了，属实很期待boot能优雅到什么程度了。

## ExceptionHandle

其实和普通java的处理方式区别不大，主要考虑的倒是前后端的传参问题，这次新学了一个简单的Result的方式来传参，主要就是定义一个result类，然后把这个类转成JSON传给前端。
还有就是利用AOP的思想来实现异常管理，包括切入点的位置等。

# 拦截器

动态拦截方法调用的机制，在SpringMVC中动态拦截控制器方法的执行。具体用法在Spring Boot还会再次整合，就不详细讨论了。


