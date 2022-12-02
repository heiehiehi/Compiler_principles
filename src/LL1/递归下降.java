package LL1;

import java.util.Scanner;

import static java.lang.System.exit;

public class 递归下降 {
    //S->MH|a
    //H->LSo|ε
    //k->dML|ε
    //L->eHf
    //M->K|bLM
    static String str;
    static int len = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        str = sc.next();
        S();
        if (str.charAt(len) == '#'){
            System.out.println("完成!!");
        }
    }
    public static void S(){
        if (str.charAt(len) == 'a'){
            match('a');
        }
        else if (str.charAt(len)=='o'||str.charAt(len)=='d'||str.charAt(len)=='e'||str.charAt(len)=='b'||str.charAt(len)=='#'){
            M();H();
        }
        else {
            error();
        }
    }
    public static void M(){
        if (str.charAt(len) == 'b'){
            match('b');L();M();
        }
        else if (str.charAt(len)=='o'||str.charAt(len)=='d'||str.charAt(len)=='e'||str.charAt(len)=='#'){
            K();
        }
        else {
            error();
        }
    }
    public static void L(){
        if (str.charAt(len)=='e'){
            match('e');H();match('f');
        }
        else {
            error();
        }
    }
    public static void K(){
        if (str.charAt(len)=='d'){
            match('d');M();L();
        }
        else if (str.charAt(len)=='o'||str.charAt(len)=='e'||str.charAt(len)=='#'){

        }
        else {
            error();
        }
    }
    public static void H(){
        if (str.charAt(len) == 'e'){
            L();S();match('o');
        }
        else if (str.charAt(len) == 'o'||str.charAt(len) == 'f'||str.charAt(len) == '#'){

        }
        else {
            error();
        }
    }
    public static void match(char x){
        if (x == str.charAt(len)){
            len++;
        }
        else {
            error();
        }
    }
    public static void error(){
        System.out.println("错误");
        exit(0);
    }
}
//bef
