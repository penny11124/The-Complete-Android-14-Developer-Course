package com.techmania.lib;

public class Exercise7 {
    public static void main(String[] args) {
        int[] nums = {8,5,7,2,4,9};
        int even = 0, odd = 0;

        for(int i=0;i<nums.length;i++) {
            if(nums[i]%2 == 0) {
                even++;
            } else {
                odd++;
            }
        }

        System.out.println("The number of even numbers: "+even);
        System.out.println("The number of odd numbers:　"+odd);
    }
}
