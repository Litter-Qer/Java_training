# MySQL进阶 2

## SQL优化

### 批量插入

最好一次同时插入多条数据，一般来说500-1000条是比较合理的。并且开启手动提交，如果涉及主键插入的话最好保证是顺序插入，这样可以大大提高效率。
但是同样批量插入如果出现回滚的话，也会导致大量的请求丢失，所以需要手动进行补偿。

### 页分裂

如果主键乱序插入的话，可能会导致页分裂现象。因为主键索引一般是一个双向链表，在InnoDB的逻辑储存结构中，每一页至少需要2个行数据。如果按照乱序插入，
那么当把某行数据填充进一页中的时候，就可能出行溢出，那么InnoDB会把你要插入的那一页的一半数据移动到一个新的数据页，然后把乱序插入的这个数据连回刚才的那页，
同时又需要把新的这一页连接到之前分裂的那页的后面那一页。显然这种操作非常的消耗性能。

### 页合并

正好和页分裂相反，当一页中的50%（默认）的数据都被标记为删除后，InnoDB会自动合并相邻两页（如果可以合并），那么这样就自动空闲出一个页。
这个的一个阈值可以通过variable来设置`MERGE_THRESHOLD`

### 主键涉及原则

- 满足需求情况下，尽量减少主键的长度。
- 尽量使用AUTO_INCREMENT来进行自增操作，来减少页分裂问题
- 尽量不要使用UUID来做主键，因为大部分情况下，它的长度过长且无序

### Order by 

尽量保证多字段order by的时候，前后两个字段的顺序和索引顺序一致，不然会导致索引失效。

## 视图

### 入门

View是一个虚拟存在的表，视图中的数据并不是在数据库中真实存在的数据。视图中所有看到的数据都是基于创建视图时所使用的表，这张表也被称为基表。
并且视图的数据时动态生成的。也就是视图只保存SQL逻辑，并不保存数据。

视图的简单增删改查就利用如下语句即可

```mysql
-- 创建
create or replace view tb_v_1 as
select id, name
from tb_user
where id <= 5;
-- 查询
show create view jon.tb_v_1;
select * from tb_v_1;
-- 修改
alter view tb_v_1 as select * from tb_user;
-- 删除
drop view if exists tb_v_1;
```

由于视图用的不是很多，这里就略过了。

## 储存过程

同样的SQL或者同样的逻辑，很多时候会被大量多次的调用，这就造成了一定程度上的资源浪费，也给网络带宽造成了压力，所以MySQL就利用了叫做储存过程的方法来简化开发。

基本的定义方法如下

```mysql
create procedure {name}(args.)
begin
    #存储逻辑
end;
```

调用的时候就直接call就好了。

# 锁

和java中的锁类似，主要是为了保证并发访问安全的。锁的类型在Mysql中也是比较多变的，一般用锁住的数据量来区分，也就是所谓的粒度。

## 全局锁

锁住数据库中的所有表，这个是最为暴力的锁，因为所有的表都无法进行操作。但是同时也是最保险的锁。实际用的应该不多，我目前能想到的一些应用场景就是全表备份。
把整个数据库的数据全部记录下来，如果这个时候出现了写入，可能导致数据不一致问题。还有需要统计所有数据做分析的时候，也许会需要使用这个。具体语法如下

```mysql
flush tables with read lock;
    -- some work here
unlock tables;
```

一个简单的例子如下

```mysql
jon> flush tables with read lock
[2023-02-02 12:04:43] completed in 8 ms
jon> update tb_user set name = '新白起' where name = '白起'
[2023-02-02 12:04:45] [HY000][1223] Can't execute the query because you have a conflicting read lock
[2023-02-02 12:04:45] [HY000][1223] Can't execute the query because you have a conflicting read lock
```

加锁后如果尝试修改数据，就会直接被拒绝，但是如果使用select是不会收到影响的。操作结束后直接通过unlock tables解锁即可。

## 表级锁

锁单张表的锁，但是依旧锁住的是整张表的数据。
简单的使用就是以下语句

```mysql
lock tables [table_name] write/read;
unlock tables;
```
