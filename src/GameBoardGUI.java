import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GameBoardGUI extends JFrame implements ActionListener,MouseListener{
    private JButton[][] gameBoardGrid;
    private int guiBoardDimensions;
    private GameBoard g1;
    private int seals; // Limit of how many seals can be placed, COMMENT ADDED
    private int sealCounter; //CHANGE MADE, NEW VARIABLE MADE. How many seals have been placed by player.


    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {
            String[] cell;
            int row, column;
            cell = new String[2];
            cell = ((JButton) e.getSource()).getName().split(" ");
            row = Integer.parseInt(cell[0]);
            column = Integer.parseInt(cell[1]);

            if(g1.boardValue(row,column) > 0 && ((JButton)e.getSource()).getText() != "S") { // Changes Made:   && ((JButton)e.getSource()).getText() != "S"
                //System.out.println(cell[0] + " " + cell[1]+ " "+g1.boardValue(row, column));
                ((JButton) e.getSource()).setText(Integer.toString(g1.boardValue(row,column)));

            }else if(g1.boardValue(row, column) == 0 && ((JButton)e.getSource()).getText() != "S") { // Same Change Made
                ((JButton) e.getSource()).setText(Integer.toString(g1.boardValue(row,column)));
                exposeBlocks(row,column);
            }else if(g1.boardValue(row, column) == -1 && ((JButton)e.getSource()).getText() != "S") { // Same Change Made
                ((JButton) e.getSource()).setText("X");
                displayGameBoard();
                JOptionPane.showMessageDialog(this, "Game Over: Mine Exploded", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }else if(e.getButton() == MouseEvent.BUTTON3) {
            if(((JButton) e.getSource()).getText() == "S"){
                ((JButton) e.getSource()).setText("");
                sealCounter--;

            }else if(((JButton) e.getSource()).getText() == "") { //CHANGE MADE HERE
                if(sealCounter == seals) //CHANGE MADE HERE, CHECKS FOR INITIAL SEAL LIMIT BEFORE ADDING ANOTHER SEAL
                    sealLimit();
                else {
                    sealCounter++;
                    ((JButton) e.getSource()).setText("S");
                    if(sealCounter == seals) { // CHANGED MADE, IF STATEMENT HERE TO CHECK FOR WIN AFTER ADDING SEAL CONSIDERING MORE SEALS CAN BE ADDED
                        if(checkSolution() == seals) {
                            displayGameBoard();
                            JOptionPane.showMessageDialog(this, "Game Over: You Win!!", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                        } //CHANGE HERE, Removed else statement
                    }
                }

            }
        }

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void actionPerformed(ActionEvent event) {
		/*String[] cell;
		int row, column;
		cell = new String[2];
		cell = ((JButton) event.getSource()).getName().split(" ");
		row = Integer.parseInt(cell[0]);
		column = Integer.parseInt(cell[1]);


		if(g1.boardValue(row,column) > 0) {
			System.out.println(cell[0] + " " + cell[1]+ " "+g1.boardValue(row, column));
			((JButton) event.getSource()).setText(Integer.toString(g1.boardValue(row,column)));
		}	*/
    }
    public GameBoardGUI() {
        //guiBoardDimensions = 10;
        g1 = new GameBoard();
        seals = g1.getNumberMines();
        sealCounter = 0;

        guiBoardDimensions = g1.getDimensions();
        gameBoardGrid = new JButton[guiBoardDimensions][guiBoardDimensions];


        JFrame gameBoard = new JFrame("Mine Sweeper");
        JPanel panel = new JPanel(new GridLayout(10,10));



        for(int i=0;i<gameBoardGrid.length; i++) {
            for(int j=0; j<gameBoardGrid[i].length; j++) {
                gameBoardGrid[i][j] = new JButton();
                gameBoardGrid[i][j].setName(i + " " + j);
                gameBoardGrid[i][j].setText("");
                panel.add(gameBoardGrid[i][j]);
                gameBoardGrid[i][j].addActionListener(this);
                gameBoardGrid[i][j].addMouseListener(this);
            }
        }
        gameBoard.setSize(500,500);
        gameBoard.getContentPane().add(panel);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setVisible(true);
    }
    private void exposeBlocks(int row, int column) {
        //Cell is found on inner matrix so we have to test all 8 adjacent cells
        if(row != 0 && row != (guiBoardDimensions-1) && column != 0 && column != (guiBoardDimensions-1)) {

            gameBoardGrid[row-1][column-1].setText((gameBoardGrid[row-1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column-1)) : "S");
            gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
            gameBoardGrid[row-1][column+1].setText((gameBoardGrid[row-1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column+1)) : "S");
            gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");
            gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");
            gameBoardGrid[row+1][column-1].setText((gameBoardGrid[row+1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column-1)) : "S");
            gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");
            gameBoardGrid[row+1][column+1].setText((gameBoardGrid[row+1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column+1)) : "S");


        }else if(row == 0) {
            //1st row, 1st column
            if(column == 0) {
                gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");
                gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");
                gameBoardGrid[row+1][column+1].setText((gameBoardGrid[row+1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column+1)) : "S");


                //1st row, last column
            }else if(column == guiBoardDimensions-1) {
                gameBoardGrid[row+1][column-1].setText((gameBoardGrid[row+1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column-1)) : "S");
                gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");
                gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");


                //middle columns
            }else {
                gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");
                gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");
                gameBoardGrid[row+1][column-1].setText((gameBoardGrid[row+1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column-1)) : "S");
                gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");
                gameBoardGrid[row+1][column+1].setText((gameBoardGrid[row+1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column+1)) : "S");

            }

        }else if(row == guiBoardDimensions -1) {
            //last row, 1st column
            if(column == 0) {

                //gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
                //gameBoardGrid[row-1][column+1].setText((gameBoardGrid[row-1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column+1)) : "S");
                //gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");

                //last row, last column
            }else if(column == guiBoardDimensions-1) {

                gameBoardGrid[row-1][column-1].setText((gameBoardGrid[row-1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column-1)) : "S");
                gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
                gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");

                //middle columns
            }else {
                gameBoardGrid[row-1][column-1].setText((gameBoardGrid[row-1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column-1)) : "S");
                gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
                gameBoardGrid[row-1][column+1].setText((gameBoardGrid[row-1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column+1)) : "S");
                gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");
                gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");

            }

        }else if(column == 0) {

            if(row == 0 || row == guiBoardDimensions - 1) {
                ;
            }else {

                gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
                gameBoardGrid[row-1][column+1].setText((gameBoardGrid[row-1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column+1)) : "S");
                gameBoardGrid[row][column+1].setText((gameBoardGrid[row][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row, column+1)) : "S");
                gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");
                gameBoardGrid[row+1][column+1].setText((gameBoardGrid[row+1][column+1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column+1)) : "S");

            }

        }else if(column == guiBoardDimensions-1) {

            if(row == 0 || row == guiBoardDimensions - 1) {
                ;
            }else {

                gameBoardGrid[row-1][column-1].setText((gameBoardGrid[row-1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column-1)) : "S");
                gameBoardGrid[row-1][column].setText((gameBoardGrid[row-1][column].getText() != "S") ? Integer.toString(g1.boardValue(row-1, column)) : "S");
                gameBoardGrid[row][column-1].setText((gameBoardGrid[row][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row, column-1)) : "S");
                gameBoardGrid[row+1][column-1].setText((gameBoardGrid[row+1][column-1].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column-1)) : "S");
                gameBoardGrid[row+1][column].setText((gameBoardGrid[row+1][column].getText() != "S") ? Integer.toString(g1.boardValue(row+1, column)) : "S");

            }

        }

    }
    private int checkSolution() {
        int correctSeals=0;
        for(int i = 0;i<guiBoardDimensions; i++) {
            for(int j=0; j<guiBoardDimensions;j++) {
                if(gameBoardGrid[i][j].getText() == "S" && g1.boardValue(i, j) == -1) {
                    correctSeals++;
                }
            }
        }
        return correctSeals;
    }

    //Check number of seals  //ADDED, CHANGE MADE
    private void sealLimit(){
        JFrame warning = new JFrame("WARNING");
        JTextField message = new JTextField();
        message.setText(" Maximum number of seals used: \"Get sealed yo.\" ");
        message.setEditable(false);

        warning.add(message);
        warning.pack();
        warning.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        warning.setVisible(true);
    }

    private void displayGameBoard() {
        for(int row=0; row< guiBoardDimensions; row++) {
            for(int column=0; column < guiBoardDimensions; column++) {
                if(gameBoardGrid[row][column].getText() == "S" && g1.boardValue(row, column) < 0) {
                    gameBoardGrid[row][column].setBackground(Color.blue);
                }else if(gameBoardGrid[row][column].getText() == "S" && g1.boardValue(row, column) > -1) {
                    gameBoardGrid[row][column].setBackground(Color.red);
                }else if(gameBoardGrid[row][column].getText() != "S" && g1.boardValue(row, column) < 0) {
                    gameBoardGrid[row][column].setBackground(Color.red);
                    gameBoardGrid[row][column].setText("X");
                }else {
                    gameBoardGrid[row][column].setText(Integer.toString(g1.boardValue(row, column)));
                }
            }
        }
    }
    public static void main(String[] args) {
        GameBoardGUI gui = new GameBoardGUI();
    }
}


