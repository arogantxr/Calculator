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

    public void clearButtonClicked() {
        resultTextField.clear();
        calculateWay.setText(""); // clear label too
        firstNumber = 0;
        operation = "";
    }

    public void mathOperationClicked(ActionEvent actionEvent) {
        String op = ((Button) actionEvent.getSource()).getText();
        ifEmpty(op);
    }

    private void ifEmpty(String op) {
        if (!operation.isEmpty()) {
            resultButtonClicked();
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

    public void resultButtonClicked() {
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
                    System.out.println("Error: Division by zero!");
                    yield Double.NaN; // or show an error message
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

    public void invertButtonClicked() {
        if (!resultTextField.getText().isEmpty()) {
            double number = Double.parseDouble(resultTextField.getText());
            number *= -1;
            resultTextField.setText(String.valueOf(number));
        }
    }

    public void commaButtonClicked() {
        if (!resultTextField.getText().contains(".")) {
            resultTextField.appendText(".");
        }
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        String text = event.getText();


        if (text.matches("[1-6]")) {
            if (operationClicked) {
                resultTextField.clear();
                operationClicked = false;
            }
            resultTextField.appendText(text);
            return;

        } else if (text.matches("8-9")) {
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

                if (event.isShiftDown()) {
                    startOperation("X");
                } else {
                    startOperation("+");
                }
                break;

            case MINUS:
            case SUBTRACT:
                startOperation("-");
                break;

            case DIVIDE:
                startOperation("/");
                break;

            case DIGIT7:
                if (event.isShiftDown()) {
                    startOperation("/");
                } else {
                    if (operationClicked) {
                        resultTextField.clear();
                        operationClicked = false;
                    }
                    resultTextField.appendText("7");
                }
                break;

            case ENTER:
            case EQUALS:
                resultButtonClicked();
                break;

            case PERIOD:
            case COMMA:
                commaButtonClicked();
                break;

            case BACK_SPACE:
                deleteLastChar();
                break;

            case C:
                clearButtonClicked();
                break;

            case CONTROL:
                invertButtonClicked();
                break;

            case DIGIT0:
                if(event.isShiftDown()) {
                    resultButtonClicked();
                } else {
                    resultTextField.appendText("0");
                }

            default:
                if ("*".equals(text)) startOperation("X");
                if ("/".equals(text)) startOperation("/");
                if ("+".equals(text)) startOperation("+");
                if ("-".equals(text)) startOperation("-");
                break;
        }
    }

    private void startOperation(String op) {
        ifEmpty(op);
    }

    private void deleteLastChar() {
        String current = resultTextField.getText();
        if (!current.isEmpty()) {
            resultTextField.setText(current.substring(0, current.length() - 1));
        }
    }
}
