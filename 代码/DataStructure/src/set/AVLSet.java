package set;

import AVL.AVLTree;

public class AVLSet<E extends Comparable<E>> implements Set<E> {

    //由于AVLTree底层是支持key-value类型的数据的，而我们的set集合不是键值对，所以我们只保存一个值，value传null即可
    private AVLTree<E,Object> avl;

    public AVLSet(){
        avl = new AVLTree<>();
    }

    @Override
    public int getSize() {
        return avl.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avl.isEmpty();
    }

    @Override
    public void add(E e) {
        avl.add(e,null);
    }

    @Override
    public boolean contains(E e) {
        return avl.contains(e);
    }

    @Override
    public void remove(E e) {
        avl.remove(e);
    }
}
