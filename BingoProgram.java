import java.util.Scanner;

public class BingoProgram {
    static final int MAX_BINGO_NUM = 75, MIN_BINGO_NUM = 1;
    static final int BINGO_COL_RANGE = 15;
    static Scanner oScanner = new Scanner(System.in);
    //Main method for the program.
    public static void main(String [] args)
    {
   	 // Define the 2-D arrays bingoNums1, bingoNums2, bingoMatches1, bingoMatches2
   	 String playAgain;
   	 int [][] bingoNums1;
   	 int [][] bingoNums2;
   	 boolean [][] bingoMatches1;
   	 boolean [][] bingoMatches2;
   	 bingoNums1 = new int [5][5];
   	 bingoNums2 = new int [5][5];
   	 bingoMatches1 = new boolean [5][5];
   	 bingoMatches2 = new boolean [5][5];
   	 
   	 //Do-while loop to play Bingo Card.
   	 do
   	 {
   		 fillBingoNums(bingoNums1);
   		 fillBingoNums(bingoNums2);
   		 displayBingoNums("\nYour Bingo Card: ", bingoNums1);
   		 displayBingoNums("\nComputer's Bingo Card: ", bingoNums2);
   		 initBingoMatches(bingoMatches1);
   		 initBingoMatches(bingoMatches2);
   		 playBingo(bingoNums1, bingoNums2, bingoMatches1, bingoMatches2);
   		 System.out.println();
   		 System.out.print("Play again? (Y for yes): ");
   		 playAgain = oScanner.next();
   	 } while (playAgain != "y" | playAgain != "Y");
    }
    
    //Method to fill the Bingo Numbers which depends on the column.
    public static int [][] fillBingoNums(int [][] array)
    {
   	 int row = 0, col = 0;
   	 for(row = 0; row < array.length; row++)
   	 {
   		 int MIN = BINGO_COL_RANGE*row + 1;
   		 int MAX = BINGO_COL_RANGE*(row+1);
   		 for(col = 0; col < array[row].length; col++)
   		 {
   			 array[row][col] =  (int)(Math.random() * (MAX - MIN +1)) + MIN;
   			 if(row == 2 && col == 2)
   				 array[row][col] = -1;
   			 int COL = col -1;
   			 while( COL >= 0)
   			 {
   				 if( array[row][col] == array[row][COL])
   				 {
   					 array[row][col] =  (int)(Math.random() * (MAX - MIN +1)) + MIN;
   					 COL = col -1;
   				 }
   				 else COL--;
   			 }
   		 }
   	 }
   	 return array;
    }
    
    //Method to display the Bingo Number with the BINGO words and the numbers after that.
    public static void displayBingoNums(String bingocard, int [][] array)
    {
   	 System.out.println(bingocard);
   	 String [] name = {"B", "I", "N", "G", "O"};
   	 for(int i= 0; i < name.length; i++)
   	 {
   		 System.out.print(name[i] + "\t");
   	 }
   	 System.out.println();
   	 
   	 for(int col = 0; col <array[0].length; col++)
   	 {
   		 for(int row = 0; row < array.length; row++)
   		 {
   			 if (array[col][row] == -1) {
   				 System.out.print("FREE" + "\t");
   			 } else {
   				 System.out.print(array[row][col] + "\t");
   			 }
   		 }
   		 System.out.println();
   	 }
    }
    
    //Method to initialize the 2-D array BingoMatches to true or false.
    public static boolean [][] initBingoMatches( boolean [][] bingoMatches)
    {
   	 int row = 0, col = 0;
   	 for(row = 0; row < bingoMatches.length; row++)
   	 {
   		 for(col = 0; col < bingoMatches[row].length; col++)
   		 {
   			 bingoMatches[row][col] = false;
   			 bingoMatches[2][2] = true;
   		 }
   	 }
   	 return bingoMatches;
    }
    
    //Method to get the Bingo Number and return the current Number.
    public static int getBingoNumber(boolean []  bingoNumbers)
    {
   	 int MAX_BINGO_NUM = 75, MIN_BINGO_NUM = 1;
   	 int currentNum = 0;
   	 bingoNumbers[currentNum] = false;
   	 do {
   		 currentNum = (int) (Math.random()*MAX_BINGO_NUM) + MIN_BINGO_NUM;
   	 } while(bingoNumbers[currentNum] == true);
   	 return currentNum;    
    }
    
    //Method to display the Current Bingo Number card to the screen.
    public static void displayBingoNum(int currentNum)
    {
   	 int MAX_BINGO_NUM = 75, BINGO_COL_RANGE = 15;
   	 if(currentNum > 0 && currentNum <= MAX_BINGO_NUM)
   	 {
   		 int column = (currentNum - 1) / BINGO_COL_RANGE;
   		 char columnLetter = "BINGO".charAt(column);
   		 System.out.println("Current Bingo Card = " +columnLetter + " " + currentNum + "  ");
   	 }
   	 else if(currentNum == 0)
   		 System.out.println("No Number");
   	 else
   	 {
   		 System.out.print("FREE");
   	 }
    }
    
