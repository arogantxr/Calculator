package htl.steyr.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("calculator-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        // Controller holen
        CalculatorController controller = fxmlLoader.getController();

        // KeyEvents an den Controller weitergeben
        scene.setOnKeyPressed(controller::handleKeyPress);

        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();

        // Fokus setzen, sonst fängt das TextField alle Events ab
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
