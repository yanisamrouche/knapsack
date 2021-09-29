import java.util.Comparator;

public class SortByRatio implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return (o1.value/o1.weight) > (o2.value/o2.weight) ? -1 : 1;
    }
}
