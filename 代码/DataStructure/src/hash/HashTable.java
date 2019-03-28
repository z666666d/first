package hash;

import java.util.TreeMap;

public class HashTable<K,V> {

    private TreeMap<K,V>[] hashtable;

    //与静态数组一样，固定空间大小是有缺陷的，所以我们需要根据情况对地址空间进行resize
    private static final int upperTol = 10;//平均每个地址下元素达到10个，即进行扩容
    private static final int lowerTol = 2;//平均每个地址下元素低于2个，即进行缩容
    private static final int initCapacity = 7;//初始空间大小

    private int M;
    private int size;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashtable = new TreeMap[M];
        for (int i = 0; i < M; i ++){
            hashtable[i] = new TreeMap<>();
        }
    }

    public HashTable(){
        this(initCapacity);
    }

    private int hash(K key){
        // & 0x7fffffff 是为了消除符号位，将所有hashCode转为正数
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    public void add(K key,V value){
        TreeMap<K,V> map = hashtable[hash(key)];
        if(map.containsKey(key)){
            //已存在，修改
            map.put(key,value);
        }else{
            //不存在，新增
            map.put(key,value);
            size ++;

            //在新增元素后，判断是否需要扩容
            if(size >= upperTol * M){
                resize(2 * M);
            }
        }
    }

    public V remove(K key){
        TreeMap<K,V> map = hashtable[hash(key)];
        V ret = null;
        if(map.containsKey(key)){
            ret = map.remove(key);
            size --;

            //删除元素后，判断是否需要缩容
            if(size < lowerTol * M && M/2 >= initCapacity){
                resize(M / 2);
            }
        }
        return ret;
    }

    public void set(K key,V value){
        TreeMap<K,V> map = hashtable[hash(key)];
        if(!map.containsKey(key)){
            throw new IllegalArgumentException(key + "doesn's exist!");
        }

        map.put(key,value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }

    private void resize(int newM){
        //新建一个newM大小的TreeMap数组
        TreeMap<K,V>[] newHashTable = new TreeMap[newM];
        for(int i = 0;i < newM;i ++){
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;
        for(int i = 0;i < oldM;i ++){
            TreeMap<K,V> map = newHashTable[i];
            for(K key:map.keySet()){
                newHashTable[hash(key)].put(key,map.get(key));
            }
        }

        this.hashtable = newHashTable;
    }
}
