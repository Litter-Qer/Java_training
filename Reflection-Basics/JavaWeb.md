这篇作为学习spring前的复习，重新梳理一遍java web的结构和基本用法。

# HTTP-请求格式

```http request
POST / HTTP/1.1
Host: www.google.com
Connection: keep-alive
Cache-Control: max-age=0 Upgrade-Insurance-Request: 1
User-Agent: Mozilla/5.0 Chrome/xx.xx.xxx

username=superbaby/password=123465
```

一般来说一个http请求会被分成三个部分：

1. 请求行：请求数据的第一行，其中`GET`表示请求格式，`/`表示请求资源路径，`Http/1.1`表示版本。
2. 请求头：第二行开始，格式为key: value形式
3. 请求体：POST请求的最后一部分，存放请求参数

> 常见请求头：
> - `Host` 表示请求的主机名
> - `User-Agent` 浏览器版本，例如Chrome的标识
> - `Accept：*/*` 浏览器可以接受服务器回发的类型为全部，但是也可以自己限定
> - `Accept-Encoding` 浏览器申明自己接收编码方法，通常指定压缩方法，是否支持压缩，支持什么压缩方法。（并不是只有字符编码）
> - `Accept-Langauge` 浏览器申明自己接收的语言
> - `Connection` 如果是`keep-alive`则是不断开连接，`close`就断开连接。所谓的连接就是客户端和服务器之间用于传输HTTP数据的TCP连接。
> - `Referer`当浏览器向Web服务器发送请求的时候，一般会带上Referer，告诉服务器我是从哪个页面链接过来的，服务器借此可以获取信息用于处理
> - `Cache_Control` 默认是`private`，只能够作为私有缓存，不能再用户间共享。`public`
    响应会被缓存，并且再多用户间共享。正常情况，如果要求HTTP认证，相应会自动设置为private。
    > `must-revalidate`意思是再特定条件下会被重用，以满足接下来的请求，但是它必须到服务器端验证它是不是仍然是最新的
    > `no-cache` 响应不会被缓存，而是实时向服务器端请求资源，`max-age` 设置缓存最大的有效时间，单位是秒。`no-store`
    在任何条件下，响应都不会被缓存，并且不会被写入客户端的磁盘中
> - `Cookie` 存储一些用户信息以便让服务器辨别用户身份
> - `Range:bytes=0-5` 指定第一个字节的位置和最后一个字节的位置。用于告诉服务器自己想取对象的哪部分。

GET的请求参数再请求行中，没有请求体。POST的请求参数再请求体中。GET请求参数大小有限制，POST没有

# HTTP-响应数据格式

```http request
HTTP/1.1 200 OK
Server: Tengine
Content-Type: text/html
Transfer-Encoding: chunked

<html>
<head>
    <title></title>
</head>
    <body></body>
</html>
```

它也分为三个部分：

1. 响应行：响应数据的第一行，其中`HTTP`表示协议版本，`200`表示请求响应状态码，`OK`表示响应描述。
2. 响应头：第二行开始，格式为key: value形式
3. 响应体：存放响应参数

常见的响应头这里就不总结了，网上太多总结好的。

# 响应状态码

| 状态码分类 | 说明                                             |
|-------|------------------------------------------------|
| 1xx   | 响应中 -- 临时状态码，表示请求已经接受，告诉客户端应该继续请求或者如果它已经完成则忽略它 |
| 2xx   | 成功 -- 表示请求已经被成功接收，处理已完成                        |
| 3xx   | 重定向 -- 重定向到其它地方 它让客户端再发起一个请求已完成整个处理            |
| 4xx   | 处理发生错误，责任在客户端                                  |
| 5xx   | 处理发生错误，责任在服务器端                                 |

