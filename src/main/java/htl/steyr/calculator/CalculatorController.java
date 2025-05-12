package htl.steyr.calculator;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {
    public TextField resultTextField;

    private double firstNumber = 0;
    private String operation = "";
    private boolean operationClicked = false;

    public void clearButtonClicked(ActionEvent actionEvent) {
        resultTextField.clear();
        firstNumber = 0;
        operation = "";
    }

    public void mathOperationClicked(ActionEvent actionEvent) {

        if(!operation.isEmpty()){
            resultButtonClicked(actionEvent);
        }
        operation = ((Button) actionEvent.getSource()).getText();
        firstNumber = Double.parseDouble(resultTextField.getText());
        operationClicked = true;


    }

    public void numberButtonClicked(ActionEvent actionEvent) {
        if (operationClicked){
            resultTextField.clear();
            operationClicked = false;
        }

        Button source = (Button) actionEvent.getSource();
        resultTextField.appendText(source.getText());
    }

    public void resultButtonClicked(ActionEvent actionEvent) {
        double result = 0;

        switch (operation){
            case "+":
                result = firstNumber + Double.parseDouble(resultTextField.getText());
                break;

            case "-":
                result = firstNumber - Double.parseDouble(resultTextField.getText());
                break;

            case "X":
                result = firstNumber * Double.parseDouble(resultTextField.getText());
                break;

            case "/":
                result = firstNumber / Double.parseDouble(resultTextField.getText());
                break;
        }

        resultTextField.setText(String.valueOf(result));
    }

    public void invertButtonClicked(ActionEvent actionEvent) {
        if (!resultTextField.getText().isEmpty()) {
            double number = Double.parseDouble(resultTextField.getText());
            number *= -1;
            resultTextField.setText(String.valueOf(number));
        }
    }


    public void commaButtonClicked(ActionEvent actionEvent) {
        if (!resultTextField.getText().contains(".")) {
            resultTextField.appendText(".");
        }
    }
}
