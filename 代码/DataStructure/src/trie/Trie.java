package trie;

import java.util.TreeMap;

public class Trie {

    //通过内部类来实现Trie的节点
    private class Node{
        public boolean isWord;//到当前节点为止为一个单词
        public TreeMap<Character,Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this.isWord = false;
        }
    }

    private Node root;//根节点
    private int size;//当前存储的单词数量


    public Trie(){
        root = new Node();
        size = 0;
    }

    //获得Trie中存储的单词数量
    public int getSize(){
        return size;
    }

    public void add(String word){
        Node cur = root;

        for(int i = 0;i < word.length(); i++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                cur.next.put(c,new Node());
            }
            cur = cur.next.get(c);
        }

        if(!cur.isWord){
            cur.isWord = true;
            size++;
        }
    }

    // 查询Trie中是否存在单词word
    public boolean contains(String word){
        Node cur = root;
        for(int i = 0;i < word.length(); i++){
            char c = word.charAt(i);

            //如果为null，说明没有这个单词
            if(cur.next.get(c) == null){
                return false;
            }

            cur = cur.next.get(c);
        }

        //循环结束，判断isWord的值，为true就是有这个单词
        return cur.isWord;
    }

    //查询Trie中是否有单词以prefix为前缀
    public boolean isPrefix(String prefix){
        Node cur = root;

        for(int i = 0;i < prefix.length(); i++){
            char c = prefix.charAt(i);

            if(cur.next.get(c) == null){
                return false;
            }

            cur = cur.next.get(c);
        }

        return true;
    }

    //匹配带通配符“.”的字符串
    public boolean match(String word){
        return match(root,word,0);
    }

    //从Node节点开始匹配从index字符以后的word
    private boolean match(Node node,String word,int index){

        if(index == word.length()){
            return node.isWord;
        }

        char c = word.charAt(index);

        if(c != '.'){
            if(node.next.get(c) == null){
                return false;
            }

            return match(node.next.get(c),word,index + 1);
        }else{
            //如果是通配符“.”，则要遍历下面所有节点
            for(char nextChar:node.next.keySet()){
                if(match(node.next.get(nextChar) , word , index + 1)){
                    return true;
                }
            }
            return false;
        }
    }
}

