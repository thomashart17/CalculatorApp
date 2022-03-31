package com.thomashart.calculatorapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MainActivity class for the calculator app.
 * @author Thomas Hart
 */
public class MainActivity extends Activity implements OnCalculatorClickListener {

    // TextViews
    TextView calculatorText;
    TextView answerText;

    // ArrayLists of numbers and operators
    ArrayList<BigDecimal> inputNumbers;
    ArrayList<Operator> inputOperators;

    // HashMap to convert from String to corresponding Operator
    HashMap<String, Operator> operatorHashMap = new HashMap<>();

    private int currentNumberIndex = 0;
    private boolean decimalStatus = false;
    private Toast toast = null;

    /**
     * onCreate: Initializes default application state when activity is created.
     * @param savedInstanceState Saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up TextViews for calculator display
        calculatorText = findViewById(R.id.calculator_text);
        calculatorText.setText("0");
        answerText = findViewById(R.id.answer_text);

        // Setting up ArrayLists to store input
        inputNumbers = new ArrayList<>();
        inputOperators = new ArrayList<>();

        initOperatorHashMap();
        initToast();
    }

    /**
     * initOperatorHashMap: Initializes operator text ids to corresponding enum values.
     */
    private void initOperatorHashMap() {
        operatorHashMap.put(getString(R.string.plus), Operator.PLUS);
        operatorHashMap.put(getString(R.string.minus), Operator.MINUS);
        operatorHashMap.put(getString(R.string.multiply), Operator.MULTIPLY);
        operatorHashMap.put(getString(R.string.divide), Operator.DIVIDE);
        operatorHashMap.put(getString(R.string.modulus), Operator.MODULUS);
    }

    /**
     * initToast: Initializes toast that is displayed for invalid syntax.
     */
    private void initToast() {
        Context context = getApplicationContext();
        CharSequence message = "Invalid Syntax";
        int duration = Toast.LENGTH_SHORT;
        toast = Toast.makeText(context, message, duration);
    }

    /**
     * onNumberClick: Handles input from number buttons.
     * @param view View that was clicked
     */
    @Override
    public void onNumberClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        Button pressedButton = findViewById(view.getId());
        updateText(pressedButton.getText().toString());
    }

    /**
     * onOperatorClick: Handles input for operator buttons.
     * @param view View that was clicked
     */
    @Override
    public void onOperatorClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        switch (view.getId()) {
            case R.id.clear_button:
                clear();
                break;
            case R.id.equals_button:
                if (answerText.length() == 0) {
                    if (currentNumberIndex != calculatorText.getText().length()) {
                        addLastNumber();
                        calculate();
                    } else {
                        invalidSyntax();
                    }
                } else {
                    updateText("");
                    calculate();
                }
                break;
            case R.id.plus_minus_button:
                break;
            case R.id.decimal_button:
                if (!decimalStatus) {
                    if (isDigit(calculatorText.getText().charAt(calculatorText.length() - 1)) && answerText.length() == 0) {
                        updateText(".");
                    } else {
                        updateText("0.");
                    }
                    decimalStatus = true;
                }
                break;
            default:
                TextView operatorText = findViewById(view.getId());
                if (isDigit(calculatorText.getText().charAt(calculatorText.length()-1))) {
                    inputOperators.add(operatorHashMap.get(operatorText.getText().toString()));
                    if (answerText.length() == 0) {
                        addLastNumber();
                    }
                    updateText(operatorText.getText().toString());
                } else if (calculatorText.getText().charAt(calculatorText.length()-1) == 46) {
                    inputOperators.add(operatorHashMap.get(operatorText.getText().toString()));
                    if (answerText.length() == 0) {
                        addLastNumber();
                    }
                    replaceLastChar(operatorText.getText().toString());
                } else {
                    inputOperators.set(inputOperators.size()-1, operatorHashMap.get(operatorText.getText().toString()));
                    replaceLastChar(operatorText.getText().toString());
                }
                decimalStatus = false;
                break;
        }
    }

    /**
     * isNumber: Checks if given character is a digit
     * @param c Character to be checked
     * @return true if c is a digit, false if it isn't
     */
    private boolean isDigit(char c) {
        return c >= 48 && c <= 57;
    }

    /**
     * updateText: Updates text displayed on calculator screen.
     * @param update String to be added to display
     */
    private void updateText(String update) {
        if (answerText.length() > 0) {
            String answer = answerText.getText().toString();
            clear();
            if (update.length() == 0) {
                calculatorText.setText(answer);
                inputNumbers.add(new BigDecimal(answer));
            } else if (isDigit(update.charAt(0))) {
                calculatorText.setText(update);
            } else {
                calculatorText.setText(answer + update);
                inputNumbers.add(new BigDecimal(answer));
                inputOperators.add(operatorHashMap.get(update));
                currentNumberIndex = calculatorText.length();
            }
        } else if (calculatorText.getText().equals("0")) {
            if (update.charAt(0) >= 49 && update.charAt(0) <= 57) {
                calculatorText.setText(update);
            } else if (update.charAt(0) != 48) {
                calculatorText.setText(calculatorText.getText() + update);
            }
        } else {
            calculatorText.setText(calculatorText.getText() + update);
        }
    }

    /**
     * replaceLastChar: Replaces the last character in the calculator TextView.
     * @param update the character to use as a replacement.
     */
    private void replaceLastChar(String update) {
        calculatorText.setText(calculatorText.getText().subSequence(0, calculatorText.length()-1) + update);
    }

    /**
     * calculate: Performs given calculation based on ArrayLists of numbers and operators.
     *            Displays result of calculations to screen.
     */
    private void calculate() {
        BigDecimal answer;
        answer = inputNumbers.get(0);
        for (int i = 0; i < inputOperators.size(); i++) {
            switch (inputOperators.get(i)) {
                case PLUS:
                    answer = answer.add(inputNumbers.get(i + 1));
                    break;
                case MINUS:
                    answer = answer.subtract(inputNumbers.get(i + 1));
                    break;
                case MULTIPLY:
                    answer = answer.multiply(inputNumbers.get(i + 1));
                    break;
                case DIVIDE:
                    answer = answer.divide(inputNumbers.get(i + 1));
                    break;
                case MODULUS:
                    answer = answer.remainder(inputNumbers.get(i+ 1));
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        answerText.setText(String.valueOf(answer));
        decimalStatus = false;
    }

    /**
     * clear: Clears any calculations and results currently displayed to the screen.
     */
    private void clear() {
        calculatorText.setText("0");
        answerText.setText("");

        inputNumbers.clear();
        inputOperators.clear();

        currentNumberIndex = 0;
        decimalStatus = false;
    }

    /**
     * addLastNumber: Takes the last number from the input TextView and adds it to inputNumbers
     */
    private void addLastNumber() {
        inputNumbers.add(new BigDecimal(calculatorText.getText().subSequence(currentNumberIndex, calculatorText.length()).toString()));
        if (calculatorText.getText().charAt(calculatorText.length()-1) == 46) {
            currentNumberIndex = calculatorText.length();
        } else {
            currentNumberIndex = calculatorText.length() + 1;
        }
    }

    /**
     * invalidSyntax: Displays toast message if the operation is invalid.
     */
    private void invalidSyntax() {
        toast.cancel();
        toast.show();
    }
}
