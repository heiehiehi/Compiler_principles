package First集;

import java.util.ArrayList;
import java.util.List;

public class Followstore {
    int index;
    public String head;

    List<Integer> wait = new ArrayList<>();
    public List<String> follow = new ArrayList<>();

    Followstore(int index, String head) {
        this.index = index;
        this.head = head;
    }

    boolean AddNewFollow(String str) {
        boolean flag = false;
        if (!follow.contains(str)) {
            if (str.equals("ε")) {
                flag = true;
            } else {
                follow.add(str);
            }

        }
        return flag;
    }

    boolean AddNewFollowlist(List<String> strings) {
        boolean flag = false;
        for (String s : strings) {
            if (!follow.contains(s)) {
                if (s.equals("ε")) {
                    flag = true;
                } else {
                    follow.add(s);
                }
            }
        }
        return flag;
    }
}
