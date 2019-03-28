package queue;

import array.Array;

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
