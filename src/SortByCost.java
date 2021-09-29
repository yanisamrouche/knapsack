import java.util.Comparator;

public class SortByCost implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.lower_bound > o2.lower_bound ? 1 : -1;
    }

}
