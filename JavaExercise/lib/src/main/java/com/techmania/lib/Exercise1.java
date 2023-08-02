package com.techmania.lib;

import java.util.Scanner;

public class Exercise1 {

    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);

        System.out.println("Enter the First Number : ");
        int n1 = s1.nextInt();

        System.out.println("Enter the Second Number : ");
        int n2 = s1.nextInt();

        float d = (float) n1 / n2;
        float r = n1 % n2;
        System.out.println("n1 / n2 " + d);
        System.out.println("The remainder : " + r);
    }
}