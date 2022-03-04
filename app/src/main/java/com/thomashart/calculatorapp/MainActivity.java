package com.thomashart.calculatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * MainActivity class for the calculator app.
 * @author Thomas Hart
 */
public class MainActivity extends Activity implements View.OnClickListener {

    // Number Buttons
    Button zeroButton;
    Button oneButton;
    Button twoButton;
    Button threeButton;
    Button fourButton;
    Button fiveButton;
    Button sixButton;
    Button sevenButton;
    Button eightButton;
    Button nineButton;

    // Operator Buttons
    Button clearButton;
    Button equalsButton;
    Button plusButton;
    Button minusButton;
    Button multiplyButton;
    Button divideButton;
    Button plusMinusButton;
    Button modulusButton;
    Button decimalButton;

    // TextViews
    TextView calculatorText;
    TextView answerText;

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

        initButtons();
    }

    /**
     * initButtons: Initializes buttons used on calculator.
     */
    private void initButtons() {
        zeroButton = findViewById(R.id.zero_button);
        oneButton = findViewById(R.id.one_button);
        twoButton = findViewById(R.id.two_button);
        threeButton = findViewById(R.id.three_button);
        fourButton = findViewById(R.id.four_button);
        fiveButton = findViewById(R.id.five_button);
        sixButton = findViewById(R.id.six_button);
        sevenButton = findViewById(R.id.seven_button);
        eightButton = findViewById(R.id.eight_button);
        nineButton = findViewById(R.id.nine_button);

        clearButton = findViewById(R.id.clear_button);
        equalsButton = findViewById(R.id.equals_button);
        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.minus_button);
        multiplyButton = findViewById(R.id.multiply_button);
        divideButton = findViewById(R.id.divide_button);
        plusMinusButton = findViewById(R.id.plus_minus_button);
        modulusButton = findViewById(R.id.modulus_button);
        decimalButton = findViewById(R.id.decimal_button);

        zeroButton.setOnClickListener(this);
        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        sevenButton.setOnClickListener(this);
        eightButton.setOnClickListener(this);
        nineButton.setOnClickListener(this);

        clearButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        plusMinusButton.setOnClickListener(this);
        modulusButton.setOnClickListener(this);
        decimalButton.setOnClickListener(this);
    }

    /**
     * onClick: Determines correct action to be taken based off of button press.
     * @param view
     */
    @Override
    public void onClick(View view) {
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

    private void calculate() {
        answerText.setText(calculatorText.getText());
    }

    private void clear() {
        calculatorText.setText("0");
        answerText.setText("");
    }
}