package array;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        //数组的初始化方式一
        int[] arr1 = new int[10000];
        for (int i : arr1) {
            arr1[i] = i;
        }

        //数组的初始化方式二
        int[] arr2 = {12, 13};

        //数组的初始化方式三
        int[] arr3 = new int[]{123, 456};

        //遍历数组
        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i]);
        }


        Array<Integer> arr = new Array(10000);
        for (int i = 0; i < 5; i++) {
            arr.addLast(i);
        }
        System.out.println(arr.toString());

        arr.insert(1, 5000);
        System.out.println(arr);

        arr.remove(4);
        arr.remove(4);
        arr.remove(0);
        System.out.println(arr);

        arr.removeElement(5000);
        System.out.println(arr);

        int[] arr5 = new int[]{2, 7, 11, 15};

        int[] res = twoSum(arr5, 9);
        System.out.println("[" + res[0] + "," + res[1] + "]");
    }


    public static int[] twoSum(int[] nums, int target) {
        List list = Arrays.asList(nums);
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            Object j = target - nums[i];
            int index = list.indexOf(j);
            System.out.println("index:" + index);
            if (index != -1) {
                result[0] = i;
                result[1] = index;
                return result;
            }
        }
        return result;
    }
}
