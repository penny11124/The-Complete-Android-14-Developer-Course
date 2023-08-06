package com.techmania.lib;

public class Exercise6 {
    public static void main(String[] args){
        int[] arr1 = {1,3,-5,4};
        int[] arr2 = {1,4,-5,-2};
        String result="";

        for(int i=0;i<arr1.length;i++){
            result+=arr1[i]*arr2[i]+" ";
        }
        System.out.println("Result: " +result);
    }
}
