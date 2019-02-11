

# 一、概述



数据结构研究的是数据如何在计算机中进行组织和存储，使得我们可以**高效的**获取数据或者修改数据

## 1.1 数据结构分类

| 线性结构             | 树结构                                      | 图结构      |
| ---------------- | ---------------------------------------- | -------- |
| 数组、栈、队列、链表、哈希表等等 | 二叉树、二分搜索树、AVL、红黑树、Treap、Splay、堆、Trie、线段树、K-D树、并查集、哈夫曼树等等 | 邻接矩阵、邻接表 |

需要根据应用场景的不同，灵活的选择合适的数据结构

## 1.2 计算机中的数据结构

1.数据库

主要是树结构：AVL、红黑树、Treap、伸展树、B树等等

还包括哈希表

2.操作系统

操作系统要实现多任务，需要用到系统栈、堆等等

3.文件压缩

如png、mp3、mp4、PDG等等文件都会用到文件压缩，其中最基础的数据结构就是哈夫曼树

4.通讯录

Trie--前缀树

5.算法

几乎所有的算法都是以数据结构为基石的

如游戏中的寻路算法--图论算法，两种思路：DFS（深度优先搜索、使用栈）、BFS（广度优先搜索、使用队列）

数据结构+算法=程序

# 二、线性结构

##2.1 数组

###2.1.1 概述

数组（array），即一个有序的元素序列。数组有一个从0开始的索引，对应每一个元素，我们可以通过索引快速的访问对应的元素。

在Java中一个数组只能存放同一种数据类型的元素，数组在申明的时候就必须确定数组的长度，且这个长度初始化后就是不可变的。

使用Java中的数组：

```java
//数组的初始化方式一
int[] arr1 = new int[10];
for(int i:arr1){
  arr1[i] = i;
}

//数组的初始化方式二
int[] arr2 = {12,13};

//数组的初始化方式三
int[] arr3 = new int[]{123,456};

//遍历数组
for(int i = 0;i<arr1.length;i++){
  System.out.println(arr1[i]);
}
```

数组最大的优势在于能够通过索引快速搜索。所以如果索引有语意，则能够最好的利用数组的这一优势。

###2.1.2 实现动态数组

```java
/**
 * 自己的动态数组类
 */
public class Array<E> {

    private E[] data;
    private int size;//数组中当前的元素数量

    /**
     * 构造方法
     *
     * @param capacity 数组容量
     */
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 无参构造方法，默认数组容量为10
     */
    public Array() {
        this(10);
    }

    public Array(E[] arr){
        data = (E[]) new Object[arr.length];

        for(int i = 0;i < arr.length; i++){
            data[i] = arr[i];
        }
        size = arr.length;
    }

    /**
     * 获取数组元素数量
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     *
     * @return
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 判断数组是否为0
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在数组所有元素末尾添加一个元素
     *
     * @param e
     */
    public void addLast(E e) {
        //当size等于数组长度，说明数组元素已经满了，无法再继续插入元素
        /*if(size == data.length){
            throw new IllegalArgumentException("addLast failed.Array is full");
        }
        data[size] = e;
        size ++;*/

        //直接复用insert方法
        insert(size, e);
    }

    public void addFrist(E e) {
        insert(0, e);
    }

    /**
     * 向数组的指定位置插入新元素
     *
     * @param index
     * @param e
     */
    public void insert(int index, E e) {
        //当size等于数组长度，说明数组元素已经满了，无法再继续插入元素
        /*if (size == data.length) {
            throw new IllegalArgumentException("addLast failed.Array is full");
        }*/

        //校验index参数，保证数组中元素连续排列
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("insert failed.Require index >= 0 and index <= size");
        }

        //当size等于数组长度，说明数组元素已经满了，进行数组动态扩容
        if (size == data.length) {
            resize(2 * data.length);
        }

        //将index位置及之后的元素统一向后移一位
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        //将元素e插入到index位置
        data[index] = e;
        size++;
    }

    /**
     * 获取指定位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get Failed.Index is illegal");
        }
        return data[index];
    }

    /**
     * 获取最后一个元素
     * @return
     */
    public E getLast(){
        return get(size -1);
    }

    /**
     * 获取第一个元素
     * @return
     */
    public E getFrist(){
        return get(0);
    }

    /**
     * 修改指定位置的元素
     *
     * @param index
     * @param e
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set Failed.Index is illegal");
        }
        data[index] = e;
    }

    /**
     * 判断数组中是否包含某个元素
     *
     * @param e
     * @return
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找数组中第一个值为e元素的索引，如果没有该元素返回-1
     *
     * @param e
     * @return
     */
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除指定位置的元素，返回被删除的元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set Failed.Index is illegal");
        }
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        //当数组空间利用过少，动态减少数组长度
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }

        return ret;


    }

    /**
     * 删除数组中第一个元素
     *
     * @return
     */
    public E removeFrist() {
        return remove(0);
    }

    /**
     * 删除数组中最后一个元素
     *
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除数组中第一个值为e元素
     *
     * @param e
     */
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * 重写toString()方法
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append("[");
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }

    /**
     * 动态调整数组大小
     *
     * @param newCapacity
     */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * 交换i，j索引位置的元素
     * @param i
     * @param j
     */
    public void swap(int i ,int j){
        if(i < 0 || i >= size || j < 0 || j >= size){
            throw new IllegalArgumentException("Index is illegal.");
        }

        E t = data[i];
        data[i] = data[j];
        data[j] = t;
    }
}
```

