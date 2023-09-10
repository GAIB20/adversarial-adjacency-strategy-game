import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class Bot {
    public int[] move(Button[][] board, int roundsLeft) {
        int[] move = new int[2];
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Check if the board is available
                if (board[i][j].getText().equals("")) {
                    board = updateGameBoard(i, j, board, "O");
                    int score = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false, roundsLeft, i, j);
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[] { i, j };
                    }
                    board[i][j].setText("");
                }
            }
        }
        return move;
    }

    public int minimax(Button[][] board, int depth, int alpha, int beta, boolean isMaximizing, int roundsLeft, int i,
            int j) {
        // Check if the game is over
        if ((boardElements(board) - 8) / 2 >= roundsLeft) {
            return evaluate(board);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int startRow = 0; startRow < 8; startRow++) {
                for (int startColumn = 0; startColumn < 8; startColumn++) {
                    if (board[startRow][startColumn].getText().equals("")) {
                        Button[][] tempBoard = new Button[8][8];
                        for (int x = 0; x < 8; x++) {
                            for (int y = 0; y < 8; y++) {
                                tempBoard[x][y] = new Button();
                                tempBoard[x][y].setText(board[x][y].getText());
                            }
                        }
                        board[startRow][startColumn].setText("O");
                        board = updateGameBoard(startRow, startColumn, board, "O");
                        int score = minimax(board, depth + 1, alpha, beta, false, roundsLeft, startRow, startColumn);
                        board = tempBoard;
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int startRow = 0; startRow < 8; startRow++) {
                for (int startColumn = 0; startColumn < 8; startColumn++) {
                    if (board[startRow][startColumn].getText().equals("")) {
                        Button[][] tempBoard = new Button[8][8];
                        for (int x = 0; x < 8; x++) {
                            for (int y = 0; y < 8; y++) {
                                tempBoard[x][y] = new Button();
                                tempBoard[x][y].setText(board[x][y].getText());
                            }
                        }
                        board[startRow][startColumn].setText("X");
                        board = updateGameBoard(startRow, startColumn, board, "X");
                        int score = minimax(board, depth + 1, alpha, beta, true, roundsLeft, startRow, startColumn);
                        board = tempBoard;
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * Get the score of the player
     * 
     * @param board is the game board
     * 
     * @return the score of the player
     *         [0] is the score of the player (X)
     *         [1] is the score of the bot (O)
     */
    /**
     * Change adjacent cells to X's or O's.
     *
     * @param i The row number of the button clicked.
     * @param j The column number of the button clicked.
     *
     */
    private Button[][] updateGameBoard(int i, int j, Button[][] board, String player) {
        // Value of indices to control the lower/upper bound of rows and columns
        // in order to change surrounding/adjacent X's and O's only on the game board.
        // Four boundaries: First & last row and first & last column.
        int startRow, endRow, startColumn, endColumn;
        
        if (i - 1 < 0) // If clicked button in first row, no preceding row exists.
        startRow = i;
        else // Otherwise, the preceding row exists for adjacency.
        startRow = i - 1;
        
        if (i + 1 >= 8) // If clicked button in last row, no subsequent/further row exists.
        endRow = i;
        else // Otherwise, the subsequent row exists for adjacency.
        endRow = i + 1;
        
        if (j - 1 < 0) // If clicked on first column, lower bound of the column has been reached.
        startColumn = j;
        else
        startColumn = j - 1;
        
        if (j + 1 >= 8) // If clicked on last column, upper bound of the column has been reached.
            endColumn = j;
        else
        endColumn = j + 1;
        
        // Search for adjacency for X's and O's or vice versa, and replace them.
        // Update scores for X's and O's accordingly.
        for (int x = startRow; x <= endRow; x++) {
            for (int y = startColumn; y <= endColumn; y++) {
                if (board[x][y].getText().equals("X") && player.equals("O")) {
                    board[x][y].setText("O");
                } else if (board[x][y].getText().equals("O") && player.equals("X")) {
                    board[x][y].setText("X");
                }
            }
        }
        return board;
    }
    
    public int evaluate(Button[][] board) {
        int playerXScore = 0;
        int playerOScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals("X")) {
                    playerXScore++;
                } else if (board[i][j].getText().equals("O")) {
                    playerOScore++;
                }
            }
        }
        return playerOScore - playerXScore;
    }

    public void printBoard(Button[][] board) {
        // it must be 8x8 grid, pay attention to the whitespaces
        for (int i = 0; i < 8; i++) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals(""))
                    System.out.print(" |");
                else
                    System.out.print(board[i][j].getText() + "|");
            }
            System.out.println();
        }
    }

    public int boardElements(Button[][] board) {
        int elements = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].getText().equals(""))
                    elements++;
            }
        }
        return elements;
    }
}