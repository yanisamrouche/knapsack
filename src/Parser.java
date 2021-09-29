import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {


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
        KnapsackBranchAndBound.bagCapacity = Integer.parseInt(data.get(0));
        data = data.subList(1,data.size());
        KnapsackBranchAndBound.nbOfItems = data.size();
        double[] profits = new double[data.size()];
        double[] weights = new double[data.size()];
        for(int i=0; i < data.size(); i++){
            String[] s = data.get(i).split(" ");
            weights[i] = Integer.parseInt(s[0]);
            profits[i] = Integer.parseInt(s[1]);
        }
        double[][] matrix = new double[3][KnapsackBranchAndBound.nbOfItems];
        for(int i = 0; i < KnapsackBranchAndBound.nbOfItems; i++){
            matrix[0][i] = profits[i];
            matrix[1][i] = weights[i];
            matrix[2][i] = profits[i]/weights[i];
        }
        return matrix;

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
}