## 2.2 栈

### 2.2.1 概述

​	栈是一种运算受限的线性数据结构，仅允许在一端进行插入和删除操作。这一端被称为栈顶，相对的另一端被称为栈底。向一个栈插入新元素又称作进栈、入栈或压栈，它是把新元素放到栈顶元素的上面，使之成为新的栈顶元素；从一个栈删除元素又称作出栈或退栈，它是把栈顶元素删除掉，使其相邻的元素成为新的栈顶元素。

​	相比于数组，栈的操作更少。栈是一种后进先出（Last In Frist Out/LIFO）的数据结构。

### 2.2.2 栈的实现

定义一个栈接口：

```java
package Stack;

/**
 * 栈接口
 * @param <E>
 */
public interface Stack<E> {

    int getSize();

    boolean isEmpty();

    void push(E e);

    E pop();

    E peek();
}

```

####2.2.2.1 基于数组实现

```java
package Stack;

import Array.Array;

/**
 * 基于数组实现栈
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {
    Array<E> array;


    public ArrayStack(int capacity){
        array = new Array(capacity);
    }

    public ArrayStack(){
        array = new Array();
    }


    /**
     * 获取栈中当前的元素数量
     * @return
     */
    @Override
    public int getSize() {
        return array.getSize();
    }

    /**
     * 栈是否为空
     * @return
     */
    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 获取当前容量
     * @return
     */
    public int getCapacity(){
        return array.getCapacity();
    }

    /**
     * 将一个新元素插入到栈顶（入栈）
     * @param e
     */
    @Override
    public void push(E e) {
        array.addLast(e);
    }

    /**
     * 取出栈顶元素（出栈）
     * @return
     */
    @Override
    public E pop() {
        return array.removeLast();
    }

    /**
     * 查看栈顶元素
     * @return
     */
    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Stack:");
        res.append("[");
        for (int i = 0; i < array.getSize(); i ++ ){
            res.append(array.get(i));
            if(i != array.getSize() - 1){
                res.append(", ");
            }
        }
        res.append("] top");
        return res.toString();
    }
}

```

####2.2.2.2 基于链表实现

```java
public class LinkedListStack<E> implements Stack<E> {

    private LinkedList<E> list;

    public LinkedListStack(){
        list = new LinkedList<>();
    }

    @Override
    public int getSize(){
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.removeFrist();
    }

    @Override
    public E peek() {
        return list.getFrist();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: top");
        res.append(list);
        return res.toString();
    }
}
```

### 2.2.3 通过栈实现括号校验

```java
/**
 * 通过栈实现括号验证
 */
public class Solution {
    public static boolean isValid(String s){
        Stack<Character> stack = new Stack<>();
        for(int i =0;i < s.length();i++){
            char c = s.charAt(i);
            if(c == '{' || c == '['||c == '('){
                stack.push(c);
            }else {
                if(stack.isEmpty()){
                    return false;
                }

                char topChar = stack.pop();
                if(c == '}' && topChar != '{'){
                    return false;
                }
                if(c == ']' && topChar != '['){
                    return false;
                }
                if(c == ')' && topChar != '('){
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
    
    public static void main(String[] args){
        boolean b = isValid("{[(]}");
        System.out.println(b);
    }
}
```



## 2.3 队列

### 2.3.1 概述

​	队列是一种先进先出的数据结构。Frist In Frist Out（FIFO）。

### 2.3.2 队列实现

队列接口：

```java
public interface Queue<E> {

    int getSize();
    boolean isEmpty();
    void enqueue(E e);
    E dequeue();
    E getFront();
}
```

####2.3.2.1 基于数组的实现

```java
/**
 * 基于数组实现队列
 */
public class ArrayQueue<E> implements Queue<E> {


    private Array<E> array;

    public ArrayQueue(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayQueue(){
        array = new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 基于动态数组的实现，所有有一个获取容量的方法
     * @return
     */
    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFrist();
    }

    @Override
    public E getFront() {
        return array.getFrist();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue:");
        res.append("front [");
        for (int i = 0; i < array.getSize(); i ++ ){
            res.append(array.get(i));
            if(i != array.getSize() - 1){
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }
}
```



####2.3.2.2 基于数组的循环队列实现

