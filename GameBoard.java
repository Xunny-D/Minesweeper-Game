import java.util.Random;

public class GameBoard {
    private int boardDimension;
    private int numberMines;
    private int[][] gameBoard;

    private void initializeBoard() {
        for(int row = 0; row<boardDimension; row++) {
            for(int column = 0; column<boardDimension; column++) {
                gameBoard[row][column] = 0;
            }
        }
    }
    private void setMines() {
        int counter = 0;
        Random rand = new Random();

        while(counter < numberMines) {
            int row = rand.nextInt(boardDimension);
            int column = rand.nextInt(boardDimension);

            if(gameBoard[row][column] != -1) {
                gameBoard[row][column] = -1;
                counter++;
            }
        }
    }
    public void locateMines() {

        //Loop through game board to find mines
        for(int row=0; row<boardDimension; row++) {
            for(int column=0; column<boardDimension; column++) {
                //If we find a mine the add 1 to adjacent cells
                if(gameBoard[row][column] == -1) {
                    //Mine is found on inner matrix so we have to test all 8 adjacent cells
                    if(row != 0 && row != (boardDimension-1) && column != 0 && column != (boardDimension-1)) {

                        gameBoard[row-1][column-1] = (gameBoard[row-1][column-1] == -1) ? -1 : gameBoard[row-1][column-1]+1;
                        gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                        gameBoard[row-1][column+1] = (gameBoard[row-1][column+1] == -1) ? -1 : gameBoard[row-1][column+1]+1;
                        gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                        gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;
                        gameBoard[row+1][column-1] = (gameBoard[row+1][column-1] == -1) ? -1 : gameBoard[row+1][column-1]+1;
                        gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;
                        gameBoard[row+1][column+1] = (gameBoard[row+1][column+1] == -1) ? -1 : gameBoard[row+1][column+1]+1;

                    }else if(row == 0) {
                        //1st row, 1st column
                        if(column == 0) {
                            gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;
                            gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;
                            gameBoard[row+1][column+1] = (gameBoard[row+1][column+1] == -1) ? -1 : gameBoard[row+1][column+1]+1;
                            //1st row, last column
                        }else if(column == boardDimension-1) {
                            gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;
                            gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                            gameBoard[row+1][column-1] = (gameBoard[row+1][column-1] == -1) ? -1 : gameBoard[row+1][column-1]+1;
                            //middle columns
                        }else {
                            gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                            gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;
                            gameBoard[row+1][column-1] = (gameBoard[row+1][column-1] == -1) ? -1 : gameBoard[row+1][column-1]+1;
                            gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;
                            gameBoard[row+1][column+1] = (gameBoard[row+1][column+1] == -1) ? -1 : gameBoard[row+1][column+1]+1;


                        }
                    }else if(row == boardDimension -1) {
                        //last row, 1st column
                        if(column == 0) {
                            gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;
                            gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                            gameBoard[row-1][column+1] = (gameBoard[row-1][column+1] == -1) ? -1 : gameBoard[row-1][column+1]+1;
                            //last row, last column
                        }else if(column == boardDimension-1) {

                            gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                            gameBoard[row-1][column-1] = (gameBoard[row-1][column-1] == -1) ? -1 : gameBoard[row-1][column-1]+1;
                            gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                            //middle columns
                        }else {
                            gameBoard[row-1][column-1] = (gameBoard[row-1][column-1] == -1) ? -1 : gameBoard[row-1][column-1]+1;
                            gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                            gameBoard[row-1][column+1] = (gameBoard[row-1][column+1] == -1) ? -1 : gameBoard[row-1][column+1]+1;
                            gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                            gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;

                        }
                    }else if(column == 0) {
                        if(row == 0 || row == boardDimension - 1) {
                            ;
                        }else {
                            gameBoard[row-1][column+1] = (gameBoard[row-1][column+1] == -1) ? -1 : gameBoard[row-1][column+1]+1;
                            gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                            gameBoard[row][column+1] = (gameBoard[row][column+1] == -1) ? -1 : gameBoard[row][column+1]+1;
                            gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;
                            gameBoard[row+1][column+1] = (gameBoard[row+1][column+1] == -1) ? -1 : gameBoard[row+1][column+1]+1;

                        }
                    }else if(column == boardDimension-1) {
                        if(row == 0 || row == boardDimension - 1) {
                            ;
                        }else {
                            gameBoard[row-1][column-1] = (gameBoard[row-1][column-1] == -1) ? -1 : gameBoard[row-1][column-1]+1;
                            gameBoard[row-1][column] = (gameBoard[row-1][column] == -1) ? -1 : gameBoard[row-1][column]+1;
                            gameBoard[row][column-1] = (gameBoard[row][column-1] == -1) ? -1 : gameBoard[row][column-1]+1;
                            gameBoard[row+1][column-1] = (gameBoard[row+1][column-1] == -1) ? -1 : gameBoard[row+1][column-1]+1;
                            gameBoard[row+1][column] = (gameBoard[row+1][column] == -1) ? -1 : gameBoard[row+1][column]+1;


                        }
                    }
                }
            }
        }

    }
    public GameBoard() {
        boardDimension = 10;
        numberMines = 10;
        gameBoard = new int[boardDimension][boardDimension];

        initializeBoard();
        setMines();
        locateMines();
        printBoard();
    }
    public GameBoard(int dim,int num) {
        if(dim < 10) {
            boardDimension = 10;
        }else {
            boardDimension = dim;
        }

        if(num<0 || num>boardDimension) {
            numberMines = boardDimension/2;
        }else {
            numberMines = num;
        }

        initializeBoard();

    }
    public void printBoard() {
        for(int row = 0; row<boardDimension; row++) {
            for(int column = 0; column<boardDimension; column++) {
                System.out.print(gameBoard[row][column] + " ");
            }
            System.out.println();
        }
    }
    public int boardValue(int row, int column) {
        return gameBoard[row][column];
    }
    public int getNumberMines() {
        return numberMines;
    }
    public int getDimensions() {
        return boardDimension;
    }


//public static void main(String[] args) {
//		GameBoard g1 = new GameBoard();



//	}
}

