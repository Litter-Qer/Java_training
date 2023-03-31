# NIO

本章的内容主要基于NIO理论基础，会结合一些简单的代码，但是仅供学习使用。

首先NIO就是简单的Non-Blocking IO，翻译过来就是非阻塞IO。

## 三大组件

### Channel & Buffer

channel就是通道的意思，类似于java中的stream流。在NIO中一个channel必须满足双向输入输出数据的特性。也就是读写一体。

Buffer就是缓冲区，类似于Java中的Buffer，它的作用就是在内存中开辟一块区域用来保存从channel输入或输出的数据。由于在内存中，所以这块数据只是缓冲，并不具备持久性。

目前比较常见的channel有如下4个

- FileChannel：文件通道
- DatagramChannel：UDP通道
- SocketChannel：长连接通道 TCP
- ServerSocketChannel：服务器长连接通道 TCP

在NIO中相对比较重要的buffer就是ByteBuffer，而它的实现类一般有以下三种

- MappedByteBuffer
- DirectByteBuffer
- HeapByteBuffer

### Selector 选择器

选择器一般用来配合一个线程管理多个channel，获取这些channel上的事件。这些channel工作在非阻塞模式下，就不会出现一个channel把一个线程卡死的情况。
当连接数变多，但是流量的低的时候。这个模式可以完美解决需求。

## ByteBuffer

ByteBuffer是NIO中最重要的一个部分，它是数据输入输出的关键，本质上就是在内存开辟一块空间。那么对于任何一个ByteBuffer而言，它都会有一些基本属性。
比如capacity，position和limit。



