# Hexo

Hexo 就是一个将markdown转译成blog的工具。基本上，只要输入一个markdown文件，Hexo就可以将其转译成一个blog

### 开发环境与配置

#### 环境

Hexo不需要很复杂的开发环境，基本上只需要三个工具

- Git
- Node.js
- Hexo

> 基本安装的话，就参考官网的一个[教程](https://hexo.io/docs/)即可

#### 配置

Hexo中可修改的配置有很多，基本都放置在_config.yml里面。使用IDEA打开即可修改。

> 注意：Hexo使用的配置文件的格式是.yml
> 我们也可以自己写config，只要用``` $ hexo server --config custom.ymln ```把写好的config配置给hexo就可以了
> 配置文件里面的东西大多数都是字面意思，后面我会把一些有些小坑的地方标注出来。

### 前期准备

##### Github部分

首先需要使用github账号创建一个仓库，这个仓库的名称有限制条件。 必须要保证仓库的名字为``` <github_username>.github.io```，这个是使用github pages的一个充要条件。

##### SSH部分

其实从20年后，hexo就不在要求SSH作为必须条件了，只要保证本地的Git和Github建立了链接即可。



