import java.io.IOException;
import java.util.Scanner;


public class MainApplication {

    static boolean running = true;
    public static void runKnapsackApplication() throws IOException, InterruptedException {
        System.out.println("<================= Problème du sac-à-dos et Branch and Bound =================>");


            System.out.println("         <===== Veuillez selectionner le numéro du sac-à-dos =====>");
            System.out.println(" 0-sac0.txt\n 1-sac1.txt \n 2-sac2.txt \n 3-sac3.txt \n 4-sac4.txt");
            System.out.print("Saisir votre choix : ");
            Scanner scanner = new Scanner(System.in);
            int numChoice = scanner.nextInt();
            System.out.println("            <===solution pour le sac numéro " + numChoice + " ===>");
            switch (numChoice) {
                case 0:
                    solution("0");
                    break;
                case 1:
                    solution("1");
                    break;
                case 2:
                    solution("2");
                    break;
                case 3:
                    solution("3");
                    break;
                case 4:
                    solution("4");
                    break;
            }

        while (running) {
            System.out.println("         <===== Vous voulez choisir un autre sac ? =====>");
            System.out.println(" 1-Oui \n 2-Non ");
            System.out.print("Saisir votre choix : ");
            int yesOrNo = scanner.nextInt();
            switch (yesOrNo) {
                case 1: runKnapsackApplication();break;
                case 2: running =false;break;
            }
        }
    }

    public static void solution(String numOfTheKnapsack) throws IOException {
        Knapsack knapsack = new Knapsack();
       // double[][] Mat = Parser.loadData(numOfTheKnapsack);
        double[][] Mat = Parser.loadData("knapsack_data/sac"+numOfTheKnapsack+".txt");
        System.out.println("la masse totale du sac = "+knapsack.bagCapacity);
        System.out.println("le nombre d'objets dans le manoir = "+knapsack.nbOfItems);
        Item[] items = new Item[knapsack.nbOfItems];
        for(int i=0; i < knapsack.nbOfItems; i++){
            items[i] = new Item((int) Mat[0][i],Mat[1][i],i);
        }
        knapsack.findOptimalSolution(items);
    }
    public static void main(String args[]) throws IOException, InterruptedException {
        runKnapsackApplication();
        //solution(args[0]);
    }

    /* Terminal :
        $ javac MainApplication.java
        $ java MainApplication
     */
}
