import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class first {

	public static String[] str= {"S->MH|a","H->LSo","K->dML|&","L->eHf","M->K|bLM"};
//	public static String[] str= {"S->aH","H->aMd|d","M->Ab|&","A->aM|e"};
//	public static String[] str= {"A->A|b"};
//	public static String[] str= {"S->TE","E->+TE|&","T->FA","A->*FA|&","F->i|n|(S)"};
	public static String[] VN=new String[str.length];
	public static String string="";
	public static String[] first=new String[str.length];
	public static void find_VN() {
		for(int i=0;i<str.length;i++) {
			VN[i]=str[i].substring(0,1);
			string=string+VN[i];
			first[i]="";
		}
	}

	public static String solution(String s){
	    StringBuffer sb = new StringBuffer(s);
	    String[] arr = new String[sb.length()];

		// 将字符串转换为字符串			数组
	    for (int i = 0; i < sb.length(); i++) {
	        arr[i] = String.valueOf(sb.charAt(i));
	    }

		// Arrays.asList(arr)：将数组转换为List集合形式
	    // 将arr的List集合输入到set中用于去重
	    HashSet<String> set = new HashSet<String>(Arrays.asList(arr));

	    // 将set集合转化成数组
	    String[] strarr = set.toArray(new String[0]);

	    // 数组拼接成字符串
	    String result = "";
	    for (int i = 0; i < strarr.length; i++) {
	        result = result + strarr[i];
	    }

	    return result;
	}

	public static void part(int i,int x,int position) {
		if(x==0) {
			x=4;
		}else {
			x++;
		}
		char ch=str[i].charAt(x);
		if(first[position].indexOf('&')!=-1) {

			if(ch=='|')	return;
			else if(string.indexOf(ch)!=-1) {
				int p=first[position].indexOf('&');
				first[position]=first[position].substring(0, p)+first[position].substring(p+1);
				first[i]=first[i]+first[string.indexOf(ch)];
				part(i, x,string.indexOf(ch));
			}else {
				first[i]=first[i]+string.valueOf(ch);
			}
		}else {
			return;
		}
	}

	public static void  First() {
		find_VN();
		int flag=0;
		while(flag<=str.length-1) {
			flag++;
			for(int i=0;i<str.length;i++) {
				String s=str[i];
				int p=s.indexOf('>');
				String chr=s.substring(p+1);
				int leng=chr.length();
				int x=0;
				while(x<leng) {
					if(chr.charAt(x)=='|') { x++;	continue;}
					int position=string.indexOf(chr.charAt(x));
					if(x==0||chr.charAt(x-1)=='|') {
						if(position!=-1) {
							if(first[position]==null) {
								x++; continue;
							}else {
								first[i]=first[i]+first[position];
								part(i, x,position);
							}
						}
						else{
							first[i]=first[i]+String.valueOf(chr.charAt(x));
						}
						x++;
					}
					else {x++; continue;}
				}
			}
		}
//		System.out.println("ghj");
		for(int i=0;i<str.length;i++) {
			first[i]=solution(first[i]);
			System.out.println(VN[i]+" "+first[i]);
		}
	}
}
