package linkedList;

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