```java
/**
 * 循环队列
 * @param <E>
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int front,tail;
    private int size;

    public LoopQueue(int capacity){
        data = (E[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {

        //判断队列是否已经满了
        if((tail + 1)% data.length == front){
            //如果已经满了，要对队列进行扩容
            resize(getCapacity() * 2);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }

    @Override
    public E dequeue() {
        //队列为空的话报错
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from an empty queue");
        }

        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;
        //队列中元素过少进行缩容操作
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0){
            resize(getCapacity() / 2);
        }

        return ret;
    }

    @Override
    public E getFront() {
        //队列为空的话报错
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty");
        }
        return data[front];
    }

    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity + 1];

        for(int i = 0;i < size; i++){
            newData[i] = data[(i + front)% data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue:");
        res.append("front [");
        for (int i = front; i != tail; i = (i + 1)% data.length ){
            res.append(data[i]);
            if((i + 1)% data.length != tail){
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }
}

```

#### 2.3.2.3 基于带有尾指针链表的实现

```java
public class LinkedListQueue<E> implements Queue<E> {
    /**
     * 将节点类作为链表类的内部类
     */
    private class Node{
        public E e;
        public Node next;

        public Node(E e, Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head,tail;
    private int size;

    public LinkedListQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 入队操作在链表尾进行
     * @param e
     */
    @Override
    public void enqueue(E e) {
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else{
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    /**
     * 出队操作在链表头进行
     * @return
     */
    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }

        Node retNode = head;
        head = head.next;
        retNode.next = null;

        if(head == null){
            tail = null;
        }
        size --;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty.");
        }

        return head.e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("Queue: front ");
        Node cur = head;
        while(cur != null){
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }
}
```

### 2.3.3 复杂度分析

数组队列，基于数组的实现在入队、获取队列长度，判断队列是否为空、查看队首元素的操作上都是O(1)的时间复杂度，但是在出队的操作上，基于数组的实现复杂度为O(n)。

循环队列，对于循环队列来说，所有的操作都是O(1)的复杂度。

### 2.3.4 优先队列

普通队列：先进先出，后进后出

优先队列：出队顺序和入队顺序无关，和优先级相关。

往往我们在处理实际问题时，队列出队顺序是与优先级有关的，且是动态的。

基于最大堆实现的优先队列：

```java
/**
 * 优先队列
 * @param <E>
 */
public class PriorityQueue<E extends Comparable> implements Queue<E>{

    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap<>();
    }

    /**
     * 获取优先队列大小
     * @return
     */
    @Override
    public int getSize() {
        return maxHeap.getSize();
    }

    /**
     * 判断优先队列是否为空
     * @return
     */
    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    /**
     * 查看队首元素
     * @return
     */
    @Override
    public E getFront() {
        return maxHeap.findMax();
    }

    /**
     * 入队
     * @param e
     */
    @Override
    public void enqueue(E e) {
        maxHeap.add(e);
    }

    /**
     * 出队
     * @return
     */
    @Override
    public E dequeue() {
        return maxHeap.extractMax();
    }
}
```

## 2.4 链表

### 2.4.1 概述

​	前面讲到的数组、栈、队列这些线性数据结构，底层都是依托于静态数组实现的，通过resize来解决数组的固定容量问题。而链表是真正的动态数据结构，它是最简单的动态数据结构。

​	链表是一种物理存储单元上非连续、非顺序的存储结构，数据元素的逻辑顺序是通过链表中的指针链接次序实现的。链表由一系列结点（链表中每一个元素称为结点）组成，每个节点包含一个元素，以及指向下一个节点的引用，最后一个节点的所保存的引用为null。

​	优点：真正的动态结构，不需要处理固定容量的问题。

​	缺点：丧失了随机访问的能力。

​	链表和数组之间比较：数组最好用于索引有语义的情况，最大的优点就是支持通过索引快速搜索；而链表不适合用于索引有语义的情况，最大的优点是动态。

### 2.4.2 链表实现

