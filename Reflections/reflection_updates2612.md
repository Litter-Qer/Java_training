# Collection

本次任务的重点主要是讨论不同的集合和整体集合框架的应用，由于上次已经完整的对比过了ArrayList和LinkedList。所以这次直接略过这两个部分。

## 基本结构

java中的集合其实主要就分为两大类，一个是List，一个是set。  
List主要是由不唯一，但是有序的元素组成。  
Set主要是由唯一，但是无序的元素组成。  
这里所使用的有序无序表示的是插入的顺序，而不是排序的顺序。

观察Collection的源码会发现它是其实继承了可迭代这个接口，可迭代接口内部仅有两个方法一个是foreach还有一个是spliterator。其实这就相当于规定了所有的collection类都应该可以遍历。
由于iterable这个接口主要就是foreach，所以就略过了。当然从java 8后出现了一个新的特性stream。这个部分我想放到后面单独做一系列的反思。继续看collection中的方法，
主要有add，remove等基础的集合操作，具体实现会结合具体的类型来解释。这里比较值得注意的一个点是remove中入参是Object，算是和之前的研究对上了。这个说明了java处理集合的元素的时候，
还是使用object来进行的。通过add的入参是一个T（泛型变量），再加上JVM都是把泛型变量再擦出的时候使用object来替换的，直接可以证明collection中的元素java也是当作object来处理的。

## 迭代器 Iterator

由于无论是iterable还是collection都再参数中包括了一个iterator，所以有必要先研究以下iterator的作用和实现。其实迭代器也是一个接口，不过它规定的东西和iterable有所不同，
首先它实际上是从enumeration演变过来的，这一点源码中也有提及。其次它允许再迭代中途删除元素。具体再源码中一共给出了三个方法——hasNext(),remove(),next().
都可以从字面意思去理解。在java 8之后又新增了一个forEachRemaining的方法，这个方法主要的作用是都剩下的元素使用同一个操作。这个方法更多的是为了stream准备的。

实际应用中，迭代器处于两个元素之间。每次使用next的时候，迭代器都走到下一个元素和下下元素之间，并且返回刚走过的元素的引用。既然如此那么删除操作中就要特别注意删除的顺序。
每当调用remove这个方法时，实际上迭代器删除的是刚走过的那个元素。这里就出现了一个有意思的现象，如果我想连续删除某两个或以上的元素，则先要越过两个元素在连续使用remove。或者next和remove交替使用。

## 集合 Collection

collection中的大部分方法通过方法名就可以理解了，相对值得注意的是基础方法中的size和iterator在AbstractCollection这个类中抽象化，所以即便Collection接口具有众多的方法，但是一个具体的集合类
已经不需要刻意去实现当中的大部分方法。这算是java设计者的一个小优惠吧。还有一个点就是所有的集合类都可以调用toArray转为数组的形式。

## 集合种类

省略了ArrayList和LinkedList

### Hash table

严格来说hash table和后面的map都应该属于map而不是collection。但是我就当作一种特殊的集合吧。hash table本质上就是一个一个的键值对数组。在java中hash table是由链表数组实现的。每一个单独的列表被成为桶。一般来说，决定对象的位置时使用对象的hash code除以桶的总数，在取余数。
这个余数就是对象的index。如果在余数的位置已经存在了元素的话，也被成为hash collision，这时就会需要使用新的对象和已有的对象进行比较。如果相同则不用加入（唯一性），如果不同则已linkedList的形式加入这个位置。
这里说明了桶的数量是非常的关键的，一般标准的桶数应该为2的幂，这样做的好处就是如果出现了移动的操作话，可以利用machine code的特性来达到快速移动。那么同样的，如果开始的桶数过少，
则会出现rehash这种情况。这个步骤就是把已有的表重新散列，创建一个桶数更多的表。一般判定是否需要rehash的变量为load factor。这个值，java中默认的是0.75，既75%的表被填满是则rehash。
根据源码中的规定，一般hashtable自动扩容会扩到2n+1。这里就比较有意思了，一般来说我们希望表的大小为2的次幂，但是自动扩充后，却永远是一个奇数。虽然取模操作的方法中，2的次幂可以提高效率，
但是hash table其实希望元素在表中的分布更加均匀。所以为了迎合java原先的设计思路，使用hash table时也可以尽量选择奇数。后面在hashmap中还在再次提到，hash map是怎么处理这种不均匀问题的。

观察hash table的源码会发现，它是线程安全的，基本上所有对这个table进行操作的方法都是synchronized。而且在put方法中也可以看到它是不允许null键和值的。
在通过查找它的底层实现方式，就会发现其实它也是一个数组

```java
private transient Entry<?,?>[]table;
```

只不过它所有的元素都是Entry类。查看Entry的源码，其实底层也就是一个单向的链表，其中每一个entry会包含它的哈希，键， 值和指针指向下一个entry。hash table还有一个值得注意的方法是elements。
elements这个方法其实返回的是一个枚举类而非数组，所以不可以直接遍历，需要使用iterator的方式来遍历。但是这个方法确实提供了遍历hash table的一个方案。关于一个entry的加入这里我想引入以下源码，

```java
private void addEntry(int hash, K key, V value, int index) {
        Entry<?,?> tab[] = table;
        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded
            rehash();

            tab = table;
            hash = key.hashCode();
            index = (hash & 0x7FFFFFFF) % tab.length;
        }

        // Creates the new entry.
        @SuppressWarnings("unchecked")
        Entry<K,V> e = (Entry<K,V>) tab[index];
        tab[index] = new Entry<>(hash, key, value, e);
        count++;
        modCount++;
    }
```

