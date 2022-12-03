import First集.First;
import First集.Firststore;
import First集.Followstore;
import First集.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SlR1生成表 {
    public static char[][] action1;
    public static int[][] action2;
    public static int[][] goto1;
    public static List<Store> Ap = new ArrayList<>();
    public static List<String> gotohead = new ArrayList<>();
    public static List<String> actionhead = new ArrayList<>();

    public static List<Character> arr = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        First firstandfollow = new First();
        firstandfollow.main(args);
        ProduceArr();
        Ap = firstandfollow.Aproduction;

        for (Store store : firstandfollow.Aproduction) {
            System.out.print(store.head+"->");
            for (String i:store.strings){
                System.out.print(i);
                System.out.print("|");
            }
            System.out.println();
        }

        //建立Goto表
        Goto gotolist = new Goto();
        //建立表头
        gotolist.Addhead(firstandfollow.Bproduction);
        //建立Action表
        Action action = new Action();
        //从所有的follow集合first集中获取终结符
        for (Firststore i:firstandfollow.AllFirsts){
            action.Addhead(i.first);
        }

        for (Followstore i:firstandfollow.Allfollows){
            action.Addhead(i.follow);
        }
        for (Store store : Ap) {
            for (String string : store.strings) {
                for (int i = 0;i<string.length();i++){
                    if (gotolist.head.contains(string.charAt(i)+"")){
                        continue;
                    }
                    else {
                        String ss = string.charAt(i)+"";
                        if(arr.contains(string.charAt(i))){
                            if (i+1<string.length()&&arr.contains(string.charAt(i+1))){
                                ss = ss + string.charAt(i+1);
                                i++;
                            }
                        }
                        if (!action.head.contains(ss)){
                            action.head.add(ss);
                        }
                    }
                }
            }

        }


        //创建I0
        UntiIlist I0 = new UntiIlist();
        //创建集合
        IList AllIn = new IList();
        //扩展文法
//        Singerdata singer = new Singerdata();
//        singer.head = "S";
//        singer.content = "·"+firstandfollow.Aproduction.get(0).head;
//        I0.Add(singer);
//        firstandfollow.Bproduction.add("S");

        //从之前的Ap里面读取
        for (Store store : firstandfollow.Aproduction) {
            for (String i:store.strings){
                Singerdata singerdata = new Singerdata();
                //设置单行头
                singerdata.head = store.head;
                if (i.equals("ε")){
                    singerdata.content = "·";
                }
                else {
                    singerdata.content = "·"+i;
                }
                I0.Add(singerdata);
            }
        }
        //将IO加入AllIn
        AllIn.Addnew(I0);
        //显示I0
        for (Singerdata singerdata : I0.In) {
            System.out.println(singerdata.head+"->"+singerdata.content);
        }
        //开始循环


        getIn(AllIn,gotolist,action);


        for (int i=0;i<AllIn.list.size();i++){
            System.out.println();
            System.out.println("I"+i);
            UntiIlist untiIlist = AllIn.list.get(i);
            for (Singerdata singerdata : untiIlist.In) {
                System.out.println(singerdata.head+"->"+singerdata.content);
            }
            System.out.println("路径");
            for (dir dirs : untiIlist.wheregoal) {
                System.out.println("I"+i+"->"+dirs.road+"->"+"I"+dirs.goal);
            }
        }

        //打印Goto表头和Action表头
        gotolist.head.remove("S");

        //分析action表和goto表
        AnalyzeTable(action,gotolist,AllIn,firstandfollow.Allfollows,I0);

        //生成action1表
        action1 = ProduceAction1(action);
        action2 = ProduceAction2(action);
        goto1 = ProduceGoto(gotolist);

        System.out.println();
        for (int i = 0;i<action1.length;i++){
            System.out.println(Arrays.toString(action1[i]));
        }
        for (int i = 0;i<action2.length;i++){
            System.out.println(Arrays.toString(action2[i]));
        }
        for (int i = 0;i<goto1.length;i++){
            System.out.println(Arrays.toString(goto1[i]));
        }
        //获取到了其中三个表

        gotohead = gotolist.head;
        actionhead = action.head;


    }
    public static void ProduceArr(){
        for (int i=0;i<10;i++){
            arr.add((char)('0'+i));
        }
    }
    public static int[][] ProduceGoto(Goto l){
        int n = l.lists.size();
        int m = l.head.size();
        int gotolist[][] = new int[n][m];
        List<String[]> lists1 = l.lists;
        for (int i = 0;i<gotolist.length;i++){
            Arrays.fill(gotolist[i],-1);
        }
        for (int i = 0;i<lists1.size();i++){
            String[] strings = lists1.get(i);
            for (int j = 0;j<strings.length;j++){
                String str = strings[j];
                if (str==null){
                    continue;
                }
                int value = Integer.valueOf(str);
                gotolist[i][j] = value;
            }
        }
        return gotolist;


    }
    public static int[][] ProduceAction2(Action action){
        int n = action.lists.size();
        int m = action.head.size();
        int action2[][] = new int[n][m];
        List<String[]> lists = action.lists;
//        setde(action1,'0');
        for (int i = 0;i<action2.length;i++){
            Arrays.fill(action2[i],-1);
        }
        for (int i = 0;i<lists.size();i++){
            String[] strings = action.lists.get(i);
            for (int j = 0;j<strings.length;j++){
                String str = strings[j];
                if (str==null){
                    continue;
                }
                str = str.replace(" ","");
                int value = -1;
                if (str.charAt(0)=='S'){
                    value = Integer.valueOf(str.substring(1));
                    action2[i][j] = value;
                }
                if (str.charAt(0)=='R'){
                    value = Integer.valueOf(str.substring(1));
                    action2[i][j] = value;
                }
                if (str.equals("acc")){
                    continue;
                }
            }
        }
        return action2;


    }
    public static char[][] ProduceAction1(Action action){
        int n = action.lists.size();
        int m = action.head.size();
        char action1[][] = new char[n][m];
        List<String[]> lists = action.lists;
//        setde(action1,'0');
        for (int i = 0;i<action1.length;i++){
            Arrays.fill(action1[i],'0');
        }
        for (int i = 0;i<lists.size();i++){
            String[] strings = action.lists.get(i);
            for (int j = 0;j<strings.length;j++){
                String str = strings[j];
                if (str==null){
                    continue;
                }
                if (str.charAt(0)=='S'){
                    action1[i][j] = 's';
                }
                if (str.charAt(0)=='R'){
                    action1[i][j] = 'r';
                }
                if (str.equals("acc ")){
                    action1[i][j] = 'a';
                }
            }
        }
        return action1;


    }
    public static void setde(char a[][],char b){
        for (int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length;j++){
                a[i][j] = b;
            }
        }
    }
    public static void AnalyzeTable(Action action,Goto gotolist,IList AllIn,List<Followstore> follow,UntiIlist StartI0){
        //打印表头
        System.out.print("In"+"  ");
        for (String s : action.head) {
            System.out.print(s+"  ");
        }
        System.out.print("|  ");
        for (String s : gotolist.head) {
            System.out.print(s+" ");
        }

        //求表中内容
        for (UntiIlist ilist : AllIn.list) {
            //求action内容
            String alist[] = new String[action.head.size()];
            //求goto内容
            String glist[] = new String[gotolist.head.size()];

            //判断是否终结
            for (Singerdata singerdata : ilist.In) {
                Singerdata singerdata1 = StartI0.In.get(0);
                if (equalsSingerdata(singerdata1,singerdata)){
                    int index = singerdata.content.indexOf("·");
                    if (index==singerdata.content.length()-1){
                        //说明点在S的最后一位上
                        int index2 = action.head.indexOf("#");
                        alist[index2] = "acc ";
                    }
                }
            }

            for (dir d : ilist.wheregoal) {//获取每条路径
                //判断这个路径是不是在action中
                if (action.head.contains(d.road)){
                    //如果是将对应的录入
                    int index = action.head.indexOf(d.road);
                    alist[index] = "S"+d.goal;
                }
                if (gotolist.head.contains(d.road)){
                    int index = gotolist.head.indexOf(d.road);
                    glist[index] = d.goal+"";
                }
            }


            //判断是否为最终态
            for (Singerdata singerdata : ilist.In) {
                //获取这里面内容
                //获取该是在StartI0中的第几个
                int index1 = singerdata.content.indexOf("·");
                if (index1 == singerdata.content.length()-1&&!singerdata.head.equals("S")){
                    int index = getIDbyList(singerdata,StartI0);
                    for (Followstore followstore : follow) {
                        if (singerdata.head.equals(followstore.head)){
                            //如果找到了匹配的follow集
                            for (String s : followstore.follow) {
                                //获取follow集中的元素
                                int index2 = action.head.indexOf(s);
                                alist[index2] = "R"+index;
                            }

                        }
                    }
                }

            }
            action.lists.add(alist);
            gotolist.lists.add(glist);
        }

        for (int i = 0;i<action.lists.size();i++){
            String[] astrings = action.lists.get(i);
            String[] gstrings = gotolist.lists.get(i);
            System.out.println();
            System.out.print("I"+i+" ");
            for (int j = 0;j<astrings.length;j++){
                String mid = astrings[j];
                if (mid==null){
                    System.out.print("// ");
                }
                else {
                    System.out.print(mid+" ");
                }
            }
            System.out.print("| ");
            for (int j = 0;j<gstrings.length;j++){
                String mid = gstrings[j];
                if (mid==null){
                    System.out.print("/ ");
                }
                else {
                    System.out.print(mid+" ");
                }
            }
        }

    }
    public static int getIDbyList(Singerdata singerdata,UntiIlist ilist){
        int i = 0;
        for (Singerdata singerdata1 : ilist.In) {
            if (singerdata.head == singerdata1.head){
                String str1 = singerdata.content.replace("·","");
                String str2 = singerdata1.content.replace("·","");
                if (str1.equals(str2)){
                    return i;
                }
            }
            i++;
        }
        return i;

    }
    public static boolean equalsSingerdata(Singerdata s1,Singerdata s2){
        if (s1.head == s2.head){
            String str1 = s1.content.replace("·","");
            String str2 = s2.content.replace("·","");
            if (str1.equals(str2)){
                return true;
            }
        }
        return false;
    }
    public static void getIn(IList Allin,Goto gotolist, Action action){

        UntiIlist StartI0 = Allin.list.get(0);
        for (int i = 0;i<Allin.list.size();i++){//从I0开始读取，在循环中会不断增加size，直到未有新的元素加入
            UntiIlist nowIn = Allin.list.get(i);//获取当前第N个I
            //判断是否是终结


            //获取action
            for (String s : action.head) {//先进行对
                deepgetIn(nowIn,s,Allin,StartI0,gotolist);
            }

            //获取goto
            for (String s : gotolist.head) {
                deepgetIn(nowIn,s,Allin,StartI0,gotolist);
            }
        }



    }
    public static void deepgetIn(UntiIlist nowIn ,String head,IList Allin,UntiIlist StartI0,Goto gotolist){
        UntiIlist Ins = new UntiIlist();
        for (Singerdata singerdata : nowIn.In) {
            String before = singerdata.head;//获取头部
            String str = singerdata.content;//获取内容

            //获取点所在的位置
            int index = str.indexOf('·');
            if (index>=str.length()-1){
                continue;
            }
            String next = str.charAt(index+1)+"";//获取下一个字符 但是无法判断是不是数字。读两位以上
            int p = 1;
            if (arr.contains(str.charAt(index+1))&&index+2<str.length()){//判断是否为数字
                if (arr.contains(str.charAt(index+2))){
                    p = 2;
                    next = next + str.charAt(index+2);
                }
            }
            if (next.equals(head)){
                str = str.replace("·","");
                StringBuffer mid = new StringBuffer(str);
                mid.insert(index+p,'·');
                str = mid.toString();

                Singerdata newdata = new Singerdata();
                newdata.head = before;
                newdata.content = str;
                Ins.Add(newdata);
            }
        }
        //在替换点后，需要进行一次递归判断
        deepjuice(StartI0,Ins,gotolist);
        //首先判断Ins里面有没有数据

        if (Ins.In.size()>0){
            dir newroad = new dir();
            newroad.road = head;
            if (!Allin.exist(Ins)){
                newroad.goal = Allin.list.size();
                Allin.list.add(Ins);
            }
            else {
                newroad.goal = getAllInindex(Allin.list,Ins);
            }
            nowIn.AddnewRoad(newroad);
        }
    }
    public static int getAllInindex(List<UntiIlist> list,UntiIlist Ins){
        //  判断是哪个下标
        int ans = 0;
        for (int i = 0;i<list.size();i++){
            UntiIlist mubiao = list.get(i);//获取
            if (mubiao.In.size()==Ins.In.size()){
                boolean have = true;
                for (Singerdata singerdata : Ins.In) {
                    if (!ishave(mubiao.In,singerdata)){//判断是否包含，如果全部包含就是true
                        have = false;
                    }
                }//满足数目相等，切全部包含，就说明两个相等，为true
                if (have){
                    ans = i;
                    break;
                }
            }
        }
        return ans;

    }
    static boolean ishave(List<Singerdata> In,Singerdata singerdata){
        boolean flag = false;//本来是不包含的
        for (Singerdata singerdata1 : In) {
            if (singerdata.head.equals(singerdata1.head)&&singerdata.content.equals(singerdata1.content)){
                flag = true;
            }
        }
        return flag;
    }
    public static void deepjuice(UntiIlist StartI0,UntiIlist nowIn,Goto gotolist){
        for (int i = 0;i<nowIn.In.size();i++) {
            Singerdata singerdata = nowIn.In.get(i);
            String before = singerdata.head;//获取头部
            String str = singerdata.content;//获取内容

            //获取点所在的位置
            int index = str.indexOf('·');
            if (index>=str.length()-1){
                continue;
            }
            String next = str.charAt(index+1)+"";//获取下一个字符
            if (gotolist.head.contains(next)){
                //判断下一个是否存在，如果存在的话，将在I0对应的加入其中
                //先进行循环，把head等于next的获取
                for (Singerdata singerdata1 : StartI0.In) {
                    if (singerdata1.head.equals(next)){
                        //如果其中的元素的头部等于next
                        //那么将这个元素继续加入nowIn
                        if (!nowIn.In.contains(singerdata1)){
                            nowIn.Add(singerdata1);
                        }
                    }
                }

            }
        }
    }
}
class Goto{
    List<String> head;
    List<String[]> lists = new ArrayList<>();
    void AddNewdata(String[] arr){
        lists.add(arr);
    }
    void Addhead(List<String> arr){
        head = new ArrayList<>(arr);
    }
}
class Action{
    List<String> head = new ArrayList<>();
    List<String[]> lists = new ArrayList<>();
    void AddNewdata(String[] arr){
        lists.add(arr);
    }
    void Addhead(List<String> arr){
        for (String str : arr){
            if (!head.contains(str)){
                if (!str.equals("ε")){
                    head.add(str);
                }
            }
        }
    }
}
class IList{
    List<UntiIlist> list = new ArrayList<>();
    void Addnew(UntiIlist l){
        list.add(l);
    }
    boolean exist(UntiIlist In){
        boolean flag = false;
        for (UntiIlist untiIlist : list) {
            if (untiIlist.In.size()==In.In.size()){
                boolean have = true;
                for (Singerdata singerdata : In.In) {
                    if (!ishave(untiIlist.In,singerdata)){//判断是否包含，如果全部包含就是true
                        have = false;
                    }
                }//满足数目相等，切全部包含，就说明两个相等，为true
                if (have){
                    flag = true;
                }
            }
        }
        return flag;
    }
    boolean ishave(List<Singerdata> In,Singerdata singerdata){
        boolean flag = false;//本来是不包含的
        for (Singerdata singerdata1 : In) {
            if (singerdata.head.equals(singerdata1.head)&&singerdata.content.equals(singerdata1.content)){
                flag = true;
            }
        }
        return flag;
    }

}
class UntiIlist{
    List<dir> wheregoal = new ArrayList<>();
    List<Singerdata> In = new ArrayList<>();


    void setIn(List<Singerdata> in) {
        In = in;
    }
    public void Add(Singerdata singerdata) {
        In.add(singerdata);
    }
    public void AddnewRoad(dir goal){
        wheregoal.add(goal);
    }
}
class Singerdata{
    String head;
    String content;
}

class dir{
    String road;
    int goal;
}
