package LL1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;

public class LL {
    //1 S->MH
    //2 S->a
    //3 H->LSo
    //4 H->ε
    //5 k->dML
    //6 k->ε
    //7 L->eHf
    //8 M->bLM
    //9 M->K
    static int[][] LL = {{2,1,1,1,-1,1,1},
                        {-1,9,9,9,-1,8,9},
                        {-1,4,-1,3,4,-1,4},
                        {-1,-1,-1,7,-1,-1,-1},
                        {-1,6,5,6,-1,-1,6}};
    static Stack<Character> input = new Stack<>();
    static Stack<Character> sem = new Stack<>();

    static List<Character> St = new ArrayList<>();
    static List<Character> It = new ArrayList<>();

    public static void init(){
        St.add('S');
        St.add('M');
        St.add('H');
        St.add('L');
        St.add('K');

        It.add('a');
        It.add('o');
        It.add('d');
        It.add('e');
        It.add('f');
        It.add('b');
        It.add('#');
    }

    public static void main(String[] args) {
        init();
        System.out.println("输入一个字符串");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        for (int i = str.length()-1;i>=0;i--){
            input.push(str.charAt(i));
        }
        sem.push('#');
        sem.push('S');
        LL_driver();
    }
    public static void LL_driver(){
        int k;
        char is,ss;
        is = input.peek();
        ss = sem.peek();
        while (ss!='#'){
            if (is_vt(ss)){
                if (is == ss){
                    input.pop();
                    sem.pop();
                }
                else {
                    error();
                }
            }
            else {
                k = LL[vn_to_int(ss)][vt_to_int(is)];
                switch (k){
                    case 1: sem.pop();sem.push('H'); sem.push('M'); break;
                    case 2: sem.pop();sem.push('a');break;
                    case 3: sem.pop();sem.push('o');sem.push('S');sem.push('L');break;
                    case 4: sem.pop();break;
                    case 5: sem.pop();sem.push('L');sem.push('M');sem.push('d');break;
                    case 6: sem.pop();break;
                    case 7: sem.pop();sem.push('f');sem.push('H');sem.push('e'); break;
                    case 8: sem.pop();sem.push('M');sem.push('L');sem.push('b');break;
                    case 9: sem.pop();sem.push('K');break;
                    case -1:error();
                }
            }
            is = input.peek();
            ss = sem.peek();
        }

        if (is == '#' && ss == '#'){
            System.out.println("可接受的");
        }
        else {
            error();
        }
    }
    public static int vn_to_int(char ch){
        int i = 0;
        if (St.contains(ch)){
            i = St.indexOf(ch);
        }
        else {
            error();
        }
        return i;
    }
    public static int vt_to_int(char ch){
        int i = 0;
        if (It.contains(ch)){
            i = It.indexOf(ch);
        }
        else {
            error();
        }
        return i;
    }
    public static boolean is_vt(char ch){
        if (St.contains(ch)){
            return false;
        }
        else if(It.contains(ch)){
            return true;
        }
        else {
            error();
        }
        return true;
    }
    public static void error(){
        System.out.println("错误");
        exit(0);
    }
}