```java
public class LinkedList<E> {

    /**
     * 将节点类作为链表类的内部类
     */
    private class Node{
        public E e;
        public Node next;

        public Node(E e,Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }


    //private Node head;//第一个节点的引用
    private Node dummyHead;//添加一个虚拟头结点，为了让头结点的操作和其他节点的操作一致
    private int size;//链表的元素数量

    public LinkedList(){
        //head = null;
        dummyHead = new Node(null,null);
        size = 0;
    }

    /**
     * 获取链表的元素数量
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 返回链表是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 在链表头添加一个新的元素e
     * @param e
     */
    public void addFirst(E e){
//        Node node = new Node(e);
////        node.next = head;
////        head = node;
       // head = new Node(e,head);
        add(0,e);
        size ++;
    }

    /**
     * 在指定位置index添加元素。
     * 一般来说，使用链表是不需要实现这个方法的，因为使用链表我们会完全放弃索引这个概念
     * @param index
     * @param e
     */
    public void add(int index,E e){

        //先检查索引的有效性
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed.Illegal index.");
        }

        /**
        if(index == 0){
            //索引为0，特殊处理
            addFirst(e);
        }else{
            //索引不为0，遍历链表直到指定位置前一个位置的元素
            Node prev = head;
            for(int i = 0;i< index - 1;i ++){
                prev = prev.next;
            }

//            Node node = new Node(e);
//            node.next = prev.next;
//            prev.next = node;
            prev.next = new Node(e,prev.next);
        }
         size ++;
         */
        //使用虚拟头结点后，不需要再对插入0位置元素特殊操作
        Node prev = dummyHead;
        for(int i = 0;i< index ;i ++){
            prev = prev.next;
        }
        prev.next = new Node(e,prev.next);
        size ++;
    }


    /**
     * 在链表的末尾添加元素
     * @param e
     */
    public void addLast(E e){
        add(size,e);
    }

    /**
     * 获取链表index位置的元素
     * @param index
     * @return
     */
    public E get(int index){
        //先检查索引的有效性
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Get failed.Illegal index.");
        }

        //遍历链表找到index位置的元素
        Node cur = dummyHead.next;
        for(int i = 0;i < index; i ++){
            cur = cur.next;
        }
        return cur.e;
    }

    /**
     * 获取链表的第一个元素
     * @return
     */
    public E getFrist(){
        return get(0);
    }

    /**
     * 获取链表的最后一个元素
     * @return
     */
    public E getLast(){
        return get(size - 1);
    }

    /**
     * 将index位置的元素设置为e
     * @param index
     * @param e
     */
    public void set(int index,E e){
        //先检查索引的有效性
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Set failed.Illegal index.");
        }

        //遍历链表找到index位置的元素
        Node cur = dummyHead.next;
        for(int i = 0;i < index; i ++){
            cur = cur.next;
        }
        cur.e = e;
    }


    /**
     * 检查链表中是否包含某个元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        Node cur = dummyHead.next;
        while(cur != null){
            if(e.equals(cur.e)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * 删除索引在index位置的元素
     * @param index
     * @return
     */
    public E remove(int index){
        //先检查索引的有效性
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Remove failed.Illegal index.");
        }

        Node prev = dummyHead;
        for(int i = 0;i < index;i++){
            prev = prev.next;
        }
        Node retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;

        size --;
        return retNode.e;
    }

    /**
     * 删除链表第一个元素
     * @return
     */
    public E removeFrist(){
        return remove(0);
    }

    /**
     * 删除链表最后一个元素
     * @return
     */
    public E removeLast(){
        return remove(size - 1);
    }

    // 从链表中删除元素e
    public void removeElement(E e){

        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.e.equals(e))
                break;
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size --;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node cur = dummyHead.next;
        while(cur != null){
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }


}
```

### 2.3.3 复杂度分析

添加操作		addLast O(n)   addFrist O(1)    add  O(n/2)=O(n)

删除操作		removeLast  O(n)   removeFrist  O(1)   remove  O(n/2)=O(n)

修改操作		set  O(n)

查找操作		get  O(n)	 contains  O(n)

### 2.3.4 链表与递归

​	递归的本质就是将一个大问题拆分成一个一个的小问题来解决。而链表具有天然的递归性，近乎所有对链表的操作都可以使用递归的形式完成。

​	一个递归函数主要包含两个部分，第一部分为递归终止条件，第二部分为递归调用。

通过递归解决数组求和问题：

```java
public class Sum {
    public static int sum(int[] arr){
        return sum(arr,0);
    }


    /**
     * 计算从arr[l]到arr[length]的和
     * @param arr
     * @param l
     * @return
     */
    private static int sum(int[] arr,int l){
        if(l == arr.length){
            return 0;
        }
        return arr[l] + sum(arr,l+1);
    }
    
    public static void main(String[] args){
        int[] nums = {1,2,2,2,3,4,5};
        System.out.println(sum(nums));
    }
}

```

### 2.3.5 链表的其他形式

​	1.双链表

每个节点不仅包含一个指向下一个节点的引用，还包含一个指向前一个节点的引用。

​	2.循环链表

尾节点不指向null，而是指向头结点。好处的进一步统一了对所有节点的操作。

​	3.数组链表

通过数组来实现链表，每个节点保存下一个节点在数组中的索引。

# 三、树结构

​	树结构是一种天然的组织结构，如计算机文件系统、图书馆、公司组织结构等等都是采用的树结构。树结构的优势在于高效，将数据使用树结构存储后，可能会出奇的高效。

​	常见的树结构有：二分搜索树、平衡二叉树（AVL、红黑树）、堆、并查集、线段树、Trie（字典树、前缀树）。

## 3.1 二分搜索树

###3.1.1 概述

​	二叉树：和链表一样，二叉树也是一种动态的数据结构。但是和链表不一样的是，链表只有一个指向下一个节点的引用，而二叉树有两个引用分别指向左节点和右节点。二叉树是最常用的树结构。

