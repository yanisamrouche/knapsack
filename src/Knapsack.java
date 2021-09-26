import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        int[] profits = new int[data.size()];
        int[] weights = new int[data.size()];
        for(int i=0; i<data.size(); i++){
            String[] s = data.get(i).split(" ");
            profits[i] = Integer.parseInt(s[0]);
            weights[i] = Integer.parseInt(s[1]);
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

    public static void main(String args[]) throws IOException {
        printMatrix(loadData(args[0]));
        System.out.println("--------------------------------------");
        printMatrix(sortingByRatio(loadData(args[0])));
    }




}
