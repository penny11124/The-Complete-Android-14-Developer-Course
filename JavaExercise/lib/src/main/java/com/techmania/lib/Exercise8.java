package com.techmania.lib;

import java.util.Scanner;

public class Exercise8 {
    public static void main(String[] args) {
        System.out.println("Please enter a number: ");
        Scanner s1 = new Scanner(System.in);
        int n = s1.nextInt();

        for(int i=1;i<=n;i++) {

            for(int j=0;j<n-i+4;j++) {
                System.out.print(" ");
            }
            for(int k=0;k<i;k++) {
                System.out.print(i+" ");
            }
            System.out.print("\n");
        }
    }
}
