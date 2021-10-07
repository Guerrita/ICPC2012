import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter the number of cells the ACM has");
            String Cells = reader.readLine();
            Integer nCells =  Integer.parseInt(Cells);
            String[] cells = new String[nCells];
            if (nCells<=75 && nCells>=1){
                String iCell;
                for (int i = 0; i < nCells; i++) {
                    System.out.println("Enter the information about the "+ (i+1) + "th Cell" );
                     iCell = reader.readLine().trim();
                    if (iCell.length()!=nCells){
                        System.out.println("the line does't have " +nCells + " values");
                        return;  //Validates that the line has n characters
                    }
                    if (!validateLine(iCell,i)){
                        System.out.println("The line has different values from 1 and 0");
                        return;  //validates that the line has only 0 and 1
                    }
                    cells[i] = iCell;                   //the value is added
                }
                if (!validateData(cells)){
                    System.out.println("The entered data is wrong");
                    return;       //either the ith character of the jth line is 1 or the jth
                }                 //character of the ith line is 1, but not both.

                //Now we have validated that the data is correct
                //The next step is to find the cells to infiltrate

                java.util.List infiltratedCells = new ArrayList<Integer>();
                java.util.List markedCells = new ArrayList<Integer>();
                Integer nMissingCells = nCells;

                //Lo siguiente va dentro de un for
                int maxValue = findMaxLine(cells, infiltratedCells, markedCells);
                nMissingCells-=cells[maxValue].split("1", -1).length;
                

                infiltratedCells.add(maxValue);
                markedCells.add(maxValue);
                for (int i = 0; i < nCells; i++) {
                    if (Character.getNumericValue(cells[maxValue].charAt(i)) == 1) markedCells.add(i);
                }

                while (nMissingCells>0) {
                    maxValue = findMaxLine(cells, infiltratedCells, markedCells);
                    nMissingCells-=cells[maxValue].split("1", -1).length;
                    System.out.println(nMissingCells);
                    if (nMissingCells == 1) {
                        infiltratedCells.add(maxValue);
                        for (int i = 0; i < nCells; i++) {
                            if (i != maxValue) {
                                if (Character.getNumericValue(cells[maxValue].charAt(i)) == 0 && !markedCells.contains(i)) {
                                    markedCells.add(i);
                                }
                            }
                        }
                    } else {
                        infiltratedCells.add(maxValue);
                        if (!markedCells.contains(maxValue))markedCells.add(maxValue);
                        for (int i = 0; i < nCells; i++) {
                            if (Character.getNumericValue(cells[maxValue].charAt(i)) == 1 && !markedCells.contains(i)) markedCells.add(i);
                        }
                    }
                }
                System.out.print(infiltratedCells.size() + ": ");
                for (Object data :infiltratedCells) {
                    int number = (int)data;
                    System.out.print(number + 1 + " ");
                }
            }else {
                System.out.println("the number of cells entered is incorrect");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findMaxLine(String[] cells, List infiltratedCells, List markedCells ) {
        int maxValue=-1;
        int line = -1;
        int valueCell;
        for (int i = 0; i < cells.length; i++) {
            valueCell = cells[i].split("1", -1).length-1;
            for (int j = 0; j < cells.length ; j++) {               //If the cell is infiltrated then we reduce its value
                if (Character.getNumericValue(cells[i].charAt(j))==1 && markedCells.contains(j)) valueCell-=1;
            }

            if (maxValue<=valueCell && !infiltratedCells.contains(i) ){

                line=i;
                maxValue=valueCell;


            }


        }
        return line;
    }



    private static boolean validateData(String[] cells) {
        for (int i = 0; i < cells.length-1; i++) {
            for (int j = i+1; j < cells.length; j++) {
                if(Character.getNumericValue(cells[i].charAt(j))==1
                   &&
                   Character.getNumericValue(cells[j].charAt(i))==1) return false;
            }
        }
        return true;
    }

    public static boolean validateLine(String line,int n){
        for (int i = 0; i < line.length(); i++) {
            if (Character.getNumericValue(line.charAt(i))!=0 && Character.getNumericValue(line.charAt(i))!=1) {
                return false;
            }
        }
        if (Character.getNumericValue(line.charAt(n))==1) return false;
        return true;
    }
}
