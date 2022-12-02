package First集;

import java.util.ArrayList;
import java.util.List;

public class Store {
    int index;
    public String head;
    public List<String> strings = new ArrayList<>();

    Store(String head) {
        this.head = head;
    }

    public Store(int index, String head, List<String> strings) {
        this.index = index;
        this.head = head;
        this.strings = new ArrayList<>(strings);
    }

    void AddNew(String str) {
        this.strings.add(str);
    }

}
