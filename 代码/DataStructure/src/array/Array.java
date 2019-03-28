package array;

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
