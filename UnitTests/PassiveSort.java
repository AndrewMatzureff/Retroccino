package UnitTests;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Write a description of class PassiveSort here.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/19/2019)
 */
public class PassiveSort
{
    static int index = 0;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String line = "";
        ArrayList<Integer> ints = new ArrayList<Integer>();
        while(line != null){
            line = in.nextLine();
            switch(line.toLowerCase()){
                case "quit":
                    return;
                case "scramble":
                    scramble(ints);
                    break;
                case "reverse":
                    reverse(ints);
                    break;
                default:
                    int n = 0;
                    try{
                        n = Integer.parseInt(line);
                        ints.add(n);
                    }catch(NumberFormatException e){}
                    break;
            }
            sort(ints, index++);
            print(ints);
        }
    }
    public static void sort(ArrayList<Integer> a, int i){
        i %= a.size();
        if(i + 1 < a.size() && a.get(i + 1) > a.get(i))
            swap(a, i, i + 1);
    }
    public static void print(ArrayList<Integer> a){
        StringBuilder sb = new StringBuilder();
        for(int i = a.size() - 1; i >= 0; i--){
            sb.append(" ");
            sb.append(a.get(i));
        }
        System.out.println(sb);
    }
    public static void swap(ArrayList<Integer> a, int i, int j){
        Integer t = a.get(i);
        a.set(i, a.get(j));
        a.set(j, t);
    }
    public static void scramble(ArrayList<Integer> a){
        int i = 0, j = a.size() - 1;
        while(i <= j){
            int ri = i + (int)(Math.random() * a.size());
            int rj = j + (int)(Math.random() * a.size());
            swap(a, i++, rj % a.size());
            swap(a, j--, ri % a.size());
        }
    }
    public static void reverse(ArrayList<Integer> a){
        int i = 0, j = a.size() - 1;
        while(i < j){
            swap(a, i++, j--);
        }
    }
}
