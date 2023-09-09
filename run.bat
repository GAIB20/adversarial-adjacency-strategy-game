javac -cp ./src --module-path "./javafx-sdk/lib" --add-modules=javafx.controls,javafx.fxml src/*.java
java -cp ./src --module-path "./javafx-sdk/lib" --add-modules=javafx.controls,javafx.fxml Main