    //Method to figure out if the current Number matches the 2-D array bingoMatches or not.
    public static boolean findMatch(int[][] bingoNums, int currentNum, boolean[][] bingoMatches) {
   	 int a = (currentNum - 1) / BINGO_COL_RANGE;
   	 for(int col = 0; col < bingoNums[0].length; col++)
   	 {
   		 if(bingoNums[a][col] == currentNum)
   		 {
   			 bingoMatches[a][col] = true;
   			 return true;
   		 }
   	 }
   	 return false;
    }
    
    //Method to check if either the computer or the player wins or not by checking rows, column
    // left or right diagonal.
    public static int [] checkWin(int [][] bingoNums, boolean[][] bingoMatches)
    {
   	 int [] winningNums = null;
   	 boolean allTrue;
   	 // Check rows "Bingo" Column
   	 for(int i = 0; i < bingoMatches.length; ++i)
   	 {
   		 allTrue = true;
   		 for ( int j = 0; j < bingoMatches[i].length; ++j)
   		 {
   			 if ( !bingoMatches[i][j])
   			 {
   				 allTrue = false;
   				 break;
   			 }
   		 }
   			 if ( allTrue) {
   				 winningNums = new int[bingoMatches.length];
   				 for (int j = 0; j < winningNums.length; j++)
   				 {
   					 winningNums[j] = bingoNums[i][j];
   				 }
   			 return winningNums;
   		 }
   	 }
   	 //Check column ("Bingo" rows)
   	 for(int i = 0; i < bingoMatches.length; ++i)
   	 {
   		 allTrue = true;
   		 for ( int j = 0; j < bingoMatches[i].length; ++j)
   		 {
   			 if ( !bingoMatches[i][j])
   			 {
   			 allTrue = false;
   			 break;
   			 }
   	 }
   	 if ( allTrue) {
   		 winningNums = new int[bingoMatches.length];
   		 for (int j = 0; j < winningNums.length; j++)
   		 {
   			 winningNums[j] = bingoNums[j][i];
   		 }
   		 return winningNums;
   		 }    
   	 }
   	 //Check left diagonal
   	 allTrue = true;
   	 for(int i = 0; i < bingoMatches.length; i++)
   	 {
   		 if(!bingoMatches[i][i])
   			 allTrue = false;   	 
   	 }
   	 if( allTrue)
   	 {
   		 winningNums = new int[bingoMatches.length];
   		 for (int j = 0; j < winningNums.length; j++)
   		 {
   			 winningNums[j] = bingoNums[j][j];
   		 }
   		 return winningNums;
   	 }
   	 //Check Right diagonal
   	 allTrue = true;
   	 int j = bingoMatches.length -1;
   	 for(int i = 0; i < bingoMatches.length; i++)
   	 {
   		 if(!bingoMatches[i][j - i])
   			 allTrue = false;
   	 }
   	 if( allTrue)
   	 {
   		 winningNums = new int[bingoMatches.length];
   		 for (int k = 0; k < winningNums.length; k++)
   		 {
   			 winningNums[k] = bingoNums[k][j - k];
   		 }
   		 return winningNums;
   	 }
   	 return winningNums;
    }
    
    //Method to play the Bingo Card game.
    public static void playBingo(int [][] bingoNums1, int [][] bingoNums2, boolean [][] bingoMatches1, boolean [][] bingoMatches2)
    {
   	 Scanner scanner = new Scanner(System.in);
   	 String answer;
   	 boolean [] bingoArray;
   	 bingoArray = new boolean[76];
   	 System.out.println("\nThe Bingo Game will continue until someone wins or you enter 'N'.");
   	 int currentNum;
   	 do
   	 {
   		 currentNum = getBingoNumber(bingoArray);
   		 displayBingoNum(currentNum);
   		 if( findMatch(bingoNums1, currentNum, bingoMatches1) == true)
   			 System.out.println("You matched the number!");
   		 int [] array = checkWin(bingoNums1, bingoMatches1);
   		 if(array != null)
   		 {
   			 System.out.println("You won!");
   			 System.out.print("Winning numbers are: ");
   			 for(int i = 0; i < array.length; i++)
   			 {
   				 int column = (array[i] -1) / BINGO_COL_RANGE;
   				 char columnLetter = "BINGO".charAt(column);
   				 if(array[i] == -1)
   					 System.out.print("FREE ");
   				 else
   					 System.out.print(columnLetter + " " + array[i] + " ");
   			 }
   			 break;
   		 }
   		 if( findMatch(bingoNums2, currentNum, bingoMatches2) == true)
   			 System.out.println("The computer matched the number.");
   		 int [] Array = checkWin(bingoNums2, bingoMatches2);
   		 if(Array != null)
   		 {
   			 System.out.println("Computer won :-(");
   			 System.out.print("Winning numbers are: ");
   			 for(int i = 0; i < Array.length; i++)
   			 {
   				 int column = (Array[i]-1) / BINGO_COL_RANGE;
   				 char columnLetter = "BINGO".charAt(column);
   				 if(Array[i] == -1)
   					 System.out.print("FREE ");
   				 else
   					 System.out.print(columnLetter + " " + Array[i] + " ");
   			 }
   			 break;
   		 }
   		 System.out.print("Keep playing? (N for No): ");
   		 answer = scanner.next();
   	 } while (answer != "Y" || answer != "y");
    }
}