​	二叉树具有唯一的一个根节点。在二叉树中，每个节点最多有两个子节点，对于没有子节点的元素，我们称为叶子节点。每个节点只能有一个父节点。整个二叉树中，只有根节点没有父节点。

​	二叉树和链表一样具有天然的递归性，由于链表是一种线性结构，我们可以通过循环来对其进行操作。而对于二叉树而言，递归操作要简单的多。



​	二分搜索树是二叉树的一种，二分搜索树的每个节点的值要大于其左子树上所有节点的值，每个节点的值要小于其右子树上所有节点的值。

​	由于二分搜索树的定义，其存储的元素必须要有可比较性。

### 3.1.2 二分搜索树的实现

```java
/**
 * 二分搜索树（Binary Search Tree） 由于二分搜索树存储的元素必须具有可比较性，所以这里泛型定义必须实现Comparable接口
 *
 * @param <E>
 */
public class BST<E extends Comparable<E>> {

    /**
     * 通过内部类来实现树节点
     */
    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;//根节点
    private int size;//元素数量

    public BST() {
        root = null;
        size = 0;
    }

    /**
     * 获取当前树中元素
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 当前树是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向二分搜索树添加一个新的元素
     *
     * @param e
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 向以node为根节点的二分搜索树插入元素E，递归算法
     *
     * @param node
     * @param e
     */
    private Node add(Node node, E e) {

        //1.递归终止条件
        if (node == null) {
            size++;
            return new Node(e);
        }

        //2.递归调用
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
        return node;
    }

    /**
     * 查看二分搜索树中是否包含元素e
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root,e);
    }

    /**
     * 判断以node为根节点的二分搜索树中是否包含元素e，递归算法
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node,E e){
        //1.递归终止条件
        if(node == null){
            return false;
        }

        if(e.compareTo(node.e) == 0){
            return true;
        //2.递归调用
        }else if(e.compareTo(node.e) < 0){
            return contains(node.left,e);
        }else{
            return contains(node.right,e);
        }
    }

    /**
     * 对二分搜索树进行前序遍历，即先访问根节点，再访问左子树，最后访问右子树
     */
    public void preOrder(){
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的二分搜索树，递归调用
     * @param node
     */
    private void preOrder(Node node){
        if(node == null){
            return;
        }

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 对二分搜索树进行中序遍历，即先访问左子树，再访问根节点，最后访问右子树
     */
    public void inOrder(){
        inOrder(root);
    }

    /**
     * 中序遍历以node为根节点的二分搜索树
     * @param node
     */
    private void inOrder(Node node){
        if(node == null){
            return;
        }

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 对二分搜索树进行后序遍历，即先访问左子树，再访问右子树，最后访问根节点
     */
    public void postOrder(){
        postOrder(root);
    }

    /**
     * 后序遍历以node为根节点的二分搜索树
     * @param node
     */
    private void postOrder(Node node){
        if(node == null){
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);

    }
    

    // 寻找二分搜索树的最小元素
    public E minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty!");

        return minimum(root).e;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 寻找二分搜索树的最大元素
    public E maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty");

        return maximum(root).e;
    }

    // 返回以node为根的二分搜索树的最大值所在的节点
    private Node maximum(Node node){
        if(node.right == null)
            return node;

        return maximum(node.right);
    }

    // 从二分搜索树中删除最小值所在节点, 返回最小值
    public E removeMin(){
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除最大值所在节点
    public E removeMax(){
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    // 删除掉以node为根的二分搜索树中的最大节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node){

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    // 从二分搜索树中删除元素为e的节点
    public void remove(E e){
        root = remove(root, e);
    }

    // 删除掉以node为根的二分搜索树中值为e的节点, 递归算法
    // 返回删除节点后新的二分搜索树的根
    private Node remove(Node node, E e){

        if( node == null )
            return null;

        if( e.compareTo(node.e) < 0 ){
            node.left = remove(node.left , e);
            return node;
        }
        else if(e.compareTo(node.e) > 0 ){
            node.right = remove(node.right, e);
            return node;
        }
        else{   // e.compareTo(node.e) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }


}
```

## 3.2 堆

### 3.2.1 二叉堆

二叉堆是一个完全二叉树。

完全二叉树：对于深度为K的，有n个结点的二叉树，当且仅当其每一个结点都与深度为K的满二叉树中编号从1至n的结点一一对应时称之为完全二叉树。即深度K-1是一个满二叉树，第K层从左到右依次排列。

二叉堆的性质：堆中某个节点的值总是不大于其父节点的值，称为最大堆。对应的还有最小堆，即所有节点都大于等于其父节点。

由于二叉堆的数据都是顺序排列的，所以我们可以用数组来实现二叉堆。

父节点、左子节点、右子节点索引计算：

parent = （i-1）/2

left = i * 2 +1

right = i * 2 + 2

### 3.2.2 最大堆的实现