重点是看最后一段creates the new entry，我们已经知道hash table是采取链表的形式处理hash collision。这里的内容就是具体的处理方法。实际上entry可以看成是一个单向的链表，
而每次出现hash collision的时候，加入的元素是放在这个链表的首部的。也就是说，每次加入都是把新的链表的头放入entry数组里面。如果从这个角度分析的话，其实hash table的查询速度，
在链表中同位置元素变多的情况下就会逐渐的变慢，因为每一个位置上都是一个链表，所以其实数据量大的时候，查询的速度并不一定快。

### HashMap

大体上和hash table很相似，但是具体的细节上面又很多不同的地方。首先是在java 8之后，底层储存方式变成了数组+链表+红黑树。数组加连链表和hash table并无不同，但是红黑树的引入确实解决了查找的问题。
由于源码中的实现部分过长，我就没复制过来。具体的算法基本上就是一个平衡树，但是原理应该是2-3 AVL tree，或者是2-3-4 AVL tree。我细看了以下讲解红黑树的实现的文章，我的理解是就是基础的平衡树算法，
不过使用了红黑给每个节点上色，这样比较清晰。其实和2-3 AVL tree实现方式很类似，都是通过旋转来保证节点的位置正确。hashMap中的红黑树一般在一个链表超过8个节点时使用。 因为红黑树的插入和删除需要旋转，一直变动并不是很明智，
因此从红黑树转回链表的时候则是6个节点。因为红黑树在搜索时可以直接看成binary tree所以搜索的速度就是log2（n）。显然比hash table中的n要快很多。在源码中也把节点从Entry改成了Node，为了迎合红黑树。当然除此而外，
hash map在进行hash计算的时候也时不同的。 前面提到为了处理分布不均匀的问题，hash table使用的是奇数的size，而hash map则通过hash的计算方式来处理。这里其实研究JVM的参数的时候提到过，下面看源码

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

之前提过，取模对于2次幂来说就是一个移动的过程。但是JDK 8之后，利用了高位运算来解决分布不均的问题。虽然这种方式只能说效果有限，但是相对还是打散了一部分的数据。
在扩容方面，hashmap是把原来的大小变成2倍来保证2的次幂。

#### 关于load factor

无论是HashMap还是HashTable默认的load factor其实都是0.75。这个数字在JDK的docs有详细的解释，因为hash collision其实是遵循一个Poisson Distribution的。所以当碰撞概率为0.5，次数为8的时候，
根据PD的CDF公式可以得出一个几乎可以忽略不计的值。那么这样就可以说，一个任意的hashmap或者table出现8次hash collision的概率过低，所以基本上使用0.75就可以避免大部分的resize。

HashMap应该是线程不安全的，基本原因就是根本找不到synchronized关键字。

### TreeSet

TreeSet底层就是一个红黑树。根据前面提到的红黑树的性质，它的查找速度也是很快的。而且由于它是一个红黑树，所以它里面的元素都已经是排序好的。当然也因为它是一个红黑树，所以它需要元素实现comparable或者comparator。
具体实现哪一个可以有程序需要外部还是内部调用来决定。当然因为它是红黑树，它又使用对比key的方式保证了元素的唯一性（无法将两个元素插入树里的同一个位置）。具体用法和hashset差不多。由于它也不是synchronized的，所以它也是线程不安全的。
因为它只对比key而没有看具体的值，所以一个set中的值有可能重复。

### 队列

Java中的Queue本身其实就是一个接口，包括它的一些衍生，比如Deque。而具体的实现一般根据功能通过LinkedList或者Array的方式实现。
队列的源码比较简单，就是一些API而已。不过一个有趣的地方是添加元素会有两个不同的方法——add和offer。一般情况下使用哪一个区别不大，因为add不过是offer的一个简单封装。
不过offer可能会普遍一点？

#### Deque

双端队列，依然是一个接口，不过这种队列允许向头尾两端加入元素。具体实现类也有很多，比如LinkedList，ArrayDeque。
也是根据任务的需求要定到底用哪一个

#### Priority Queue

这种类型的队列比较特殊一点，它本身不符合队列的特性——FIFO。优先级队列并是一个类，继承了抽象队列，而抽象队列实现了队列接口。
它的结构是一个二叉堆(balanced binary heap)。但是底层依然是数组，并且默认初始容量为11。它通过comparator来定义大小，如果没有外部的comparator则使用元素本身的comparable。
和ArrayList很像，其中的每一个元素都会被装置成object，也可以直接限定类型。
在这个类中维护的二叉堆是一个最小堆，既每一个父节点都要比子节点大。由于comparator的特性，所以可以直接用lambda表达式来创建一个Priority Queue。

```java
PriorityQueue<Student> stuQue = new PriorityQueue<Student>(5,
                new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        });
```

因为它是一个排好序的队列，查找的复杂度会低很多，而插入的复杂度由于需要维护二叉堆，所以会高一些。

#### DelayQueue

延迟队列其实就是对于优先级队列的一个衍生，不过它使用的comparator是时间而已。基本逻辑就是当一个元素的入列时间超过一个数额才能出列。
内部的处理方式就是，用锁和线程来解决。

