package com.techmania.lib;

import java.util.Scanner;

public class Exercise3 {

    public static void main(String[] args) {
        int dec_num, quot, i = 1, j;
        int bin_num[] = new int[100];
        Scanner s1 = new Scanner(System.in);

        System.out.println("Enter the decimal number : ");
        dec_num = s1.nextInt();

        quot = dec_num;
        while (quot != 0) {
            bin_num[i++] = quot%2;
            quot = quot/2;
        }

        System.out.println("Binary number is : ");
        for(j=i-1; j>0; j--) {
            System.out.println(bin_num[j]);
        }

        System.out.println();
    }
}
