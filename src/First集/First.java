package First集;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class First {
    public static List<String> Bproduction = new ArrayList<>();
    public static List<Store> Aproduction = new ArrayList<>();

    public static  List<Firststore> AllFirsts = new ArrayList<>();
    public static  List<Followstore> Allfollows = new ArrayList<>();
    static List<Character>  arr = new ArrayList<>();

    public static void main(String[] args) {
//        System.out.print(delgoalindex("123456",5));
        Scanner sc = new Scanner(System.in);
        List<String> strlist = new ArrayList<>();
        while(true){
            String mid = sc.nextLine();
            if (mid.equals("end")){
                break;
            }
            strlist.add(mid);
        }


        addnew(strlist);
        int count = Bproduction.size();//记录原本的有多少个

        List<Store> aproduction = getcopyA(Aproduction);
        List<String> bproduction = new ArrayList<>(Bproduction);
        DeleLeft();

        GetFirst();
        Aproduction = aproduction;
        getfollow();

        for (Firststore i:AllFirsts){
            System.out.print(i.head+"的First集为 ");
            System.out.println(i.first.toString());
        }

        for (Followstore i:Allfollows){
            System.out.print(i.head+"的Follow集为 ");
            System.out.println(i.follow.toString());
        }


        Bproduction = bproduction;



    }
    public static List<Store> getcopyA(List<Store> A){
        List<Store> newA = new ArrayList<>();
        for (Store store : A) {
            Store news = new Store(store.index,store.head,store.strings);
            newA.add(news);
        }
        return newA;

    }
    public static void getfollow(){
        int f = 0;
        for (String i:Bproduction){
            Allfollows.add(new Followstore(f,i));
        }

        Allfollows.get(0).AddNewFollow("#");

        for (Store i:Aproduction){
            int fl = i.index;
            for (String str:i.strings){//获取语句后面的如TG
                //获取到str后，进入判断
                singleju(str,fl);
            }
        }

        //防止有些还未获取成功，重复五遍
        for (int i = 0;i<5;i++){
            for (Followstore g:Allfollows){//获取语句后面的如TG
                //获取到str后，进入判断
                updatefollow(g);
            }

        }


    }
    public static void updatefollow(Followstore follow){
        for (int i = 0;i<follow.wait.size();i++){
            int getfollow = follow.wait.get(i);
            follow.AddNewFollowlist(Allfollows.get(getfollow).follow);
        }
    }

    public static void singleju(String str,int fl){
        for (int k=0;k<str.length();k++){//获取字符串每个字符
            if (Bproduction.contains(str.charAt(k)+"")){
                //获取当前的follow级
                //获取后一个符
                String fu = str.charAt(k) + "";
                if(k+1<str.length()){
                    if ((str.charAt(k+1)+"").equals("'")){
                        fu = str.charAt(k) +""+ str.charAt(k+1);
                        k++;
                    }
                }

                int i1 = Bproduction.indexOf(fu);
                Followstore nowfollow = Allfollows.get(i1);
                if (k==str.length()-1){//判断是不是最后一个字符
                    //是最后一个字符的情况

                    //如果是的话，把前面的Follow加上
                    List<String> follow = new ArrayList<>(Allfollows.get(fl).follow);//获取之前的follow集
                    nowfollow.AddNewFollowlist(follow);//添加follow
                    nowfollow.wait.add(fl);//添加标记，可能需要再次加的
                }
                else {
                    //不是最后一个字符的状况
                    String s = str.charAt(k+1)+"";//获取后一个字符
                    if (!Bproduction.contains(s)){//判断是否是终结符
                        //如果不是终结符,直接加入follow
                        if (k+2<str.length()&&arr.contains(str.charAt(k+2))){
                            s = s + str.charAt(k+2);
                        }
                        nowfollow.AddNewFollow(s);
                    }
                    else {
                        //是终结符情况
                        //判断有没有‘
                        String fu2 = s;//下面获取之前的s，s是第k+1个，看看k+1后面有没有，那就是k+2个
                        if(k+2<str.length()){//判断存不存在k+2
                            if ((str.charAt(k+2)+"").equals("'")){//看看k+2是不是'
                                fu2 = s+ str.charAt(k+2);//是’的话连起来
                            }
                        }

                        Firststore i2 = AllFirsts.get(Bproduction.indexOf(fu2));//先获取后一个符的First集合
                        if (nowfollow.AddNewFollowlist(i2.first)){//将first集添加并判断
                            //如果返回的是真的，就说明first集里面有空，需要再判断
                            String newstr;
                            if (str.length()>1){
                                newstr = delgoalindex(str, k + 1);
                                newstr = delgoalindex(newstr, k + 1);
                            }
                            else {
                                newstr = delgoalindex(str, k + 1);
                            }

                            singleju(newstr,fl);
                        }
                        //如果返回是假的，就说明first里面没有空，直接加入即可
                    }
                }
            }
        }
    }
    public static String delgoalindex(String str,int k){
        String newstr;
        if (k>=str.length()){
            newstr = str.substring(0,k);
        }
        else {
            newstr = str.substring(0,k) + str.substring(k+1,str.length());
        }
        return newstr;
    }
    public static void DeleLeft(){
//        for (int i = Aproduction.size()-1;i>=1;i--){
//            Store store = Aproduction.get(i);
//            Store store2 = Aproduction.get(i-1);
//            def(store2,store);
//        }
        for (int i = 0;i<Aproduction.size();i++){
            Store store = Aproduction.get(i);
            singleDel(store);
        }
    }
    public static void def(Store before,Store after){
        String head = after.head;
        List<String> strings = new ArrayList<>(before.strings);
        List<String> change = new ArrayList<>();
        for (int i = 0;i<strings.size();i++){
            String str = strings.get(i);
            for (int j = 0;j<after.strings.size();j++){
                String mid = str;
                mid = mid.replace(head,after.strings.get(j));
                if(mid.length()>1&&mid.charAt(0)=='ε'){
                    mid = mid.substring(1);
                }
                if (!change.contains(mid))change.add(mid);
            }
        }
        before.strings = change;
    }
    public static void singlemidDel(Store store){
        List<String> strings = new ArrayList<>(store.strings);
        for (int i = 0;i<strings.size();i++){
            String str = strings.get(i);
            String head = "";
            if (str.length()>1){
                if ((str.charAt(1)+"").equals("'")){
                    head = str.charAt(0)+""+str.charAt(1);
                }
                else {
                    head = str.charAt(0)+"";
                }
            }
            else {
                head = str.charAt(0)+"";
            }

            if (Bproduction.contains(head)){
                int index = Bproduction.indexOf(head);
                Store store1 = Aproduction.get(index);
                replace(store1,store);
            }
        }
    }
    public static void replace(Store rep,Store store){
        String head = rep.head;
        List<String> temp = store.strings;
        for (int i = 0;i<temp.size();i++){
            String str = temp.get(i);
            String newhead;
            if (str.length()>1){
                if ((str.charAt(1)+"").equals("'")){
                    newhead = str.charAt(0)+""+str.charAt(1);
                }
                else {
                    newhead = str.charAt(0)+"";
                }
            }
            else {
                newhead = str.charAt(0)+"";
            }

            if (head.equals(newhead)){
                temp.remove(i);
                str = str.substring(head.length());
                List<String> change = rep.strings;
                for (int j = 0;j<change.size();j++){
                    String keep = str;
                    String mid = change.get(j);
                    keep = mid + keep;
                    if (!temp.contains(keep))temp.add(keep);
                }
            }
        }
    }
    public static void singleDel(Store store){
        String head = store.head;
        List<Integer> change = new ArrayList<>();
        String Keep = "";
        List<String> strings = new ArrayList<>(store.strings);
        for(int i = 0;i<strings.size();i++){
            String mid = strings.get(i);
            if (head.equals(mid.charAt(0)+"")){
                if (mid.length()>1&&(mid.charAt(1)+"").equals("'")){
                    Keep = mid.charAt(0)+""+mid.charAt(1);
                    continue;
                }
                else {
                    change.add(i);
                    continue;
                }
            }
            if (mid.length()<=1){
                Keep = mid.charAt(0)+"";
                continue;
            }
            else if ((mid.charAt(1)+"").equals("'")){
                Keep = mid.charAt(0)+""+mid.charAt(1);
            }
        }

        String newhead = head+"'";
        if (change.size()>0){

            Store store1 = new Store(newhead);
            store1.index = Bproduction.size();
            Bproduction.add(newhead);
            collect(store,store1,Keep,change);
            Aproduction.add(store1);

            store.strings.clear();
            String newstr = Keep + newhead;
            store.AddNew(newstr);
        }

    }
    public static void collect(Store old,Store store,String Keep,List<Integer> list){
        List<String> temp = new ArrayList<>(old.strings);
        for (int i = 0;i<temp.size();i++){
            String change = temp.get(i);
            if (list.contains(i)){
                if (change.length()>1&&(change.charAt(1)+"").equals("'")){
                    change = change.substring(2,change.length());
                }
                else {
                    change = change.substring(1,change.length());
                }
                change = change+store.head;
            }
            else {
                if (Keep.length()==1){
                    if ((change.charAt(0)+"").equals(Keep)){
                        StringBuffer str = new StringBuffer(change);
                        str.setCharAt(0,'ε');
                        change = str.toString();
                    }
                }
                else {
                    if (change.length()>1&&(change.charAt(1)+"").equals("'")){
                        if (change.substring(0,2).equals(Keep)){
                            change = change.substring(2);
                            change = Keep + change;
                        }
                    }
                }
            }
            temp.set(i,change);
        }
        store.strings = temp;
    }
    public static void GetFirst(){
        List<Firststore> Firsts = new ArrayList<>();
        for (int i = 0;i<Aproduction.size();i++){
            Firststore fs = new Firststore(Aproduction.get(i).index,Aproduction.get(i).head);
            FindFirst(fs);
            Firsts.add(fs);
        }
        AllFirsts = Firsts;
    }
    public static void FindFirst(Firststore fs){
        Store store = Aproduction.get(fs.index);
        List<String> strings = new ArrayList<>();
        for (int i = 0;i<store.strings.size();i++){
            String mid = store.strings.get(i);
            Search(store,strings,mid);
        }
        fs.first = strings;
    }
    public static void Search(Store old,List<String> strings,String mid){
        arr = new ArrayList<>();
        for (int i = 0;i<10;i++){
            arr.add((char)('0'+i));
        }
        String str = ""+mid.charAt(0);
        if (arr.contains(mid.charAt(0))){
            if (mid.length()>1&&arr.contains(mid.charAt(1))){
                str = mid.charAt(0)+""+mid.charAt(1);
            }
        }
        if (Bproduction.contains(str)){
            if (mid.length()>1){
                if (Bproduction.contains(str+mid.charAt(1))){
                    str = str + mid.charAt(1);
                }
            }
            int index = Bproduction.indexOf(str);
            Store store = Aproduction.get(index);
            for (int i = 0;i<store.strings.size();i++){
                String mid2 = store.strings.get(i);
                if (mid2.equals("ε")){
                    if (mid.length()>str.length()){
                        if (!old.strings.contains(mid.substring(str.length())))old.strings.add(mid.substring(str.length()));
                        break;
                    }
                }
                Search(old,strings,mid2);
            }
        }
        else {
            if (!strings.contains(str))strings.add(str);
        }
    }
    public static void addnew(List<String> strlist){
        List<String> keep = new ArrayList<>();
        List<Store> AllStore = new ArrayList<>();
        for (int i = 0;i<strlist.size();i++){
            Store store = newstore(strlist.get(i));
            store.index = keep.size();
            AllStore.add(store);
            keep.add(store.head);
        }
        Aproduction = AllStore;
        Bproduction = keep;

    }
    public static Store newstore(String str){
        String hstr = "";
        int count = 0;
        for (int i = 0;i<str.length();i++){
            if (str.charAt(i)=='-'){
                if (str.charAt(i+1)=='>'){
                    count = i+2;
                    break;
                }
            }
            hstr = hstr + str.charAt(i);
        }

        Store res = new Store(hstr);

        String mid = "";
        for (int i = count;i<str.length();i++){
            if (str.charAt(i)=='|'){
                res.AddNew(mid);
                mid = "";
            }
            else {
                mid = mid+str.charAt(i);
            }
        }
        res.AddNew(mid);

        return res;
    }
}

