package unionFind;

/**
 * 第一版并查集，通过数组实现并查集，查询操作较快，一般不这样实现
 */
public class QuickFind implements UF {

    private int[] id;

    public QuickFind(int size){
        id = new int[size];

        for(int i = 0; i < id.length; i++){
            id[i] = i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    // 查找元素p所属的集合编号
    private int find(int p){
        if(p < 0 || p >= id.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        return id[p];
    }

    // 查看元素p和元素q是否属于同一个集合，时间复杂度为O(1)
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所在的集合 这个操作需要遍历整个数组，所以时间复杂度为O(n)
    @Override
    public void unionElements(int p, int q) {
        int pID = find(p);
        int qID = find(q);

        //如果元素p和元素q所在的集合编号一样，那么本来就在同一个集合中，不需要再合并
        if(pID == qID){
            return;
        }

        // 遍历数组，将与p元素同一集合的所有元素修改为和q元素集合编号相同。实现合并
        for(int i = 0 ; i < id.length ; i++){
            if(id[i] == pID){
                id[i] = qID;
            }
        }
    }
}
