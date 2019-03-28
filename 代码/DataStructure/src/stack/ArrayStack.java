package stack;

import array.Array;

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
