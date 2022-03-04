package com.thomashart.calculatorapp;

import android.view.View;

/**
 * Interface of methods to handle button presses based on function of button.
 * @author Thomas Hart
 */
public interface OnCalculatorClickListener {

    void onNumberClick(View view);

    void onOperatorClick(View view);
}
