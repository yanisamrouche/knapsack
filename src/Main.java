import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        Knapsack knapsack = new Knapsack();
        double[][] Mat = Parser.loadData(args[0]);
        System.out.println("la masse totale du sac = "+knapsack.bagCapacity);
        System.out.println("le nombre d'objets dans le manoir = "+knapsack.nbOfItems);
        Item[] items = new Item[knapsack.nbOfItems];
        for(int i=0; i < knapsack.nbOfItems; i++){
            items[i] = new Item((int) Mat[0][i],Mat[1][i],i);
        }
        knapsack.findOptimalSolution(items);
    }
}
