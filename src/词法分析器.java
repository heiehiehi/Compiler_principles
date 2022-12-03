import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class 词法分析器 {

    public static List<Zhushi> zhushi = new ArrayList<>();
    public static List<error> errors = new ArrayList<>();
    public static ArrayList<store> ALLdatas;
    public static List<String> NameLs;
    public static List<Integer> ConstLs;
    public static ArrayList<String> Alllists;
    public static void main(String[] args) throws IOException {
        String text = readTxtFile("src\\text.txt");
        System.out.println(text);
        List<Character> arr = new ArrayList<>();
        for (int i = 0;i<10;i++){
            arr.add((char)('0'+i));
        }
        List<Character> dangchi = new ArrayList<>();
        for (int i = 0;i<26;i++){
            dangchi.add((char)('a'+i));
            dangchi.add((char)('A'+i));
        }

        List<String> NameL = new ArrayList<>();
        List<Integer> ConstL = new ArrayList<>();

        ArrayList<String> lists = new ArrayList<>();
        setlists(lists);
        Alllists = lists;

        ArrayList<store> ALLdata = new ArrayList<>();
        readline(text,arr,dangchi,NameL,ConstL,lists,ALLdata);


        File writename = new File(".\\output.txt");

        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));

        out.write(text);

        out.write('\n');
        out.write("error:");
        for (int i = 0;i<errors.size();i++){
            out.write('\n');
            out.write("第"+errors.get(i).hang+"行 , "+errors.get(i).value+errors.get(i).message);
        }


        int j = 1;
        out.write('\n');
        out.write('\n');
        out.write("Token:");
        for (store i : ALLdata){
            out.write('\n');
            out.write(j+", "+"classCode:"+i.classCode+", Seman:"+i.seman);
            j++;
        }

        out.write('\n');
        out.write('\n');
        out.write("Name:");
        for (int i = 0;i<NameL.size();i++){
            out.write('\n');
            out.write((i+1)+" , "+NameL.get(i));
        }

        out.write('\n');
        out.write('\n');
        out.write("Const:");
        for (int i = 0;i<ConstL.size();i++){
            out.write('\n');
            out.write((i+1)+" , "+ConstL.get(i));
        }

        out.write('\n');
        out.write('\n');
        out.write("注释:");
        for (int i = 0;i<zhushi.size();i++){
            out.write('\n');
            out.write((i+1)+" , "+zhushi.get(i).value);
        }
        ALLdatas = ALLdata;
        NameLs = NameL;
        ConstLs = ConstL;
        out.flush();
        out.close();

    }
    public static String readTxtFile(String filePath) {
        File file = new File(filePath);
        String encoding = "utf-8";
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
             BufferedReader bufferedReader = new BufferedReader(read)) {
            if (file.isFile() && file.exists()) {
                String lineTxt;
                String all = "";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    all = all + lineTxt + '\n';
                }
                return all;
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return null;
    }
    public static void readline(String str,List<Character> arr,List<Character> dangchi,List<String> NameL,List<Integer> ConstL,ArrayList<String> lists,ArrayList<store> ALLdata){
        String temp = "";
        boolean into = false;
        boolean num = false;
        boolean al = false;
        boolean zhu = false;
        List<String> head = new ArrayList<>();

        head.add("/");
        head.add(">");
        head.add("=");
        head.add("<");
        head.add("|");
        head.add("!");
        head.add("&");
        head.add('"'+"");

        int hangs = 1;

        for (int i = 0;i<str.length();i++){
            if (!into){
                if (str.charAt(i)==' '){
                    continue;
                }
                else if (arr.contains(str.charAt(i))){
                    num = true;
                    into = true;
                    temp = temp + str.charAt(i);
                }
                else if (lists.contains(""+str.charAt(i))||head.contains(""+str.charAt(i))){
                    temp = ""+str.charAt(i);
                    if(head.contains(temp)){
                        String mid = ""+str.charAt(i+1);
                        if (str.charAt(i)=='/'&&str.charAt(i+1)=='/'){
                            zhu = true;
                            into = true;
                            i++;
                            continue;

                        }
                        if(str.charAt(i)=='"'){
                            i++;
                            while (str.charAt(i)!='"'){
                                i++;
                            }
                            int code = lists.indexOf(temp);
                            ALLdata.add(new store(code,temp));
                        }
                        if (str.charAt(i)=='/'&&str.charAt(i+1)=='*'){
                            i = getmorenote(i,str);
                        }
                        if (head.contains(mid)){
                            temp = temp + mid;
                            i++;
                        }
                    }
                    int code = lists.indexOf(temp);
                    if (code==37){
                        temp = "换行符";
                        hangs++;
                    }
                    ALLdata.add(new store(code,temp));
                    temp = "";
                    continue;
                }
                else if(dangchi.contains(str.charAt(i))||str.charAt(i)=='_'){
                    al = true;
                    into = true;
                    temp = temp + str.charAt(i);
                }
                else {
                    errors.add(new error(hangs,str.charAt(i)+"","非法字符错误"));
                }
                continue;
            }


            if (num){
                if (arr.contains(str.charAt(i))){
                    temp = temp + str.charAt(i);
                }
                else if (dangchi.contains(str.charAt(i))){
                    temp = temp + str.charAt(i);
                    i++;
                    while (dangchi.contains(str.charAt(i))||arr.contains(str.charAt(i))){
                        temp = temp + str.charAt(i);
                        i++;
                    }
                    into = false;
                    num = false;
                    al = false;
                    errors.add(new error(hangs,temp,"非法标识符错误"));
                    i = junpnext(i,str);
                    temp = "";
                }
                else {
                    ConstL.add(Integer.valueOf(temp));
                    ALLdata.add(new store(12,String.valueOf(ConstL.size())));
                    i--;
                    into = false;
                    num = false;
                    al = false;
                    temp = "";
                }
            }

            if (zhu){
                String z = "";
                while (str.charAt(i)!='\n'){
                    z = z + str.charAt(i);
                    i++;
                }
                zhushi.add(new Zhushi(z));
                zhu = false;
                into = false;
            }

            if (al){
                if (str.charAt(i)=='/'){
                    if (str.charAt(i+1)=='/'){
                        zhu = true;
                    }
                }
                if ((arr.contains(str.charAt(i))||dangchi.contains(str.charAt(i)))&&!zhu){
                    temp = temp + str.charAt(i);
                }
                else {
                    if (lists.contains(temp)){
                        int code = lists.indexOf(temp);
                        if (temp.equals("\n")){
                            hangs++;
                            temp="换行符";
                        }
                        ALLdata.add(new store(code,temp));
                        if (temp.equals("int")||temp.equals("float")||temp.equals("char")){
                            i = storeNameL(hangs,i,str,arr,dangchi,NameL,ALLdata,lists);
                        }
                        else {
                            i--;
                        }
                        into = false;
                        num = false;
                        al = false;
                        temp = "";
                    }
                    else {
                        int size;
                        if (NameL.contains(temp)){
                            size = NameL.indexOf(temp);
                            ALLdata.add(new store(11,String.valueOf(size)));
                        }
                        else {
                            errors.add(new error(hangs,temp,"未定义变量名错误"));
                        }


                        i--;
                        into = false;
                        num = false;
                        al = false;
                        temp = "";
                    }
                }
            }
        }
    }
    public static int storeNameL(int hangs,int i,String str,List<Character> arr,List<Character> dangchi,List<String> NameL,ArrayList<store> ALLdata,ArrayList<String> lists){
        i++;
        if (!arr.contains(str.charAt(i))){
            int size;
            String temp = "";
            while ((arr.contains(str.charAt(i))||dangchi.contains(str.charAt(i))||str.charAt(i)=='_')){
                temp = temp + str.charAt(i);
                i++;
            }
            if(lists.contains(str.charAt(i)+"")||str.charAt(i)==' '){
                if (NameL.contains(temp)){
                    size = NameL.indexOf(temp);
                }
                else {
                    NameL.add(temp);
                    size = NameL.size();
                }
                ALLdata.add(new store(11,String.valueOf(size)));
            }
            else {
                errors.add(new error(hangs,str.charAt(i)+"","非法字符错误"));
                i++;
            }
        }
        i--;
        return i;
    }
    public static int getmorenote(int i,String str){
        i+=2;
        String temp = "";
        while (!(str.charAt(i)=='*'&&str.charAt(i+1)=='/')){
            temp = temp+str.charAt(i);
            i++;
        }
        i++;
        zhushi.add(new Zhushi(temp));
        return i;
    }
    public static int junpnext(int i,String str){
        while (str.charAt(i)!=';'){
            i++;
        }
        return i;
    }
    public static void setlists(ArrayList<String> lists){
        lists.add(null);
        lists.add("if");
        lists.add("else");
        lists.add("for");
        lists.add("while");
        lists.add("break");
        lists.add("return");
        lists.add("continue");
        lists.add("float");
        lists.add("int");
        lists.add("char");
        lists.add("标识符");
        lists.add("数");
        lists.add("+");
        lists.add("-");
        lists.add("*");
        lists.add("/");
        lists.add("%");
        lists.add(">");
        lists.add(">=");
        lists.add("<");
        lists.add("<=");
        lists.add("!=");
        lists.add("==");
        lists.add("!");
        lists.add("&&");
        lists.add("||");
        lists.add(",");
        lists.add("=");
        lists.add("[");
        lists.add("]");
        lists.add("(");
        lists.add(")");
        lists.add("{");
        lists.add("}");
        lists.add(";");
        lists.add(".");
        lists.add("\n");
        lists.add("end");

        lists.add("printf");
        lists.add('"'+"");
    }


}
class store{
    int classCode;
    String seman;
    store(int classCode,String seman){
        this.classCode = classCode;
        this.seman = seman;
    }
}

class error{
    int hang;
    String value;

    String message;
    error(int hang,String value,String message){
        this.hang = hang;
        this.value = value;
        this.message = message;
    }
}
class Zhushi{
    String value;
    Zhushi(String value){
        this.value = value;
    }
}


