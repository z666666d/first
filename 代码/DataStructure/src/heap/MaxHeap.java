package heap;

import array.Array;

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
