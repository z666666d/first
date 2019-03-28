package AVL;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>,V> {

    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public int height;//记录每个节点的高度

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //获得节点的高度值
    private int getHeight(Node node){
        if(node == null){
            return 0;
        }else{
            return node.height;
        }
    }

    //获取节点node的平衡因子
    private int getBalanceFactor(Node node){
        if(node == null){
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    //判断该二叉树是不是一个二分搜索树
    public boolean isBST(){
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root,keys);
        //判断中序遍历后整个集合是否是升序的，如果不是则这个树不是二分搜索树
        for(int i = 0;i < keys.size(); i ++){
            if(keys.get(i-1).compareTo(keys.get(i)) > 0){
                return false;
            }
        }
        return true;
    }

    //中序遍历整棵树，将key依次放入list集合中
    private void inOrder(Node node,ArrayList<K> keys){
        if(node == null){
            return;
        }

        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right,keys);
    }

    // 对元素y进行右旋转，返回新的根节点x
    //       y                     x
    //      / \                  /   \
    //     x  T4   右旋转        z     y
    //    / \      ------->    / \   / \
    //   z  T3                T1 T2 T3 T4
    //  / \
    // T1 T1
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;

        // 向右旋转
        x.right = y;
        y.left = T3;

        // 更新height
        y.height = Math.max(getHeight(y.left),getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left),getHeight(x.right)) + 1;

        return x;
    }

    // 左旋转的方式与右旋转对称
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        //向左旋转过程
        x.left = y;
        y.right = T2;

        // 更新height
        y.height = Math.max(getHeight(y.left),getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left),getHeight(x.right)) + 1;

        return x;
    }
    //判断该树是否是一棵平衡二叉树
    public boolean isBalanced(){
        return isBalanced(root);
    }

    //判断以node为根节点的树是否为平衡二叉树
    private boolean isBalanced(Node node){
        if(node == null){
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1){
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    //添加新元素
    public void add(K key,V value){
        root = add(root,key,value);
    }

    //向以node为根节点的树添加元素
    private Node add(Node node,K key,V value){
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

        //更新节点的height值
        node.height = 1 + Math.max(getHeight(node.left),getHeight(node.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);

        //LL情形
        // 当前node节点的平衡因子大于1且当前节点的左子节点平衡因子大于等于0说明以node为根的树向左倾斜，需要右旋转
        if(balanceFactor > 1 && getBalanceFactor(node.left) >=0 ){
            return rightRotate(node);
        }

        //RR情形
        // 当前node节点的平衡因子小于-1且当前节点的右子节点平衡因子小于等于0说明以node为根的树向右倾斜，需要左旋转
        if(balanceFactor < -1 && getBalanceFactor(node.right) <=0 ){
            return leftRotate(node);
        }

        //LR
        if(balanceFactor > 1 && getBalanceFactor(node.left) < 0 ){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if(balanceFactor < -1 && getBalanceFactor(node.right) > 0 ){
            node.right = rightRotate(node.right);
            return leftRotate(node);
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
    public boolean contains(K key) {
        return getNode(root,key) != null;
    }

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    public V get(K key) {
        Node node = getNode(root,key);
        return node == null?null:node.value;
    }

    /**
     * 将key对应的value替换为newValue
     * @param key
     * @param newValue
     */
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

        //用来保存我们要返回的node，用以平衡维护
        Node retNode;

        if(key.compareTo(node.key) < 0){
            node.left = remove(node.left, key);
            retNode = node;
        }else if(key.compareTo(node.key) > 0){
            node.right = remove(node.right,key);
            retNode = node;
        }else{

            if(node.left == null){
                // 待删除节点左子树为空的情况
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }else if(node.right == null){
                // 待删除节点右子树为空的情况
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }else{
                // 待删除节点左右子树均不为空的情况

                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);

                // 因为removeMin方法中没有添加平衡维护代码，所以我们直接调用remove方法
                //successor.right = removeMin(node.right);
                successor.right = remove(node.right,successor.key);
                successor.left = node.right = null;
                retNode = successor;
            }
        }

        if(retNode == null){
            return retNode;
        }

        //更新节点的height值
        retNode.height = 1 + Math.max(getHeight(retNode.left),getHeight(retNode.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        //LL情形
        // 当前node节点的平衡因子大于1且当前节点的左子节点平衡因子大于等于0说明以node为根的树向左倾斜，需要右旋转
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) >=0 ){
            return rightRotate(retNode);
        }

        //RR情形
        // 当前node节点的平衡因子小于-1且当前节点的右子节点平衡因子小于等于0说明以node为根的树向右倾斜，需要左旋转
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) <=0 ){
            return leftRotate(retNode);
        }

        //LR
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) < 0 ){
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        //RL
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) > 0 ){
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
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
