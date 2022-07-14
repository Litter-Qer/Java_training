### ArrayList && LinkedList

#### 泛型ArrayList

不设定任何的值，所以整个arraylist可以存不同类型的数据。Arraylist在构造的时候会根据初始的大小来生成一个空集。
这里的空集实际上就是一个空的Object array。如果实例化的时候没有限定大小，则大小会自动被设为10。 这就说明了，当任何一个元素被放入ArrayList中后，
就会自动的被转为Object类型。 这里在源码中也找到了答案：

```shell
transient Object[] elementData; // non-private to simplify nested class access

private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }
```

##### 添加

当arraylist增加一个元素的时候，实际上是把一个泛型的元素直接加入Object这个array。通过grow这个method增加array的长度。
然后再把元素放到最后一位上。因为java中所有的class都是Object的子类，所以放入这个array不会出现转置失败的问题。
实际上这个等于向上转置，从子类转成父类。

##### 删除

```shell
public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked") E oldValue = (E) es[index];
        fastRemove(es, index);

        return oldValue;
    }
```

从源码中可以很明显地看出，arraylist在删除一个element的时候，实际是先找到它的index，然后把这个位置的element删掉，
最后在把后面的所有elements向左移动一格。显然这样做的效率是不高的。而且由于底层是array的关系，所以还需要重新调整array的大小。

##### 小节

arrayList实际上底层就是一个object array。通过内部的grow和一些helper methods实现了动态数组的样子。但是由于底层的array的特性，
所以使得插入和删除变得较复杂（每次操作都需要改大小，找到位置，重新移动）。但是由于底层使用的是数组的关系，所以它的访问速度是非常块的。
使用index直接查找的话，时间复杂度也就是O(n)。<br>
当然也可以不使用泛型来设计，比如初始化的时候使用具体的类型来定义元素的类型。但是具体的操作和方法调用都是一样的。到了arraylist中后，
还是会存在object array中。只不过由于我已经知道了存储的类型，所以提取的时候就可以使用具体的类型而不是Object来定义。下面的源码证明了
即便提前定义数据类型，底层依旧是object array。要注意，第一行调用了```toArray()```，这就说明了，虽然底层仍然是object array，
但是实际上这个动态数组已经被某一种类型限定了，也就是说，其他任何类型的变量是不可被加入的。

```shell
public ArrayList(Collection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            // replace with empty array.
            elementData = EMPTY_ELEMENTDATA;
        }
    }
```

使用arraylist还有一个有意思的地方。因为arraylist实现了List，所以可以通过java中的多态来直接定义一个动态的数组。比如下面的这行代码

```shell
List<String> al = new ArrayList();
```

虽然al任然是list类型，但是由于使用arraylist进行的实例化，所以所有arraylist的方法都可用。这个用法，感觉实战中会有使用场景。

#### LinkedList

Linked list的底层可以说是完全不同的。linked list底层是一个双向的链表。不过它和arraylist一样都实现了List这个接口。

##### 创建

```shell
public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

通过源码可以发现，linked list在创建的时候，默认就是一个空集合。这个集合既不是array也不是某个特定的collection。
我的理解是实际上，linked list并不是在内存中真正的创造了一个list，而是创建了许许多多的node。每一个节点都会有一个父节点和子节点，
并且也会储存下这个节点里的值。linked list实际上就是把这些节点通过互相指的形式连起来，所以整体看上去会象是一个list。节点也正好是linked list
中的一个内部类。<br>
如果上面的理解正确的话，就说明一个linked list可以双向使用也可以单向使用。除此而外，由于在内存中创建很多个node，每一个node又是一个node类，
显然它所占用的内存空间是很大的。并且要进行查找工作的时候，就无法直接使用index查找了。这个部分后续会有解释。

##### 添加和删除

```shell
public boolean add(E e) {
        linkLast(e);
        return true;
    }
    
 public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
    
