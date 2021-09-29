import java.io.*;
import java.util.*;

public class Knapsack {

    static int bagCapacity;
    static int nbOfItems;

    public static List<String> readDataFile(String filepath) throws IOException {

        File file = new File(filepath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> data = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            data.add(line);
        }
        fileReader.close();
        return data;
    }

    public static double[][] loadData(String filepath) throws IOException {

        List<String> data = readDataFile(filepath);
        bagCapacity = Integer.parseInt(data.get(0));
        data = data.subList(1,data.size());
        nbOfItems = data.size();
        double[] profits = new double[data.size()];
        double[] weights = new double[data.size()];
        for(int i=0; i<data.size(); i++){
            String[] s = data.get(i).split(" ");
            weights[i] = Integer.parseInt(s[0]);
            profits[i] = Integer.parseInt(s[1]);
        }
        double[][] matrix = new double[3][nbOfItems];
        for(int i=0; i < nbOfItems; i++){
            matrix[0][i] = profits[i];
            matrix[1][i] = weights[i];
            matrix[2][i] = profits[i]/weights[i];
        }
        return matrix;

    }

   public static void swap(double[][] mat, int i, int j){
        double tmp = mat[i][j-1];
        mat[i][j-1] = mat[i][j];
        mat[i][j] = tmp;
   }
   public static double[][] sortingByRatio(double[][] mat){
        //double[][] newMat = new double[3][nbOfItems];
        for(int i=0; i < nbOfItems; i++){
            for (int j=1; j < (nbOfItems - i); j++){
                if(mat[2][j-1] < mat[2][j]){
                    swap(mat,0,j);//swap the first row
                    swap(mat,1,j);//swap the second row
                    swap(mat,2,j);

                }
            }
        }
        return mat;
   }

    public static void printMatrix(double[][] mat){
        for(int i=0; i<mat.length; i++){
            System.out.print("| ");
            for (int j=0; j<mat[0].length; j++){
                System.out.print(mat[i][j]+" ");
            }
            System.out.print(" |");
            System.out.println();
        }
    }

    /* ======================================================================= */

    public static double upperBound(double total_value, double total_weight, int num, Item[] items){
        double value = total_value;
        double weight = total_weight;
        for(int i=num; i < nbOfItems; i++){
            if(weight + items[i].weight <= bagCapacity){
                weight = weight + items[i].weight;
                value = value - items[i].value;
            }
            else { // le cout avec fraction
                value = value - (bagCapacity - weight)*items[i].weight/items[i].value;
            }
        }
        return value;
    }

    public static double lowerBound(double total_value, double total_weight, int num, Item[] items){
        double value = total_value;
        double weight = total_weight;
        for(int i = num; i < nbOfItems; i++){
            if(weight + items[i].weight <= bagCapacity){
                weight = weight + items[i].weight;
                value = value - items[i].value;
            }
            else { // sans fraction
                break;
            }
        }
        return value;
    }

    public static void findOptimalSolution(Item[] items){
        Arrays.sort(items, new SortByRatio());
        Node currentNode= new Node();
        Node leftNode=  new Node();
        Node rightNode= new Node();

        double minimum_lower_bound = 0;//la borne inférieure (cout) minimale de tous les nœuds explorés
        double final_lower_bound = Integer.MAX_VALUE; //borne inférieure (cout) minimale de tous les chemins qui ont atteint le niveau final

        currentNode.total_value = 0;
        currentNode.total_weight= 0;
        currentNode.upper_bound = 0;
        currentNode.lower_bound = 0;
        currentNode.level = 0;
        currentNode.selected = false;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new SortByCost()); // file d'attente prioritaire
        priorityQueue.add(currentNode);

        boolean[] isIncluded = new boolean[nbOfItems]; // currPath
        boolean[] resultSelection = new boolean[nbOfItems]; // finalPath

        while (!priorityQueue.isEmpty()){
            currentNode = priorityQueue.poll();
            if (currentNode.upper_bound > minimum_lower_bound || currentNode.upper_bound >= final_lower_bound){
                continue; // si la valeur du sommet courant n'est pas inférieur à min alors pas besion d'explorer la bronche
                // final permet d'éliminer tout les chemins
            }
            if(currentNode.level != 0){
                isIncluded[currentNode.level -1] = currentNode.selected;
            }
            if (currentNode.level == nbOfItems){
                if(currentNode.lower_bound < final_lower_bound){
                    for (int i=0; i < nbOfItems; i++){
                        resultSelection[items[i].num] = isIncluded[i];
                    }
                    final_lower_bound = currentNode.lower_bound;
                }
                continue;
            }

            int level = currentNode.level;

            rightNode.upper_bound = upperBound(currentNode.total_value,currentNode.total_weight,level+1,items);
            rightNode.lower_bound = lowerBound(currentNode.total_value,currentNode.total_weight,level+1,items);
            rightNode.level = level +1;
            rightNode.selected = false;
            rightNode.total_value = currentNode.total_value;
            rightNode.total_weight = currentNode.total_weight;

            if(currentNode.total_weight + items[currentNode.level].weight <= bagCapacity){
                leftNode.upper_bound = upperBound(currentNode.total_value - items[level].value, currentNode.total_weight+items[level].weight, level+1, items);
                leftNode.lower_bound = lowerBound(currentNode.total_value - items[level].value, currentNode.total_weight + items[level].weight, level+1,items );
                leftNode.upper_bound = leftNode.upper_bound;
                leftNode.lower_bound = leftNode.lower_bound;
                leftNode.level = level+1;
                leftNode.selected = true;
                leftNode.total_value = currentNode.total_value - items[level].value;
                leftNode.total_weight = currentNode.total_weight + items[level].weight;
            }
            else {//si on prend pas le sommet de gauche (ne pas l'ajouter a pq)
                leftNode.upper_bound = 1;
                leftNode.lower_bound = 1;
            }

            //mise à jour
            minimum_lower_bound = Math.min(minimum_lower_bound, leftNode.lower_bound);
            minimum_lower_bound = Math.min(minimum_lower_bound, rightNode.lower_bound);

            if(minimum_lower_bound >= leftNode.upper_bound)
                priorityQueue.add(new Node(leftNode));
            if (minimum_lower_bound >= rightNode.upper_bound)
                priorityQueue.add(new Node(rightNode));
        }

        System.out.println("Les objets que le voleur doit choisir sont : ");
        for (int i=0; i < nbOfItems; i++){
            if (resultSelection[i])
                System.out.print("1 ");
            else
                System.out.print("0 ");
        }
        System.out.println();
        System.out.println("La valeur optimale est de : "+(-final_lower_bound));

    }

    public static void main(String args[]) throws IOException {
        double[][] Mat = loadData(args[0]);
        System.out.println("la masse totale du sac = "+bagCapacity);
        System.out.println("le nombre d'objets dans le manoir = "+nbOfItems);
        Item[] items = new Item[nbOfItems];
        for(int i=0; i < nbOfItems; i++){
            items[i] = new Item((int) Mat[0][i],Mat[1][i],i);
        }
        findOptimalSolution(items);


    }




}
