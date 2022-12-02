import java.util.Scanner;

public class zhidongji {
    public static void main(String[] args) {
        int a = 0;
        int b = 1;
        int start = 0;
        int arr[][] = {{1,2},{1,3},{1,2},{1,4},{1,4}};
        int stauts = start;
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int len = str.length();
        int i  = 0;
        while (i<len){
            if (str.charAt(i)=='a'){
                stauts = arr[stauts][a];
            }
            else {
                stauts = arr[stauts][b];
            }
            i++;
            System.out.println("状态:"+stauts);
        }
        if (stauts!=4){
            System.out.println("无法到达终态");
        }
        else {
            System.out.println("可以到达终态");
        }
    }
}