void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }
```

直接看linked list的源码会发现，添加元素就是简单粗暴地把元素加入最后。但是如果插入元素的话，则需要先找到它的父节点，然后将这个节点放入，
并且关联到原先的子节点上。虽然这里看上去很复杂，但是其实上由于不需要像arraylist一样把后面的所有元素全部复制并且位移，整体效率理论上来说应该会更高。
但是实际情况似乎也不完全是，后面会有一个我尝试的对比。

##### 查找

```shell
public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }
    
Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
```

由于它是一个链表，所以无法使用index进行查找，那么linked list在进行查找的时候，主要的方法是先判断想要找的index在什么位置，
如果处于后半部，那么从后往前找，反之就从前往后找。这然虽然可以缩短查找时间，但是无论如何都需要经过前面或者后面很多节点。所以，
它的时间复杂度应该是O(n)。

##### 小节

linked list并非是一个list，它只是由很多不同节点连在一起形成的一串链条。所以，当加入或者删除的时候，我们可以快读的定位到需要加入或者删除的位置，
直接进行操作，不需要经过arraylist的复制和移动过程。然而当我们需要查找的时候，由于并没有明确的index，我们不得不一个一个节点的走过去，可以说浪费了效率。
而且由于它每个节点都是一个对象，所以会占用很大的空间。

### 插入和查询的对比 使用JMH进行测试

1. 直接加入到最后面

```shell
Benchmark                Mode  Cnt  Score   Error  Units
ArrayListExample2.test1  avgt    3  0.254 ± 0.981  ms/op
ArrayListExample2.test2  avgt    3  1.846 ± 1.609  ms/op
```

这里会发现，其实直接加入后面arraylist反而是更快的。和一开始的想法一致，因为arraylist加入后面并不需要平移，所以只是单纯的加入。
而linked list反而需要三个步骤来添加一个node。

2. 插入中间

```shell
// 插入位置 1000
Benchmark                Mode  Cnt    Score      Error  Units
ArrayListExample3.test1  avgt    3  253.632 ± 1441.116  ms/op
ArrayListExample3.test2  avgt    3   12.989 ±    7.821  ms/op
```

本次测试首先给一个两个list都填充了10000条数据，然后从1000的位置插入10000条数据，很明显array list的速度比linked list要慢很多。
然而这个结论一定成立吗？我又尝试了在不同的位置插入发现了下面的情况

```shell
// 插入位置 5000
Benchmark                Mode  Cnt    Score      Error  Units
ArrayListExample3.test1  avgt    3  253.999 ± 1314.622  ms/op
ArrayListExample3.test2  avgt    3   57.256 ±   19.251  ms/op

// 插入位置 9000
Benchmark                Mode  Cnt    Score      Error  Units
ArrayListExample3.test1  avgt    3  240.520 ± 1284.715  ms/op
ArrayListExample3.test2  avgt    3  105.944 ±   31.256  ms/op
```

这样看上去似乎结论还是一样的，linked list永远都要比arraylist 插入的速度快。但是实际上我这里所做的所有测试都warmup了2次，测了3次。
由于我并没有每次清空list所以实际上，arraylist的大小由于加入的元素越来越多，变得越来越大，而插入的位置又没有变，所以需要的复制的后半部分也变多了。
这样导致了其实测试结果并不准确。于是我重新设计了测试，去掉了warmup（虽然可能会导致一些效率问题，但是影响不大）。而且也之测一次。
于是我发现了一下结果。

```shell
// 插入位置 5000
Benchmark                Mode  Cnt   Score   Error  Units
ArrayListExample3.test1  avgt       56.114          ms/op
ArrayListExample3.test2  avgt       60.843          ms/op
```

通过多次手动的尝试，发现了其实当插入位置越往后的时候，linked list便开始失去优势，尤其是过半了之后。基本上都是arraylist比linked list快。
这个结论和我之前的猜想对上了，其实虽然linked list插入不需要复制和移动，但是如果我的数据插入位置靠后的话，那么复制和移动的操作。在实际运行中反而是快于加入一个ndoe的。
也就是说，当讨论arraylist和linked list的时候，插入速度，并不是完全绝对的。要根据具体情况选择。

3. 查询

```shell
Benchmark                Mode  Cnt   Score   Error  Units
ArrayListExample4.test1  avgt    3   0.099 ± 0.005  ms/op
ArrayListExample4.test2  avgt    3  28.781 ± 2.345  ms/op
```

这里我使用了随机数，随机访问了10000次。很明显arraylist的查询速度要远远快过linked list。从底层逻辑来分析的话，
linked list需要遍历大部分node才能得到某个值，而arraylist只需要用index直接访问那个内存位置即可。

## 结论

至此，arraylist和linked list的大部分对比就做完了。通过以上所有结果，很轻松就可以解释为何arraylist仍然在日常使用比火。
首先日常使用中，经常需要的功能应该是查询而不是添加和删除。甚至很多极端情况下，根本不希望删除。而添加这个操作，如果只是放入array
的话完全可以放入最后一排，除非我们需要一个排好序的array。如果只是放入后面，arraylist在性能上甚至是有优势的，而并非一般认知的
**慢**。所以在日常使用中，array list这种动态的数组已经可以很好的满足需求，而且占用空间比linked list小很多。


