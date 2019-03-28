package stack;

import java.util.Stack;

/**
 * 通过栈实现括号验证
 */
public class Solution {
    public static boolean isValid(String s){
        Stack<Character> stack = new Stack<>();
        for(int i =0;i < s.length();i++){
            char c = s.charAt(i);
            if(c == '{' || c == '['||c == '('){
                stack.push(c);
            }else {
                if(stack.isEmpty()){
                    return false;
                }

                char topChar = stack.pop();
                if(c == '}' && topChar != '{'){
                    return false;
                }
                if(c == ']' && topChar != '['){
                    return false;
                }
                if(c == ')' && topChar != '('){
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
    
    public static void main(String[] args){
        boolean b = isValid("{[(]}");
        System.out.println(b);
    }
}
