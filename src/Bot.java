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
    public int[] move(Button[][] buttons) {
        // create random move
        int[] move = new int[2];
        move[0] = (int) (Math.random() * 3);
        move[1] = (int) (Math.random() * 3);
        // check if move is valid
        while (!buttons[move[0]][move[1]].getText().equals("")) {
            move[0] = (int) (Math.random() * 3);
            move[1] = (int) (Math.random() * 3);
        }
        return move;
    }
}
