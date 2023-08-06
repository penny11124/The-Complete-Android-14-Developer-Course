package com.techmania.lib;

import java.util.Scanner;

public class Exercise5 {
    public static void main(String[] args){
        Scanner s1 = new Scanner(System.in);
        System.out.println("Input the String to reverse");

        char[] letters = s1.nextLine().toCharArray();
        System.out.println("The reversed string: ");

        for(int i=letters.length-1;i>-1;i--) {
            System.out.print(letters[i]);
        }
        System.out.print("\n");
    }
}
