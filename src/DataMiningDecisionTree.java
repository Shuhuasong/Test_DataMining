import java.awt.*;
import java.io.*;
import java.sql.DatabaseMetaData;
import java.util.*;

/**
 * Created by Shuhua Song
 */
public class DataMiningDecisionTree {
    static int numRows, numCols;
    static String[][] dataAry;
    static int total = 0;

    private static Map<String, Integer> colIndexMap = new HashMap<>();
    private static Map<String, Integer> rowIndexMap = new HashMap<>();
    private static Set<String> col0Values = new HashSet<>();
    private static Set<String> col1Values = new HashSet<>();
    private static Set<String> col2Values = new HashSet<>();
    private static Set<String> col3Values = new HashSet<>();
    private static Set<String> col4Values = new HashSet<>();
    private static Set<String> col5Values = new HashSet<>();
    private static Map<String, Set<String>> colToValues = new HashMap<>();


    public DataMiningDecisionTree(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        dataAry = new String[numRows][numCols];
    }

    private void loadData(Scanner inputFile, String[][] dataAry) {
        int currRow = 0;
        while(inputFile.hasNextLine() && currRow < numRows){
            int currCol = 0;
            String line = inputFile.nextLine();
            System.out.println("--  " + line);
            String[] parts = line.split("\\s+");
            for(String part : parts){
                dataAry[currRow][currCol++] = part;
            }
            currRow++;
            System.out.println();
        }

        for(int i=0; i<numRows; i++){
            for(int j=0; j<numCols; j++){
               System.out.print(dataAry[i][j] + "           ");
            }
            System.out.println();
        }

        for(int j=0; j<numCols; j++){
            colIndexMap.put(dataAry[0][j], j);

            System.out.println("colMap :  " + dataAry[0][j] + " " + j);
        }

        for(int i=1; i<numRows; i++){
            rowIndexMap.put(dataAry[i][0], i);
            total += Integer.parseInt(dataAry[i][numCols-1]);
            System.out.println("rowMap :  " + dataAry[i][0] + " " + i);
            //col0Values.add(dataAry[i][0]);
            col1Values.add(dataAry[i][1]);
            col2Values.add(dataAry[i][2]);
            col3Values.add(dataAry[i][3]);
            col4Values.add(dataAry[i][4]);
            col5Values.add(dataAry[i][5]);
        }
        System.out.println("total : " + total);

        colToValues.put(dataAry[0][0], col0Values);
        colToValues.put(dataAry[0][1], col1Values);
        colToValues.put(dataAry[0][2], col2Values);
        colToValues.put(dataAry[0][3], col3Values);
        colToValues.put(dataAry[0][4], col4Values);
        //colToValues.put(dataAry[0][5], col5Values);

        System.out.println();
        for(String s : col0Values){
            System.out.print(s + "  ");
        }
        System.out.println();
        for(String s : col1Values){
            System.out.print(s + "  ");
        }
        System.out.println();
        for(String s : col2Values){
            System.out.print(s + "  ");
        }
        System.out.println();
        for(String s : col3Values){
            System.out.print(s + "  ");
        }
        System.out.println();
        for(String s : col4Values){
            System.out.print(s + "  ");
        }
        System.out.println();
        for(String s : col5Values){
            System.out.print(s + "  ");
        }
        System.out.println();

    }


   static Map<String, Map<String, Integer>> typeValue2Count = new HashMap<>();

    //-SUM_Type[ Pr(Type|Q)*Log_2(Pr(Type|Q))
    private static double infoMeasureCount(){
        System.out.println("-------*****-------");
        for(String col : colToValues.keySet()){
            Set<String> values = colToValues.get(col);
            double[][] type2Info = new double[values.size()][2]; // { {sum1, v1}, {sum2, v2},.....
            int i = 0;
            int colIdx =  colIndexMap.get(col);
            for(String value : values){ //simple
                int typeSum = 0;
                for(int r=1; r<numRows; r++){
                    if(dataAry[r][colIdx].equals(value)){
                        int count = Integer.parseInt(dataAry[r][numCols-1]);
                        typeSum += count;
                    }
                }
                type2Info[i++][0] = typeSum;
                Map<String, Integer> value2Count = new HashMap<>();
                value2Count.put(value, typeSum);
                System.out.println("valut-count : " + value + " " + typeSum);
                typeValue2Count.put(col, value2Count);
            }
            System.out.println("-------*****-------");
        }
        for(String col : typeValue2Count.keySet()){
            Map<String, Integer> terms = typeValue2Count.get(col);
            for(String k : terms.keySet()){
                System.out.println(col + "--%%--"  + k +  "--%%-- " + terms.get(k));
            }
        }
        return 0.0;
    }


    public static void main(String[] args) throws IOException {
        Scanner inputFile = new Scanner(new FileReader(args[0]));
        //read header from labelFile
        String inputName = args[0];
        //System.out.println("inputName: " + inputName);

        int numRows = inputFile.nextInt();
        int numCols = inputFile.nextInt();
        inputFile.nextLine();
        //System.out.println( "lineTest: " + lineTest);
        DataMiningDecisionTree dataMining = new DataMiningDecisionTree(numRows, numCols);
        //System.out.println(numRows + " " + numCols );

        String[][]  dataAry = dataMining.dataAry;
        dataMining.loadData(inputFile, dataAry);
        dataMining.infoMeasureCount();

        //Create output File on the run time according to input File
      /*  String imgName = args[0].substring(0, 4);
        String chainCodeFileName = imgName + "_chainCode.txt";
        String boundaryFileName = imgName + "_boundary.txt";
        String printImageName = imgName + "_output.txt";
        BufferedWriter chainCodeFile = new BufferedWriter(new FileWriter(new File(chainCodeFileName)));
        BufferedWriter boundaryFile = new BufferedWriter(new FileWriter(new File(boundaryFileName)));
        BufferedWriter printImageFile = new BufferedWriter(new FileWriter(new File(printImageName))); */

        inputFile.close();
    }


}


