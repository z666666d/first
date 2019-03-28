package unionFind;

/**
 * 基于树的实现，与一般的树结构不一样，这个树结构是子节点指向父节点
 * 由于每个节点只包含一个指向父节点的引用，所以底层一样可以通过数组来实现
 *
 * 优化：
 *      1.基于size的优化，在合并的时候元素少的树指向元素多的树的根节点，这样可以降低树的深度
 *      缺点：基于树的并查集复杂度与树的深度有关，但是size大的树不一定深度更大
 *
 *      2.基于rank的优化，深度低的树指向深度高的树的根节点
 *
 *      3.路径压缩，对于并查集来说，复杂度与树的深度有关，我们通过降低树的深度的方式，可以提高效率
 *
 */
public class QuickUnion implements UF {

    private int[] parent;
    //private int[] sz;//sz[i]表示以i为根的集合中的元素个数
    private int[] rank;//rank[i]表示以i为根的集合的深度


    public QuickUnion(int size){
        parent = new int[size];
        //sz = new int[size];
        rank = new int[size];

        for(int i:parent){
            parent[i] = i;
            //sz[i] = 1;//初始化的时候所有集合的元素个数都是1
            rank[i] = 1;//初始化时所有树的深度都为1
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    // 查找元素p所在树的根节点
    // 查找操作的时间复杂度为O(h)，h为p元素所在树的深度
    private int find(int p){
        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

//        while (p != parent[p]){
//            //执行路径压缩。让节点p指向其父节点的父节点，从而降低树的深度
//            parent[p] = parent[parent[p]];
//            p = parent[p];
//        }
//        return p;
        while (p != parent[p]){
            //通过递归实现路径压缩，让节点都指向根节点
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    // 判断元素p和元素q是否同属一个集合，即根节点是否一致
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所在的集合
    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot){
            return;
        }

        //parent[pRoot] = qRoot;

        //在合并的时候，元素个数少的树指向元素多的树的根节点
//        if(sz[pRoot] < sz[qRoot]){
//            parent[pRoot] = qRoot;
//            sz[qRoot] += sz[pRoot];
//        }else{
//            parent[qRoot] = pRoot;
//            sz[pRoot] += sz[qRoot];
//        }

        //将rank低的集合合并到rank高的集合上
        if(rank[pRoot] < rank[qRoot]){
            parent[pRoot] = qRoot;
        }else if (rank[qRoot] < rank[pRoot]){
            parent[qRoot] = pRoot;
        }else{
            //当两个树深度相同时，合并后深度将会加1
            parent[qRoot] = pRoot;
            rank[pRoot] += 1;
        }
    }
}
