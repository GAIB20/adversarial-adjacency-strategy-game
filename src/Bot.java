import javafx.scene.control.Button;

public abstract class Bot {
    public abstract int[] move(Button[][] board, int roundsLeft);
}
