module htl.steyr.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.steyr.calculator to javafx.fxml;
    exports htl.steyr.calculator;
}