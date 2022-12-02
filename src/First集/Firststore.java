package First集;

import java.util.ArrayList;
import java.util.List;

public class Firststore {
    int index;
    public String head;
    public List<String> first = new ArrayList<>();

    Firststore(int index, String head) {
        this.index = index;
        this.head = head;
    }

    void AddNewFirst(String str) {
        first.add(str);
    }
}
