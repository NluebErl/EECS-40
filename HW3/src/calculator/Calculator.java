package calculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.lang.Math;

/**
 * Calculator does not have a constructor, so the default is just empty.
 * Calculator holds many different values including the textfield, operands, operator, newNumber, errorFlag, and memory
 * textfield displays the text in the calculator GUI and is updated based on operands and operator operations.
 * operands are the numbers inputted by the user that they do the arithmetic with.
 * operator is stored to tell the program what operation is currently being done (add, subtract, multiply, etc)
 * newNumber is used to tell the program if the user can input a new number or not. This is denied whenever certain scenarios are met such as double decimal points, and reset when an error occurs or after updating the operator button.
 * errorFlag flags determine whether or not an error should've occurred and determines if the ERROR message should be printed.
 * memory holds the memory of the function.
 */
public class Calculator extends Application
{
    private TextField textfield;
    private double operand1 = 0;
    private double operand2 = 0;
    private String operator = "";
    private boolean newNumber = true;
    private boolean errorFlag = false;
    private double memory = 0;

    @Override
    /**
     * Start creates a stage to initialize, format, and hold all of the nodes and scenes of the program GUI.
     * It initializes all the buttons, textfields, and gridpanes.
     */
    public void start(Stage stage)
    {
        // Set up the textfield for the new stage and ensure that the user cannot edit the text directly
        textfield = new TextField("0");
        textfield.setEditable(false);
        textfield.setPrefHeight(60);
        textfield.setAlignment(Pos.CENTER_RIGHT);

        // Set up a grid pane for the buttons.
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Font customFont = Font.font("Times New Roman", FontWeight.BOLD, 15);
        // 2D string array of all the buttons to be added to the calculator GUI.
        String[][] buttons = {
                {"MC", "MR", "M+", "M-", "AC"},
                {"7", "8", "9", "/", "1/x"},
                {"4", "5", "6", "*", "√"},
                {"1", "2", "3", "-", "x!"},
                {"0", ".", "+/-", "+", "="},
                {"ln", "**"}};

        // Convert all the button names to buttons with functionality
        int row = 1;
        for (String[] line : buttons)
        {
            int col = 0;
            for(String label : line)
            {
                Button btn = new Button(label);
                btn.setPrefSize(70,50);
                gridPane.add(btn, col, row);
                col++;

                btn.setOnAction(e -> handleButton(label));
                btn.setFont(customFont);
                if (label.equals("6") || label.equals("7"))
                {
                    btn.setStyle("-fx-background-color: #0AFF3B; -fx-text-fill: black");
                }
                else {
                    btn.setStyle("-fx-background-color: #FFA3EE; -fx-text-fill: #000000;");
                }
            }
            row++;
        }

        // Update the stage to display all of the buttons, scene name, and nodes
        gridPane.add(textfield, 0, 0, 5,1);
        gridPane.setStyle("-fx-background-color: #F7D0F1;");
        Scene scene = new Scene(gridPane,400,400);
        stage.setTitle("Ethan's BEAUTIFUL Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles all of the linking from button labels to button functionality using other methods in the Calculator class.
     * @param label String label name used to link each button to a certain function.
     */
    private void handleButton(String label)
    {
        try
        {
            if (label.equals("MC")) {
                memoryClear();
            } else if (label.equals("MR")) {
                memoryRecall();
            } else if (label.equals("M-")) {
                memorySubtract();
            } else if (label.equals("M+")) {
                memoryAdd();
            } else if (label.equals("AC")) {
                clearAll();
            } else if (label.equals("+")) {
                setOperator(label);
            } else if (label.equals("-")) {
                setOperator(label);
            } else if (label.equals("*")) {
                setOperator(label);
            }else if (label.equals("/")){
                setOperator(label);
            } else if (label.equals("x!")) {
                handleFactorial();
            } else if (label.equals("√")) {
                handleSqrt();
            } else if (label.equals("1/x")) {
                handleReciprocal();
            } else if (label.equals("ln")) {
                handleLog();
            } else if (label.equals("**")) {
                setOperator(label);
            } else if (label.equals("+/-")) {
                handleToggleSign();
            } else if (label.equals("=")) {
                calculate();
            } else if (label.matches("[0-9]") || label.equals(".")){
                append(label);
            }
        }
        catch (Exception ex)
        {
            textfield.setText("ERROR");
        }
    }

    // ---------------------------Number Input Methods------------------------------------------

    /**
     * Appends either numbers or a decimal point to the textfield to be displayed
     * @param value The string value paresed from the handleButton method; takes only values from 0-9 and decimal point (things that show up on the textfield when pressed)
     */
    private void append(String value)
    {
        if (newNumber)
        {
            // Checks if the user can input a decimal point if already written, else adds the number
            textfield.setText(value.equals(".") ? "0." : value);
            newNumber = false;
        }
        else
        {
            // If a decimal point is already in the number, cant add another.
            if (value.equals(".") && textfield.getText().contains("."))
            {
                return;
            }
            textfield.setText(textfield.getText() + value);
        }
    }

    // Change the operator

    /**
     * Changes the operator and sets up the first operand
     * @param op The operation pressed on the calculator GUI
     */
    private void setOperator(String op)
    {
        operand1 = Double.parseDouble(textfield.getText());
        operator = op;
        newNumber = true;
    }

    // ------------------------------------ Math operations ------------------------------------------

    /**
     * Add function adds double values a and b
     * @param a first operand being added
     * @param b second operand being added
     * @return the addition of operands a and b
     */
    public double add(double a, double b)
    {
        return a + b;
    }

    /**
     * Subtract function subtracts two double values b from a
     * @param a first operand being subtracted (minuend)
     * @param b second operand subtracting from the first (subtrahend)
     * @return difference between a and b
     */
    public double subtract(double a, double b)
    {
        return a - b;
    }

    /**
     * Multiply multiplies double values a and b
     * @param a first operand being multiplied
     * @param b second operand being multiplied
     * @return the product of a and b
     */
    public double multiply(double a, double b)
    {
        return a * b;
    }

    /**
     * Divide divides a and b and returns a quotient. Marks errorFlag if a condition is considered illegal.
     * @param a the numerator being divided
     * @param b the denominator dividing
     * @return the quotient of a and b
     */
    public double divide(double a, double b)
    {
        // Illegal to divide x/0
        if (b == 0)
        {
            errorFlag = true;
            return 0;
        }
        return a / b;
    }

    /**
     * Finds the power of a to the b power. Marks errorFlag if a condition is considered illegal.
     * @param a the base operand
     * @param b the exponent operand
     * @return the result of base a to the power of b
     */
    public double power(double a, double b)
    {
        // Cannot take a root of a negative number
        if (a < 0 && b % 1 != 0)
        {
            errorFlag = true;
            return 0;
        }
        return Math.pow(a, b);
    }

    /**
     * Finds the square root of a double number x. Marks errorFlag if a condition is considered illegal.
     * @param x the number being raised to the power of 1/2 (or being square rooted)
     * @return the square root of the number
     */
    public double sqrt(double x)
    {
        // Cannot take the square root of a negtive number
        if (x < 0)
        {
            errorFlag = true;
            return 0;
        }
        return Math.sqrt(x);
    }

    /**
     * Takes the natural logarithm of the number x. Marks errorFlag if a condition is considered illegal.
     * @param x The operand of which logarithm is being applied to
     * @return The natural logarithm of the number x
     */
    public double log(double x)
    {
        // Cannot take the log of 0 or negative numbers
        if (x <= 0)
        {
            errorFlag = true;
            return 0;
        }
        return Math.log(x);
    }

    /**
     * Takes the factorial of parameter x and returns it. This is done iteratively. Marks errorFlag if a condition is considered illegal.
     * @param x operand that the function is taking the factorial of.
     * @return the factorial of parameter x
     */
    public double factorial(double x)
    {
        // Cannot take the factorial of a negative number or a decimal. Factorial of anything above 10 is too large, so mark that as error as well.
        if (x < 0 || x > 10 || x % 1 != 0)
        {
            errorFlag = true;
            return 0;
        }
        double result = 1;
        for (int i = 2; i <= x; i++)
        {
            result *= i;
        }
        return result;
    }

    /**
     * Flips the sign of a double x by multiplying it by -1
     * @param x the number being flipped
     * @return the negative of parameter x
     */
    public double toggleSign(double x)
    {
        return -x;
    }

    /**
     * Takes the inverse of parameter x. Marks errorFlag if a condition is considered illegal.
     * @param x operand that is being flipped (taken the inverse)
     * @return the inverse of x (1/x or x^-1)
     */
    public double reciprocal(double x)
    {
        // Cannot take the inverse of 0 since it is the same as x/0
        if (x == 0)
        {
            errorFlag = true;
            return 0;
        }
        return 1 / x;
    }

    // Conversion of instant functions

    /**
     * Tests if the square root of a number will result in an error based on the parameters in the textfield (parsed).
     * Will display an error if the errorFlag is updated and the value of the operation if it isn't updated.
     */
    private void handleSqrt()
    {
        // Check if the errorFlag gets raised
        double value = Double.parseDouble(textfield.getText());
        errorFlag = false;
        value = sqrt(value);
        // If the error flag is raised, display an error and get a new value. Otherwise just display the answer.
        textfield.setText(errorFlag ? "ERROR" : format(value));
        newNumber = true;
    }

    /**
     * Tests if the logarithm of a number will result in an error based on the parameters in the textfield (parsed).
     * Will display an error if the errorFlag is updated and the value of the operation if it isn't updated.
     */
    private void handleLog()
    {
        // Checks if an errorFlag gets raised
        double value = Double.parseDouble(textfield.getText());
        errorFlag = false;
        value = log(value);
        // If an error is raised, display an error. Otherwise just display the answer of the operation.
        textfield.setText(errorFlag ? "ERROR" : format(value));
        newNumber = true;
    }

    /**
     * Tests if the factorial of a number will result in an error based on the parameters in the textfield (parsed).
     * Will display an error if the errorFlag is updated and the value of the operation if it isn't updated.
     */
    private void handleFactorial()
    {
        // Checks if the errorFlag gets raised
        double value = Double.parseDouble(textfield.getText());
        errorFlag = false;
        value = factorial(value);
        // If the errorFlag is updated, display ERROR on the textfield. Otherwise just display the answer of the operation.
        textfield.setText(errorFlag ? "ERROR" : format(value));
        newNumber = true;
    }

    /**
     * Tests if the reciprocal of a number will result in an error based on the parameters in the textfield (parsed).
     * Will display an error if the errorFlag is updated and the value of the operation if it isn't updated.
     */
    private void handleReciprocal()
    {
        // Checks if the errorFlag is raised
        double value = Double.parseDouble(textfield.getText());
        errorFlag = false;
        value = reciprocal(value);
        // If the errorFlag is updated, display ERROR on the textfield. Otherwise just display the answer of the operation.
        textfield.setText(errorFlag ? "ERROR" : format(value));
        newNumber = true;
    }

    /**
     * Handles the display of the new value displayed on the textfield after it gets flipped in sign.
     */
    private void handleToggleSign()
    {
        double value = Double.parseDouble(textfield.getText());
        value = toggleSign(value);
        textfield.setText(format(value));
        newNumber = true;
    }

    // Calculate the math when clicking '='

    /**
     * After the '=' button is clicked, run calculate. This method pareses the operator value and links it to the actual calculation done in previous methods.
     * Displays ERROR if an error flag was raised and gets the calculator ready for the next cycle of operations.
     */
    private void calculate()
    {
        // If an operator is not pressed, return
        if (operator.isEmpty())
        {
            return;
        }
        operand2 = Double.parseDouble(textfield.getText());
        double result;
        errorFlag = false;
        // Check what operation to do based on what the operator is using values of operands 1 and 2.
        switch (operator) {
            case "*": {
                result = multiply(operand1, operand2);
                break;
            }
            case "/": {
                result = divide(operand1, operand2);
                break;
            }
            case "+": {
                result = add(operand1, operand2);
                break;
            }
            case "-": {
                result = subtract(operand1, operand2);
                break;
            }
            case "**": {
                result = power(operand1, operand2);
                break;
            }
            default:
                return;
        }
        // If an error flag was raised, display an error message and continue
        if (errorFlag)
        {
            textfield.setText("ERROR");
        }
        else
        {
            textfield.setText(format(result));
            operand1 = result;
        }
        newNumber = true;
        operator = "";
    }

    // ------------------------------------ Memory Functionality -----------------------------------------

    /**
     * Clear the memory value (if anything was changed in it)
     * Restore it back to 0
     */
    private void memoryClear()
    {
        memory = 0;
    }

    /**
     * Display the value of the memory and allow a new input
     */
    private void memoryRecall()
    {
        textfield.setText(format(memory));
        newNumber = true;
    }

    /**
     * Adds the current value in the textfield to the memory
     */
    private void memoryAdd()
    {
        memory += Double.parseDouble(textfield.getText());
    }

    /**
     * Subtracts the current value in the textfield from the memory
     */
    private void memorySubtract()
    {
        memory -= Double.parseDouble(textfield.getText());
    }

    // AC functionality, reset all values

    /**
     * Resets the textfield, operands, and operation values (everything except memory).
     * Resets back to its original state when launched.
     */
    private void clearAll()
    {
        textfield.setText("0");
        operand1 = 0;
        operand2 = 0;
        operator = "";
        newNumber = true;
        errorFlag = false;
    }

    // Formatting method

    /**
     * Formats the number results into a String used for displaying on the textfield
     * @param result The result of an operation or function to be converted into a String
     * @return The String value of the result inputted. Turns into an integer or remains a double if type casting of the value remains the same as the original.
     */
    private String format(double result)
    {
        return ((result == (long)result) ? String.valueOf((long)result) : String.valueOf(result));
    }

    /**
     * Main function of the program which runs JavaFX GUI and program.
     * @param args commandline inputs
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
