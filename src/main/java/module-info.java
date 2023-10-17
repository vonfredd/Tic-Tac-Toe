module com.iths.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.iths.tictactoe to javafx.fxml;
    exports com.iths.tictactoe;
}