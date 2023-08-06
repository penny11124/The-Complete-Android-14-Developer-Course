package com.techmania.lib;

import java.util.Scanner;

public class Exercise9 {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.println("Please enter the number of Rows: ");
        int row = s1.nextInt();
        System.out.println("Please enter the number of Columns: ");
        int col = s1.nextInt();

        int sum[][] = new int[row][col];

        System.out.println("Enter the elements of 1st matrix: ");
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                sum[i][j] = s1.nextInt();
            }
        }
        System.out.println("Enter the elements of 2nd matrix: ");
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                sum[i][j] += s1.nextInt();
            }
        }
        System.out.println("Sum of 2 matrix: ");
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                System.out.print(sum[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
