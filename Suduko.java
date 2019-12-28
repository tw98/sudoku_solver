/****************************************************************************
 * Name : Tom Wallenstein
 * NetID : tw496
 * 
 * Execution: java Suduko nameOfTextFile
 *  
 * Description:
 * The program reads in an unsolved Suduko as textfile and solves it by 
 * using a sophistaced backtracking-algorithm. Uses linked list containing 
 * node-objects to find the possible candidates for each field and recursion
 * to solve it afterwards.
 * 
 * Programs is splitted into four parts:
 * - helper functions: converting between matrix and array, printMatrix-function
 * - checking functions: to test if Suduko conditions are fulfilled
 * - solving functions: possibleCandidates and solve function
 * - visualizing function: prints out solved Suduko using PennDraw
 *****************************************************************************/

public class Suduko {
    private static int[][] matrix = new int[9][9];
    private static Node[] candidates = new Node[81];
    
//-----------------------------------------------------------------------------    
    // HELPER FUNCTIONS
    
    // converts matrix index to array index
    private static int getArrayIndex(int col, int row) {
        int arrayNumber = 9 * row + col;
        return arrayNumber;
    }
    
    // converts array index to row - matrix index
    private static int getRow(int index) {
        int row = index / 9;
        return row;
    }
    
    // converts array index to coloumn - matrix index
    private static int getCol(int index) {
        int col = index % 9;
        return col;
    }
    
    // print matrix
    private static void printMatrix() {
        for(int row = 0; row < 9; row++) {
            if (row >0) { System.out.println(); }
            for(int col = 0; col < 9; col++) {
                System.out.print(matrix[row][col] + " ");
            }
        }
        System.out.println();
    }
    
//-----------------------------------------------------------------------------
    // SUDUKO - CONDITIONS 
    
    // umbrella function 
    // which returns true when all suduko-conditions are fulfilled
    public static boolean legal(int val, int row, int col) {
        if (checkColoumn(val, col) && 
            checkRow(val, row) && 
            checkBox(val, row, col)) { 
//          System.out.println(val + " true");
            return true; 
        } else {
//            System.out.println
//            (val + " " + matrix[row][col] + " " + row + " " + col);
//            System.out.println(checkColoumn(val, col));
//            System.out.println(checkRow(val, row));
//            System.out.println(checkBox(val, row, col));
            return false;
        }
    }
    
    // check if coloumn does not violate Suduko rules 
    public static boolean checkColoumn(int val, int c) {
        for(int i = 0; i < 9; i++) {
            if (val == matrix[i][c])
                return false;
        }
        return true;
    }
    
    // check if coloumn does not violate Suduko rules
    public static boolean checkRow(int val, int c) {
        for (int i = 0; i < 9; i++) {
            if (val == matrix[c][i])
                return false;
        }
        return true;
    }
    
    // check if box does not violate Sudoko rules
    public static boolean checkBox(int val, int i, int j) {
        int boxRowOffset = (i / 3) * 3; //index finder for box rows
        int boxColOffset = (j / 3) * 3; //index finder for box coloumns
        // go through box
        for (int k = 0; k < 3; k++) {
            for (int m = 0; m < 3; m++) {
                if (val == matrix[boxRowOffset + k][boxColOffset + m])    
                    return false;
            }
        }
        return true;
    }
    
//-----------------------------------------------------------------------------
    // SOLVING-FUNCTIONS
    
    // creating nodes with possible solutions in order to decrease 
    // number of iterations backtracking algorithm has to go through
    public static void findPossibilities() {
        for (int i = 0; i < 81; i++) {
            int row = getRow(i);
            int col = getCol(i);
            
            //   if (candidates[i].getNumber() == 0) {
            if (matrix[row][col] == 0) {
                // System.out.println("Checking values of "+ row + " " + col);
                for (int val = 1; val <= 9; val++) {
                    if (legal(val, row, col)) {
                        candidates[i] = new Node(candidates[i], val);
                    }
                }
            }      
        }
    }
    
