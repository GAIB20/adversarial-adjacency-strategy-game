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
                    board[i][j].setText("O");
                    int score = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false, roundsLeft, i, j);
                    board[i][j].setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[] { i, j };
                    }
                }
            }
        }
        return move;
    }

    public int[] range(int i, int j) {
        int[] range = new int[4];
        int startRow, endRow, startColumn, endColumn;
        if (i - 3 < 0) // If clicked button in first row, no preceding row exists.
            startRow = i;
        else // Otherwise, the preceding row exists for adjacency.
            startRow = i - 3;

        if (i + 3 >= 8) // If clicked button in last row, no subsequent/further row exists.
            endRow = i;
        else // Otherwise, the subsequent row exists for adjacency.
            endRow = i + 3;

        if (j - 3 < 0) // If clicked on first column, lower bound of the column has been reached.
            startColumn = j;
        else
            startColumn = j - 3;

        if (j + 3 >= 8) // If clicked on last column, upper bound of the column has been reached.
            endColumn = j;
        else
            endColumn = j + 3;
        range[0] = startRow;
        range[1] = endRow;
        range[2] = startColumn;
        range[3] = endColumn;
        // System.out.println("startRow: " + startRow + " endRow: " + endRow + " startColumn: " + startColumn + " endColumn: " + endColumn);
        return range;
    }

    public int minimax(Button[][] board, int depth, int alpha, int beta, boolean isMaximizing, int roundsLeft, int i, int j) {
        // Check if the game is over
        if (roundsLeft == 0) {
            return evaluate(board);
        }

        if (isMaximizing){
            int bestScore = Integer.MIN_VALUE;
            for (int startRow = 0; startRow < 3; startRow++) {
                for (int startColumn = 0; startColumn < 3; startColumn++) {
                    if (board[startRow][startColumn].getText().equals("")) {
                        board[startRow][startColumn].setText("O");
                        int score = minimax(board, depth + 1, alpha, beta, false, roundsLeft - 1, startRow, startColumn);
                        board[startRow][startColumn].setText("");
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
            for (int startRow = 0; startRow < 3; startRow++) {
                for (int startColumn = 0; startColumn < 3; startColumn++) {
                    if (board[startRow][startColumn].getText().equals("")) {
                        board[startRow][startColumn].setText("X");
                        int score = minimax(board, depth + 1, alpha, beta, true, roundsLeft - 1, startRow, startColumn);
                        board[startRow][startColumn].setText("");
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
        // Check if the board is available
        // if (isMaximizing) {
        //     int bestScore = Integer.MIN_VALUE;
        //     int[] range = range(i, j);
        //     for (int startRow = range[0]; startRow <= range[1]; startRow++) {
        //         for (int startColumn = range[2]; startColumn <= range[3]; startColumn++) {
        //             if (board[startRow][startColumn].getText().equals("")) {
        //                 board[startRow][startColumn].setText("O");
        //                 int score = minimax(board, depth + 1, alpha, beta, false, roundsLeft - 1, startRow,
        //                         startColumn);
        //                 board[startRow][startColumn].setText("");
        //                 bestScore = Math.max(score, bestScore);
        //                 alpha = Math.max(alpha, score);
        //                 if (beta <= alpha) {
        //                     break;
        //                 }
        //             }
        //         }
        //     }
        //     return bestScore;
        // } else {
        //     int bestScore = Integer.MAX_VALUE;
        //     int[] range = range(i, j);
        //     for (int startRow = range[0]; startRow <= range[1]; startRow++) {
        //         for (int startColumn = range[2]; startColumn <= range[3]; startColumn++) {
        //             if (board[startRow][startColumn].getText().equals("")) {
        //                 board[startRow][startColumn].setText("X");
        //                 int score = minimax(board, depth + 1, alpha, beta, true, roundsLeft - 1, startRow, startColumn);
        //                 board[startRow][startColumn].setText("");
        //                 bestScore = Math.min(score, bestScore);
        //                 beta = Math.min(beta, score);
        //                 if (beta <= alpha) {
        //                     break;
        //                 }
        //             }
        //         }
        //     }
        //     return bestScore;
        // }
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
    public int evaluate(Button[][] board) {
        int[] score = new int[2];
        score[0] = 0;
        score[1] = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals("X")) {
                    score[0]++;
                } else if (board[i][j].getText().equals("O")) {
                    score[1]++;
                }
            }
        }
        return score[1] - score[0];
    }
}