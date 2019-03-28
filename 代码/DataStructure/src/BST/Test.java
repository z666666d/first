package BST;

public class Test {

    public static void main(String[] args){
        BST<Integer> bst = new BST<Integer>();

        int[] nums = {5,3,7,8,9,1,0};

        for(int num:nums){
            bst.add(num);
        }

        bst.inOrder();
    }
}
