import java.io.IOException;
import java.util.Scanner;


public class MainApplication {

    static boolean running1 = true;
    static boolean running2 = true;
    public static void runKnapsackApplication() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("<================= Problème du sac-à-dos et Branch and Bound =================>");
        System.out.println("<================= Veuillez choisir la méthode de résolution =================>");
        System.out.println(" 1-Algorithme glouton \n 2-Branch and Bound ");
        System.out.print("Saisir votre choix : ");
        int methodChoice = scanner.nextInt();
        if(methodChoice == 1){

            System.out.println("<================= Algorithme Glouton =================>");
            System.out.println("<================= Veuillez selectionner le numéro du sac-à-dos =================>");
            System.out.println(" 0-sac0.txt\n 1-sac1.txt \n 2-sac2.txt \n 3-sac3.txt \n 4-sac4.txt");
            System.out.print("Saisir votre choix : ");
            int numChoice = scanner.nextInt();
            System.out.println("<=================solution pour le sac numéro " + numChoice + "=================>");
            switch (numChoice) {
                case 0: solutionUsingGreedyFractional("0");break;
                case 1: solutionUsingGreedyFractional("1");break;
                case 2: solutionUsingGreedyFractional("2");break;
                case 3: solutionUsingGreedyFractional("3");break;
                case 4: solutionUsingGreedyFractional("4");break;
            }
            while (running1) {
                System.out.println("<================= Vous voulez choisir un autre sac ? =================>");
                System.out.println(" 1-Oui \n 2-Non ");
                System.out.print("Saisir votre choix : ");
                int yesOrNo = scanner.nextInt();
                switch (yesOrNo) {
                    case 1: runKnapsackApplication();break;
                    case 2: System.out.println("Bye :) !!");running1 =false;break;
                }
            }



        }else if(methodChoice == 2){
            System.out.println("<================= Branch and Bound =================>");
            System.out.println("<================= Veuillez selectionner le numéro du sac-à-dos =================>");
            System.out.println(" 0-sac0.txt\n 1-sac1.txt \n 2-sac2.txt \n 3-sac3.txt \n 4-sac4.txt");
            System.out.print("Saisir votre choix : ");

            int numChoice = scanner.nextInt();
            System.out.println("<=================solution pour le sac numéro " + numChoice + "=================>");
            switch (numChoice) {
                case 0: solutionUsingBranchAndBound("0");break;
                case 1: solutionUsingBranchAndBound("1");break;
                case 2: solutionUsingBranchAndBound("2");break;
                case 3: solutionUsingBranchAndBound("3");break;
                case 4: solutionUsingBranchAndBound("4");break;
            }

            while (running2) {
                System.out.println("<================= Vous voulez choisir un autre sac ? =================>");
                System.out.println(" 1-Oui \n 2-Non ");
                System.out.print("Saisir votre choix : ");
                int yesOrNo = scanner.nextInt();
                switch (yesOrNo) {
                    case 1: runKnapsackApplication();break;
                    case 2: System.out.println("Bye :) !!");running2 =false;break;
                }
            }
        }


    }

    public  static void solutionUsingGreedyFractional(String num) throws IOException {
        FractionalKnapsack fractionalKnapsack = new FractionalKnapsack();
        double[][] Mat = Parser.loadData("knapsack_data/sac"+num+".txt");
        System.out.println("la masse totale du sac = "+fractionalKnapsack.bagCapacity);
        System.out.println("le nombre d'objets dans le manoir = "+fractionalKnapsack.nbOfItems);
        int[] profits = new int[fractionalKnapsack.nbOfItems];
        double[] weights = new double[fractionalKnapsack.nbOfItems];
        for (int i=0; i < fractionalKnapsack.nbOfItems; i++){
            profits[i] = (int) Mat[0][i];
            weights[i] = Mat[1][i];
        }
        fractionalKnapsack.findOptimalSolution(weights,profits,fractionalKnapsack.bagCapacity);

    }

    public static void solutionUsingBranchAndBound(String numOfTheKnapsack) throws IOException {
        KnapsackBranchAndBound knapsack = new KnapsackBranchAndBound();
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
