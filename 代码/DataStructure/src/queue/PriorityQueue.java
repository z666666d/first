package queue;

import heap.MaxHeap;

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
