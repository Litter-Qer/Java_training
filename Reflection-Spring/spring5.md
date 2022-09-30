# Spring Boot

目前我对springboot的理解就是基于Spring技术的一种优化。它把原来spring中复杂的配置方式全部简化了，只需要有一个简单的Application类，配置一下RESTController就可以快速启动一个spring项目。
如果使用骨架创建项目甚至很多依赖都不需要自己添加到pom，全部直接继承自SpringBoot自己弄好的parent pom。真的很方便，我目前感觉最方便倒是版本问题，之前在Spring中tomcat或者各种版本不兼容问题，
处理起来非常的复杂，但是到了spring boot这个问题可以说是非常的容易解决了。完全有boot来解决版本问题，甚至根据不同的java它能给出不同的版本来，真的nice。

因为还没有学到后面，所以对于这个版本的管理我还是有点担心的，虽然基本可以把兼容性问题解决了，但是如果出现了需要使用的版本不一样的情况不知道boot调整版本是否友好。

## Building a RESTful Web Service

我可能没有完全按照官网的教程来。但是大致步骤是一样的。用的IDEA的spring initializer创建一个springboot的项目。整体创建过程和maven很像，但是需要考虑使用到的技术，比如SpringWeb等。
这个部分先不聊，后面我想稍微花点时间研究一下。继续官网的步骤，直接做一个Controller，和之前一样，就放了一个简单的请求响应。

```java
@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println("id ==> " + id);
        return "Get Success";
    }
}
```

做完这个controller直接通过application启动程序，居然直接就跑起来了，不得不说比spring简单了太多了。

## pom 文件分析

简单看一下依赖文件

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.4</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
</plugins>
</build>
```

先看前面的parent文件，这里的话应该是springboot能都整合和简化开发的最大原因了。可以说springboot直接通过parent提供了很多需要的依赖。
中间的依赖就是简单的依赖导入，没有什么特别需要注意的。主要看最后的这个build块，它保证了程序可以使用jar指令来启动。这个我个人觉得算是springboot对前后端协作的一个小优化。

### spring-boot-dependencies

前面的parent pom也是继承的这个文件，可以它才是整个spring boot的核心pom。这个pom太长了我就没复制，主要看思路。它最重要的事情就是把几乎所有能用到的依赖版本全部整理好了，
但是由于它的版本整理是根据springboot的版本来的，所以开发的时候需要格外注意，很有可能会出现迭代后版本不兼容问题。

额外提一下springboot所有的依赖都叫做starter依赖也就是起步依赖，它里面一般包含了某一个技术需要的全部依赖，并且一般情况下解决了版本冲突的问题。而且由于boot会定义所有的版本号。
因此导入依赖并不需要填写版本号，除非出现了一些并不冲突，那么再去指定版本。

### 替换或排除依赖

针对刚才说的问题，还有一个可能性就是，万一我不想用springboot给我设计的依赖，比如tomcat。我想把它换成jetty呢？那么解决的方案其实也很简单，首先在dependency中使用exclusions把依赖排除。
然后单独加入想要的依赖即可。这个步骤有些繁琐，但是目前好像主流的方法还是它。可能是比较稳定吧。

## 配置文件

之前已经使用过的properties就不在讨论了，本身也比较简单。这次主要来看用的最多的yml方式，而且正好之前接触这个也比较少。

顺便提一下，我一开始看yml和yaml还以为有区别，结果完全一致。大部分的文章都说的是windows原先扩展名超过3位会出现问题，所以导致用yml。但是现在已经解决了，所以本质上随便用就好。

虽然配置文件存在优先级，但是由于现在大部分都是使用yml文件了，所以也不需要特别关注这个部分了。就简单的给一个结论吧。`properties>yml>yaml`

基本语法就是`key: value`类似键值对，前面的是前缀，可以是变量名或者配置名等。后面的就是它的值。然后注意必须空一格，不然读取不了。如果有层级的关系就下一行然后缩几格即可。






