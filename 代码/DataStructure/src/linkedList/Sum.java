package linkedList;

public class Sum {
    public static int sum(int[] arr){
        return sum(arr,0);
    }


    /**
     * 计算从arr[l]到arr[length]的和
     * @param arr
     * @param l
     * @return
     */
    private static int sum(int[] arr,int l){
        if(l == arr.length){
            return 0;
        }
        return arr[l] + sum(arr,l+1);
    }
    
    public static void main(String[] args){
        int[] nums = {1,2,2,2,3,4,5};
        System.out.println(sum(nums));
    }
}
