package segment;

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
