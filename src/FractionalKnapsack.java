import java.util.Arrays;

public class FractionalKnapsack extends Knapsack{

    public static double findOptimalSolution(double[] weights, int[] profits, int capacity){

        double total_value = 0;
        Item[] items = new Item[nbOfItems];
        for (int i=0; i < profits.length; i++){
            items[i] = new Item(profits[i], weights[i],i);
        }
        Arrays.sort(items, new SortByRatio());
        for(Item item : items){
            int value = item.value;
            double weight = item.weight;
            if(weight <= capacity){
                total_value += value;
                capacity -= weight;
            }
            else {
                total_value += (value)*(capacity/weight);
                capacity -= (capacity/weight);
                break;
            }
        }
        System.out.println("=====================================");
        System.out.println("La solution optimale = "+total_value);
        System.out.println("=====================================");
        return total_value;
    }
}
