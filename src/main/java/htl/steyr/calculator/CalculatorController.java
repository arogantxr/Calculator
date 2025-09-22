package htl.steyr.calculator;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    public TextField resultTextField;
    public Label calculateWay;

    private double firstNumber = 0;
    private String operation = "";
    private boolean operationClicked = false;

    public void clearButtonClicked(ActionEvent actionEvent) {
        resultTextField.clear();
        calculateWay.setText(""); // clear label too
        firstNumber = 0;
        operation = "";
    }

    public void mathOperationClicked(ActionEvent actionEvent) {
        String op = ((Button) actionEvent.getSource()).getText();
        if (!operation.isEmpty()) {
            resultButtonClicked(null);
        }
        operation = op;
        if (!resultTextField.getText().isEmpty()) {
            firstNumber = Double.parseDouble(resultTextField.getText());
        }
        calculateWay.setText(firstNumber + " " + operation); // update label
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

        result = switch (operation) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "X" -> firstNumber * secondNumber;
            case "/" -> {
                if (secondNumber == 0) {
                    yield Double.NaN;
                } else {
                    yield firstNumber / secondNumber;
                }
            }
            default -> result;
        };


        calculateWay.setText(firstNumber + " " + operation + " " + secondNumber + " ="); // show calculation
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
        KeyCode code = event.getCode();
        String text = event.getText();

        if (text.matches("[0-9]")) {
            if (operationClicked) {
                resultTextField.clear();
                operationClicked = false;
            }
            resultTextField.appendText(text);
            return;
        }

        switch (code) {
            case PLUS:
            case ADD:
                startOperation("+");
                break;
            case MINUS:
            case SUBTRACT:
                startOperation("-");
                break;
            case MULTIPLY:
                startOperation("X");
                break;
            case SLASH:
            case DIVIDE:
                startOperation("/");
                break;
            case ENTER:
            case EQUALS:
                resultButtonClicked(null);
                break;
            case COMMA:
            case PERIOD:
                commaButtonClicked(null);
                break;
            case BACK_SPACE:
                deleteLastChar();
                break;
            case C:
                clearButtonClicked(null);
                break;
            case CONTROL:
                invertButtonClicked(null);
                break;
            default:
                if ("*".equals(text)) startOperation("X");
                if ("/".equals(text)) startOperation("/");
                if ((code == KeyCode.DIGIT7 || code == KeyCode.NUMPAD7) && event.isShiftDown()) {
                    startOperation("/");
                }
                break;
        }
    }

    private void startOperation(String op) {
        if (!operation.isEmpty()) {
            resultButtonClicked(null);
        }
        operation = op;
        if (!resultTextField.getText().isEmpty()) {
            firstNumber = Double.parseDouble(resultTextField.getText());
        }
        calculateWay.setText(firstNumber + " " + operation); // update label
        operationClicked = true;
    }

    private void deleteLastChar() {
        String current = resultTextField.getText();
        if (!current.isEmpty()) {
            resultTextField.setText(current.substring(0, current.length() - 1));
        }
    }
}