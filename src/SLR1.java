import First集.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class SLR1 {
    public static Stack<Integer> state_stack = new Stack<>();
    public static Stack<Character> char_stack = new Stack<>();
    public static List<String> gotohead = new ArrayList<>();
    public static List<String> actionhead = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        SlR1生成表 skl1 = new SlR1生成表();
        skl1.main(args);

        gotohead = skl1.gotohead;
        actionhead = skl1.actionhead;

        List<Store> ap = skl1.Ap;
        List<ProductionUnit> production = create_production(skl1.Ap);
        //打印production
        for (ProductionUnit unit : production) {
            System.out.println(unit.left+":"+unit.length);
        }

        //输入录入字符串
        char c;
        List<Character> ch = new ArrayList<>();

        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("输入");
        while (flag&&sc.hasNext()){
            String s = sc.nextLine();
            for (int i = 0;i<s.length();i++){
                c = s.charAt(i);
                if (c=='#'){
                    flag = false;
                    ch.add(c);
                    break;
                }
                ch.add(c);
            }
        }

        System.out.println(ch.toString());
        for (int i = ch.size()-1;i>=0;i--){
            char_stack.push(ch.get(i));
        }

        state_stack.push(0);

        SLR_driver();
    }
    public static int vn_to_int(char ch){
        int i = gotohead.indexOf(ch+"");
        if (i==-1){
            System.out.println("字符错误");
            System.exit(0);
        }
        return i;
    }
    public static int vt_to_int(char ch){
        int i = actionhead.indexOf(ch+"");
        if (i==-1){
            System.out.println("字符错误");
            System.exit(0);
        }
        return i;
    }
    public void error(){
        System.out.println("出现错误");
    }
    public static void init_state_stack(Stack<Integer> state_stack){
        state_stack.clear();
    }
    public static void init_char_stack(Stack<Character> char_stack){
        char_stack.clear();
    }
    public static List<ProductionUnit> create_production(List<Store> AP){
        int index = AP.size();
        List<ProductionUnit> production = new ArrayList<>();
        for (int i=0;i<AP.size();i++){
            Store store = AP.get(i);
            for (String s : store.strings) {
                int len = 0;
                if (!s.equals("ε")){
                    len = s.length();
                }
                ProductionUnit Unit = new ProductionUnit(store.head,len);
                production.add(Unit);
            }
        }
        return production;
    }
}
class ProductionUnit{
    String left;
    int length;
    public ProductionUnit(String left,int length){
        this.left = left;
        this.length = length;
    }
}
