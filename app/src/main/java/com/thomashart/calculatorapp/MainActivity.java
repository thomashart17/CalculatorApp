package com.thomashart.calculatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * MainActivity class for the calculator app.
 * @author Thomas Hart
 */
public class MainActivity extends Activity implements OnCalculatorClickListener {

    // TextViews
    TextView calculatorText;
    TextView answerText;

    ArrayList<BigDecimal> inputNums;

    /**
     * onCreate: Initializes default application state when activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up TextViews for calculator
        calculatorText = (TextView) findViewById(R.id.calculator_text);
        calculatorText.setText("0");
        answerText = (TextView) findViewById(R.id.answer_text);

        inputNums = new ArrayList<>();
    }

    /**
     * onNumberClick: Handles input from number buttons.
     * @param view
     */
    @Override
    public void onNumberClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        switch (view.getId()) {
            case R.id.zero_button:
                updateText("0");
                break;
            case R.id.one_button:
                updateText("1");
                break;
            case R.id.two_button:
                updateText("2");
                break;
            case R.id.three_button:
                updateText("3");
                break;
            case R.id.four_button:
                updateText("4");
                break;
            case R.id.five_button:
                updateText("5");
                break;
            case R.id.six_button:
                updateText("6");
                break;
            case R.id.seven_button:
                updateText("7");
                break;
            case R.id.eight_button:
                updateText("8");
                break;
            case R.id.nine_button:
                updateText("9");
                break;
        }
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
                calculate();
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
        answerText.setText(calculatorText.getText());
    }

    /**
     * clear: Clears any calculations and results currently displayed to the screen.
     */
    private void clear() {
        calculatorText.setText("0");
        answerText.setText("");
    }
}