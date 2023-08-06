package com.techmania.lib;

public class Exercise10 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,7,8,2,9,4,5};
        int sum = 0;
        for(int i=0;i<nums.length;i++) {
            sum+=nums[i];
        }
        double avr = (double) sum/nums.length;
        System.out.println("The average is: "+avr);
    }
}