    // solve Suduko
    public static boolean solve(int col, int row) {
        // if current cell is last cell return true
        if(col == 9) {
            col = 0;
            row++; // wrap around col
            if (row == 9) //[8][8] is last cell, increament -> out of Bounce
                return true;
        }
        
        // skip already filled cells
        if (matrix[row][col] != 0) {
            //  System.out.println("skip");
            return solve(col+1, row);    
        }
        
        // get arrayIndex to access node array
        int arrayIndex = getArrayIndex(col, row);
        //    System.out.println(arrayIndex);
        
        // going through linked nodes with possible candidates
        for (Node current = candidates[arrayIndex]; 
             current != null;
             current = current.getNext()) {
                 // if legal, add to matrix
                 // assign currentNode-value to variable
                 int val = current.getNumber();
                 
//               System.out.println(val + " 1");
                 
                 // test if value is legal
                 if (legal(val, row, col)) {
                     
                     // if yes add legal value to matrix
                     matrix[row][col] = val;
                     
//                     System.out.println(val);
//                     System.out.println("continue");
                     
                     // continue, current cell equals next cell
                     if (solve(col + 1, row)) {
                         return true;
                     } else {   
                         matrix[row][col] = 0; //reset matrix value
                     }
                 }
             }             
//             System.out.println("about to end");   
             return false;
    }     
    
//-----------------------------------------------------------------------------   
    
    public static void main(String[] args) {
        // read in Suduko from text file
        String filename = args[0];
        In inStream = new In(filename);
        
        for (int i = 0; i < 81; i++) {
            int val = inStream.readInt();
            candidates[i] = new Node(null, val); // create start of linked list
            int row = getRow(i); // convert arrayIndex to matrix
            int col = getCol(i);
            matrix[row][col] = candidates[i].getNumber(); //add value to matrix
        }
        
        // test findPossiblities  
        findPossibilities();
        
//        for(int i = 0; i < 81; i++) {
//            int row = getRow(i);
//            int col = getCol(i);
//            System.out.println
//        (row + "-" + col + " : " + candidates[i].toString());
//        }
//        System.out.println("matrix: " + matrix[3][2]);
//        System.out.println("---------------------");
        // System.out.println(solve(0, 0));
        
        // solve Suduko 
        if (solve(0, 0)) {
            // if solution found, print solution
            printMatrix();
            visualizeSuduko();
        } else {
            System.out.println("Suduko has no solution");
        }
        
        
        
        /*               
         // test checkRow and checkBox
         // fill first coloumn
         for (int i = 0; i < 9; i++) {
         int arrayIndex = getArrayIndex(0, i);
         matrix[0][i] = candidates[arrayIndex].changeNumber(i + 1);
         System.out.print(matrix[0][i] + " ");
         }
         System.out.println();
         // fill first row
         for (int i = 0; i < 9; i++) {
         int arrayIndex = getArrayIndex(i, 0);
         if(i > 0) { matrix[i][0] = candidates[arrayIndex].
         changeNumber(i + 1); }
         System.out.print(matrix[i][0]+ " ");
         }
         
         System.out.println();
         System.out.println(checkRow(1, 0));
         System.out.println(checkColoumn(1, 0));
         
         //test checkBox
         System.out.println();
         matrix[0][0] = 1; matrix[0][1] = 2; matrix[0][2] = 3;
         matrix[1][0] = 4; matrix[1][1] = 5; matrix[1][2] = 6;
         matrix[2][0] = 7; matrix[2][1] = 8; matrix[2][2] = 9;
         System.out.println(checkBox(1, 0, 1));
         */
    }
    
//-----------------------------------------------------------------------------    
    // PRINT SUDUKO
    
    public static void visualizeSuduko() {
        // set-up 
        PennDraw.setXscale(0, 100);
        PennDraw.setYscale(0, 100);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setPenRadius(0.005);
        PennDraw.square(50, 55, 40);
        
        double[] xCoord = new double[9];
        double[] yCoord = new double[9];
        
        PennDraw.setPenRadius(0.001);
        // find all xCoordinates
        for( int i = 0; i < 9; i++) {
            double xCoordinate = 10.0 + 80.0/18.0 + i * (160.0/18.0);
            xCoord[i] = xCoordinate;
        }
        
        // find all yCoordinates
        for( int i = 0; i < 9; i++) {
            double yCoordinate = 95 - (80.0/18.0) - i * (160.0/18.0);
            yCoord[i] = yCoordinate;
        }
        
        PennDraw.setFontSize(25);
        //PennDraw.setFontBold();
        
        // draw square of fields
        for ( int i = 0; i < xCoord.length; i++) {
            for ( int j = 0; j < yCoord.length; j++) {
                PennDraw.square( xCoord[i], yCoord[j], 80.0/18.0);
                PennDraw.text( xCoord[i], yCoord[j] - 1.2, matrix[j][i] + "");
                
                // make nine grinds
                if ( (i-1) % 3 == 0 && (j-1) % 3 == 0) {
                    PennDraw.setPenRadius(0.005);
                    PennDraw.square( xCoord[i], yCoord[j], 3 * (80.0/18.0));
                    PennDraw.setPenRadius(0.001);
                }
            }
        }
    }
}
