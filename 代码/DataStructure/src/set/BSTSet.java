package set;

import BST.BST;

/**
 * 基于二分搜索树实现set集合
 * @param <E>
 */
public class BSTSet<E extends Comparable<E>> implements Set<E>{

    private BST<E> bst;

    public BSTSet(){
        bst = new BST<E>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
