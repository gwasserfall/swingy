package helpers;

import java.util.ArrayList;
import java.util.Random;

public class RandomIndexOfStringArrayListPicker<T> extends ArrayList<T> {
    public T GetRandomItemFromArrayListUsingArraySize() {
        int rnd = new Random().nextInt(this.size());
        return this.get(rnd);
    }
}