```java
/**
 * 最大堆
 * @param <E>
 */
public class MaxHeap<E extends Comparable> {

    private Array<E> data;

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    public MaxHeap(E[] arr){
        data = new Array<>(arr);

        //从最后一个非叶子节点开始，依次执行siftDown
        //这种方式的复杂度为O(n)的，而将n个元素插入空堆中的复杂度为O(nlogn)
        for(int i = parent(arr.length - 1) ; i >= 0 ; i--){
            siftDown(i);
        }
    }

    /**
     * 返回堆中元素数量
     * @return
     */
    public int getSize(){
        return data.getSize();
    }

    /**
     * 判断堆是否为空
     * @return
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * 获取索引位置节点对应的父节点索引
     * @param index
     * @return
     */
    private int parent(int index){
        if(index == 0){
            throw new IllegalArgumentException("Index-0 doesn,t have parent.");
        }

        return (index - 1)/2;
    }

    /**
     * 获取index位置元素左孩子的索引
     * @param index
     * @return
     */
    private int leftChild(int index){
        return index*2 + 1;
    }

    /**
     * 获取index位置元素右孩子的索引
     * @param index
     * @return
     */
    private int rightChild(int index){
        return index*2 + 2;
    }

    /**
     * 向堆中添加元素
     * @param e
     */
    public void add(E e){
        data.addLast(e);
        //对新添加的元素进行上浮操作
        siftUp(data.getSize() - 1);
    }

    /**
     * 对元素k进行上浮操作
     * @param k
     */
    private void siftUp(int k){

        //当元素k不是根节点且比其父元素大，将元素k与其父元素互换位置
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0){
            data.swap(k,parent(k));
            k = parent(k);
        }
    }

    /**
     * 查看堆中最大的元素，即根元素
     * @return
     */
    public E findMax(){
        if(data.getSize() == 0){
            throw new IllegalArgumentException("Heap is empty.");
        }

        return data.get(0);
    }

    /**
     * 取出堆中最大的元素
     * @return
     */
    public E extractMax(){

        E ret = findMax();

        //为了保证堆是一个完全二叉树，将末尾的元素和根节点互换位置，再将末尾元素删除
        data.swap(0,data.getSize() - 1);
        data.removeLast();

        //由于上面的互换破坏了最大堆的性质，所以要对换到根节点的元素进行下沉操作
        siftDown(0);

        return ret;
    }

    /**
     * 对k元素进行下沉操作
     * @param k
     */
    private void siftDown(int k){

        //当k的左孩子索引小于size，说明k元素没有左孩子，k元素是一个叶子节点，下沉结束
        while (leftChild(k) < data.getSize()){

            int j = leftChild(k);

            //当k有右孩子，且右孩子大于左孩子
            if(j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0){
                j = rightChild(k);
            }
            //此时data[j]是k的左右孩子的最大值

            //如果k大于等于左右孩子的最大值，下沉结束
            if(data.get(k).compareTo(data.get(j)) >= 0){
                break;
            }

            //如果k小于j，那么k元素与j元素互换
            data.swap(k,j);

            //继续进行下沉操作
            k = j;
        }
    }

    /**
     * 取出堆中最大的元素，再放入一个新的元素
     * @param e
     * @return
     */
    public E replace(E e){
        E ret = findMax();

        //直接将根元素替换为新元素，再对新元素进行siftDown操作
        data.set(0,e);
        siftDown(0);

        return ret;
    }
}
```

## 3.3 线段树（区间树）

### 3.3.1 概述

平衡二叉树：叶子节点之间的深度差距不超过1的二叉树称为平衡二叉树。完全二叉树是一种平衡二叉树。

线段树也是一种平衡二叉树，但是不是完全二叉树。

由于线段树所包含的区间一般是不变的，我们也不考虑给线段树增加和删除元素，只有修改操作，所以我们可以用一个静态数组来实现。

### 3.3.2 线段树的实现

基于静态数组的线段树实现：

