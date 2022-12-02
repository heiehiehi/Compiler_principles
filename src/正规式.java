import java.util.Scanner;

public class 正规式 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.length()<4){
            System.out.println("不符合要求");
            return;
        }
        if (str.charAt(0)=='1'){
            String tail = str.substring(str.length()-3);
            if (!tail.equals("101")){
                System.out.println("不符合要求");
                return;
            }
            String mid = str.substring(1,str.length()-3);
            if (mid.equals("")){
                System.out.println("符合要求");
                return;
            }
            for (int i = 0;i<mid.length();i++){
                if (mid.charAt(i)=='1'||mid.charAt(i)=='0'){
                    System.out.println("符合要求");
                    return;
                }
                else {
                    System.out.println("不符合要求");
                    return;
                }
            }
        }
        else {
            System.out.println("不符合要求");
            return;
        }
    }
}
