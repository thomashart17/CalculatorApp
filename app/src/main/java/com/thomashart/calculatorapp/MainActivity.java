package com.thomashart.calculatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

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

    /**
     * onCreate: Initializes default application state when activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up TextViews for calculator display
        calculatorText = (TextView) findViewById(R.id.calculator_text);
        calculatorText.setText("0");
        answerText = (TextView) findViewById(R.id.answer_text);

        // Setting up ArrayLists to store input
        inputNumbers = new ArrayList<>();
        inputOperators = new ArrayList<>();

        initOperatorHashMap();
    }

    private void initOperatorHashMap() {
        operatorHashMap.put(getString(R.string.plus), Operator.PLUS);
        operatorHashMap.put(getString(R.string.minus), Operator.MINUS);
        operatorHashMap.put(getString(R.string.multiply), Operator.MULTIPLY);
        operatorHashMap.put(getString(R.string.divide), Operator.DIVIDE);
        operatorHashMap.put(getString(R.string.modulus), Operator.MODULUS);
    }

    /**
     * onNumberClick: Handles input from number buttons.
     * @param view
     */
    @Override
    public void onNumberClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        Button pressedButton = findViewById(view.getId());
        updateText(pressedButton.getText().toString());
    }

    /**
     * onOperatorClick: Handles input for operator buttons.
     * @param view
     */
    @Override
    public void onOperatorClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        switch (view.getId()) {
            case R.id.clear_button:
                clear();
                break;
            case R.id.equals_button:
                addLastNumber();
                calculate();
                break;
            case R.id.plus_minus_button:
            case R.id.decimal_button:
                break;
            default:
                TextView operatorText = findViewById(view.getId());
                inputOperators.add(operatorHashMap.get(operatorText.getText().toString()));
                addLastNumber();
                updateText(operatorText.getText().toString());
                break;
        }
    }

    /**
     * updateText: Updates text displayed on calculator screen.
     * @param update String to be added to display
     */
    private void updateText(String update) {
        if (answerText.length() > 0) {
            clear();
            calculatorText.setText(update);
        }
        if (calculatorText.getText().equals("0")) {
            calculatorText.setText(update);
        } else {
            calculatorText.setText(calculatorText.getText() + update);
        }
    }

    /**
     * calculate: Performs given calculation based on ArrayLists of numbers and operators.
     *            Displays result of calculations to screen.
     */
    private void calculate() {
        BigDecimal answer;
        switch (inputOperators.get(0)) {
            case PLUS:
                answer = inputNumbers.get(0).add(inputNumbers.get(1));
                break;
            case MINUS:
                answer = inputNumbers.get(0).subtract(inputNumbers.get(1));
                break;
            case MULTIPLY:
                answer = inputNumbers.get(0).multiply(inputNumbers.get(1));
                break;
            case DIVIDE:
                answer = inputNumbers.get(0).divide(inputNumbers.get(1));
                break;
            case MODULUS:
                answer = inputNumbers.get(0).remainder(inputNumbers.get(1));
                break;
            default:
                throw new RuntimeException();
        }
        answerText.setText(String.valueOf(answer));
    }

    /**
     * clear: Clears any calculations and results currently displayed to the screen.
     */
    private void clear() {
        calculatorText.setText("0");
        answerText.setText("");

        inputNumbers.clear();
        inputOperators.clear();
    }

    /**
     * addLastNumber: Takes the last number from the input TextView and adds it to inputNumbers
     */
    private void addLastNumber() {
        inputNumbers.add(new BigDecimal(calculatorText.getText().subSequence(calculatorText.length()-1, calculatorText.length()).toString()));
    }
}