```java
/**
 * 基于数组实现线段树
 * 线段树本身是一个平衡二叉树。但不是完全二叉树，更不是满二叉树，我们为了便于操作，将没有元素的节点填充为null，构成一个满二叉树
 * @param <E>
 */
public class SegmentTree<E> {

    private E[] data;
    private E[] tree;
    private Merger<E> merger;//融合器，将左孩子和右孩子融合，由用户自己定义，调用构造方法时传入

    public SegmentTree(E[] arr,Merger<E> merger){
        this.merger = merger;

        data = (E[]) new Object[arr.length];

        for(int i = 0;i < arr.length; i ++){
            data[i] = arr[i];
        }

        //经分析，如果数据量为n，那么线段树的大小在2n~4n之间，所以4n的空间足够放下线段树
        tree = (E[]) new Object[arr.length * 4];

        //构建线段树
        buildSegmentTree(0,0,data.length - 1);
    }

    //在treeIndex位置创建表示区间[l~r]的线段树，递归实现
    private void buildSegmentTree(int treeIndex,int l,int r){
        if(l == r){
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = l + (r - l)/2;
        buildSegmentTree(leftTreeIndex,l,mid);
        buildSegmentTree(rightTreeIndex,mid + 1,r);

        //调用融合器的方法将左孩子和右孩子合并成父节点，该方法有用户根据业务自行实现
        tree[treeIndex] = merger.merger(tree[leftTreeIndex],tree[rightTreeIndex]);
    }

    /**
     * 获取当前树中元素数量
     * @return
     */
    public int getSize(){
        return data.length;
    }

    /**
     * 获取索引位置的元素
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index >= data.length){
            throw new IllegalArgumentException("Index is illegal!");
        }

        return data[index];
    }

    /**
     * 对于一个完全二叉树index元素左孩子的索引
     * @param index
     * @return
     */
    private int leftChild(int index){
        return 2*index + 1;
    }

    /**
     * 对于一个完全二叉树index元素右孩子的索引
     * @param index
     * @return
     */
    private int rightChild(int index){
        return 2*index + 2;
    }

    public E query(int queryL,int queryR){
        if(queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR){
            throw new IllegalArgumentException("Index is illegal.");
        }

        return query(0,0,data.length - 1,queryL,queryR);
    }

    //在以treeIndex为根节点的线段树中[l~r]范围内，搜索区间[queryL~queryR]的值
    private E query(int treeIndex,int l,int r,int queryL,int queryR){

        //当需要查询的区间与treeIndex表示的区间重合，直接返回
        if(l == queryL && r == queryR){
            return tree[treeIndex];
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if(queryL >= mid + 1){
            //要查询的区间完全在右子树中，那么就去右子树中查找
            return query(rightTreeIndex,mid + 1 , r,queryL,queryR);
        }else if(queryR <= mid){
            //要查询的区间完全在左子树中，那么就去左子树中查找
            return query(leftTreeIndex,l,mid,queryL,queryR);
        }

        //要查询的区间在左右子树都有，那么就分别查找，再将结果融合后返回
        E leftResult = query(leftTreeIndex,l,mid,queryL,mid);
        E rightResult = query(rightTreeIndex,mid+1,r,mid+1,queryR);

        return merger.merger(leftResult,rightResult);
    }

    public void set(int index,E e){
        if(index < 0 || index >= data.length){
            throw new IllegalArgumentException("Index is illegal.");
        }

        data[index] = e;
        set(0,0,data.length - 1,index ,e);
    }

    //以treeIndex为根节点[l~r]区间内更新index的值为e
    private void set(int treeIndex,int l,int r,int index,E e){

        //说明找到了叶子节点，直接更新
        if(l == r){
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        //没找到，去左子树或右子树中去找
        if(index >= mid + 1){
            set(rightTreeIndex,mid + 1,r,index,e);
        }else{
            set(leftTreeIndex,l,mid,index,e);
        }

        //重新计算左子树和右子树融合后的值
        tree[treeIndex] = merger.merger(tree[leftTreeIndex],tree[rightTreeIndex]);
    }
}
```

### 3.3.3 复杂度分析

解决区间问题的时候，不考虑增删的情况

| 操作   | 数组   | 线段树     |
| ---- | ---- | ------- |
| 查询   | O(n) | O(logn) |
| 更新   | O(n) | O(logn) |

时间复杂度上，线段树表现更好，但是线段树所需要的空间是4n



除此之外，还需要考虑区间更新、二维线段树、动态线段树等问题的实现。

另外，区间操作相关的还有另外一个重要的数据结构：树状数组。

## 3.4 Trie

​	Trie，称为字典树或前缀树。不属于二叉树，主要用于处理字符串的问题。考虑到不同的语言和不同的使用情景，每个节点包含若干个指向下一个节点的指针。



# 四、集合与映射

## 4.1 set集合

### 4.1.1 基于二分搜索树实现set集合

set集合接口：

```java
/**
 * set集合接口
 * @param <E>
 */
public interface Set<E> {

    void add(E e);
    void remove(E e);
    boolean contains(E e);
    int getSize();
    boolean isEmpty();
}
```

基于二分搜索树的set集合实现

```java
/**
 * 基于二分搜索树实现set集合
 * @param <E>
 */
public class BSTSet<E extends Comparable<E>> implements Set<E>{

    private BST<E> bst;

    public BSTSet(){
        bst = new BST<E>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
```

### 4.1.2 基于链表的set集合实现

```java
public class LinkedListSet<E> implements Set<E>{

    private LinkedList<E> list;

    public LinkedListSet(){
        list = new LinkedList<>();
    }

    @Override
    public void add(E e) {
        if(!list.contains(e)){
            list.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
```

### 4.1.3 复杂度分析

对比链表实现的set集合与二分搜索树的set集合时间复杂度：

| 操作   | 链表   | 二分搜索树（平均） | 二分搜索树（最差） |
| ---- | ---- | --------- | --------- |
| 增    | O（n） | O（logn）   | O（n）      |
| 查    | O（n） | O（logn）   | O（n）      |
| 删    | O（n） | O（logn）   | O（n）      |

