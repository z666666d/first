package RB;

public class RBTree<K extends Comparable<K>,V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public boolean color;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public RBTree(){
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

    //判断节点node的颜色
    private boolean isRed(Node node){
        if(node == null){
            return BLACK;
        }
        return node.color;
    }

    private Node leftRotate(Node node){
        Node x = node.right;

        //左旋转
        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    private Node rightRotate(Node node){
        Node x = node.left;

        //右旋转
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    private void flipColors(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 向红黑树添加一个新的元素
     *
     * @param key,value
     */
    public void add(K key,V value) {
        root = add(root, key,value);
        root.color = BLACK;//红黑树根节点必为黑色
    }

    /**
     * 向以node为根节点的红黑树插入元素，递归算法
     *
     * @param node
     * @param key,value
     */
    private Node add(Node node, K key,V value) {

        //1.递归终止条件
        if (node == null) {
            size++;
            return new Node(key,value);
        }

        //2.递归调用
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key,value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right,  key,value);
        }else{
            node.value = value;
        }

        //三步操作实现平衡--左旋转、右旋转、颜色翻转

        // 右子节点为红色且左子节点不为红色，这时需要先进行左旋转
        if(isRed(node.right) && !isRed(node.left)){
            node = leftRotate(node);
        }

        // 左子节点为红色、左子节点的左子节点也为红色，需要进行右旋转
        if(isRed(node.left) && isRed(node.left.left)){
            node = rightRotate(node);
        }

        if(isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        return node;
    }
}
