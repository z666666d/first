package map;

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
            size --;
            return delNode.value;
        }

        return null;
    }
}