### 4.1.4 有序集合和无序集合

​	集合分为有序集合和无序集合，有序集合是指集合中元素具有顺序性，无序集合是指集合中元素不具有顺序性。有序集合基于搜索树实现，而无序集合基于哈希表的实现。

## 4.2 映射Map

### 4.2.1 基于链表实现Map

Map接口：

```java
/**
 * Map接口
 * @param <K>
 * @param <V>
 */
public interface Map<K,V> {

    void add(K key,V value);
    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key,V value);
    int getSize();
    boolean isEmpty();
}
```

基于链表实现Map：

```java
/**
 * 基于链表实现Map
 * @param <K>
 * @param <V>
 */
public class LinkedListMap<K,V> implements Map<K,V> {

    /**
     * 由于是Map，所以每个节点需要保存一个key和一个value
     */
    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(K key,V value,Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key){
            this(key,null,null);
        }

        public Node(){
            this(null,null,null);
        }

        @Override
        public String toString() {
            return key.toString() + ":" + value.toString();
        }
    }

    private Node dummyHead;//虚拟头节点
    private int size;//元素数量

    public LinkedListMap(){
        dummyHead = new Node();
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回key对应的节点
     * @param key
     * @return
     */
    private Node getNode(K key){
        Node cur = dummyHead.next;

        while (cur != null){
            if(cur.key.equals(key)){
                return cur;
            }
            cur = cur.next;
        }

        return null;
    }

    /**
     * 判断Map中是否包含key
     * @param key
     * @return
     */
    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    /**
     * 查询key对应的value
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null?null:node.value;
    }

    /**
     * 添加元素
     * @param key
     * @param value
     */
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);

        if (node == null){
            //如果为空，说明原来不存在key，在链表头添加一个新元素
            dummyHead.next = new Node(key,value,dummyHead.next);
            size ++;
        } else{
            //不为空，已存在key，将key对应的value替换
            node.value = value;
        }
    }

    /**
     * 将key对应的value替换为newValue
     * @param key
     * @param newValue
     */
    @Override
    public void set(K key, V newValue) {
        Node node = getNode(key);

        if(node == null){
            throw new IllegalArgumentException(key + "doesn't exist!");
        }

        node.value = newValue;
    }

    /**
     * 删除key对应的节点
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        Node prev = dummyHead;

        while (prev.next != null){
            if(prev.next.key.equals(key)){
                break;
            }
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            return delNode.value;
        }

        return null;
    }
}
```

### 4.2.2 基于映射实现Map

```java
/**
 * 基于二分搜索树的Map实现
 * @param <K>
 * @param <V>
 */
public class BSTMap<K extends Comparable,V> implements Map<K,V>{

    private class Node{
        public K key;
        public V value;
        public Node left,right;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向Map添加元素
     * @param key
     * @param value
     */
    @Override
    public void add(K key, V value) {
        root = add(root,key,value);
    }

    /**
     * 在以node为根节点的树中添加key-value
     * @param node
     * @param key
     * @param value
     * @return
     */
    private Node add(Node node,K key, V value){
        if(node == null){
            size ++;
            return new Node(key,value);
        }

        if(key.compareTo(node.key) < 0){
            node.left = add(node.left,key,value);
        }else if(key.compareTo(node.key) > 0){
            node.right = add(node.right,key,value);
        }else{
            node.value = value;
        }

        return node;
    }

    /**
     * 返回key所对应的节点
     * @param node
     * @param key
     * @return
     */
    private Node getNode(Node node,K key){
        if(node == null){
            return null;
        }

        if(key.compareTo(node.key) == 0){
            return node;
        }else if(key.compareTo(node.key) < 0){
            return getNode(node.left,key);
        }else{
            return getNode(node.right,key);
        }
    }

    /**
     * 判断是否包含key
     * @param key
     * @return
     */
    @Override
    public boolean contains(K key) {
        return getNode(root,key) != null;
    }

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        Node node = getNode(root,key);
        return node == null?null:node.value;
    }

    /**
     * 将key对应的value替换为newValue
     * @param key
     * @param newValue
     */
    @Override
    public void set(K key, V newValue) {

        Node node = getNode(root,key);

        if(node == null){
            throw new IllegalArgumentException(key + "dosen't exist!");
        }

        node.value = newValue;
    }

    /**
     * 删除key对应的节点
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        Node node = getNode(root,key);

        if(node != null){
            root = remove(root,key);
            return root.value;
        }

        return null;
    }

    private Node remove(Node node,K key){
        if(node == null){
            return null;
        }

        if(key.compareTo(node.key) < 0){
            node.left = remove(node.left, key);
            return node;
        }else if(key.compareTo(node.key) > 0){
            node.right = remove(node.right,key);
            return node;
        }else{
            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }


            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.right = null;
            return successor;
        }
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }
}
```



