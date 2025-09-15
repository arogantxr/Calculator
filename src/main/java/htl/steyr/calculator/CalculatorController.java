package htl.steyr.calculator;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
        String op = ((Button) actionEvent.getSource()).getText();

        if (!operation.isEmpty()) {
            resultButtonClicked(null);
        }
        operation = op;
        firstNumber = Double.parseDouble(resultTextField.getText());
        operationClicked = true;
    }

    public void numberButtonClicked(ActionEvent actionEvent) {
        if (operationClicked) {
            resultTextField.clear();
            operationClicked = false;
        }

        Button source = (Button) actionEvent.getSource();
        resultTextField.appendText(source.getText());
    }

    public void resultButtonClicked(ActionEvent actionEvent) {
        double result = 0;

        if (operation.isEmpty() || resultTextField.getText().isEmpty()) {
            return;
        }

        double secondNumber = Double.parseDouble(resultTextField.getText());

        switch (operation) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "X": result = firstNumber * secondNumber; break;
            case "/": result = firstNumber / secondNumber; break;
        }

        resultTextField.setText(String.valueOf(result));
        operation = "";
        firstNumber = result;
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

    public void handleKeyPress(KeyEvent event) {
        String text = event.getText();

        if (text.matches("[0-9]")) {
            if (operationClicked) {
                resultTextField.clear();
                operationClicked = false;
            }
            resultTextField.appendText(text);
            return;
        }

        switch (text) {
            case "+": case "-": case "/": startOperation(text); break;
            case "*": startOperation("X"); break;
            case "=": case "\r": case "\n": resultButtonClicked(null); break;
            case ".": case ",": commaButtonClicked(null); break;
        }
    }

    private void startOperation(String op) {
        if (!operation.isEmpty()) resultButtonClicked(null);
        operation = op;
        if (!resultTextField.getText().isEmpty()) {
            firstNumber = Double.parseDouble(resultTextField.getText());
        }
        operationClicked = true;
    }
}
