package com.hussein.zavin.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private final String STATE_PENDING_OPERATION = "PendingOperation Context";
    private static final String STATE_OPERAND1 = "operand1";
    @BindView(R.id.result)
    EditText result;
    @BindView(R.id.newNumber)
    EditText newNumber;
    @BindView(R.id.operation)
    TextView operation;

    @BindView(R.id.button0)
    Button button0;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.buttonDot)
    Button buttonDot;
    @BindView(R.id.buttonEquals)
    Button buttonEquals;
    @BindView(R.id.buttonDivide)
    Button buttonDivide;
    @BindView(R.id.buttonMultiply)
    Button buttonMultiply;
    @BindView(R.id.buttonMinus)
    Button buttonMinus;
    @BindView(R.id.buttonPlus)
    Button buttonPlus;
    @BindView(R.id.buttonNegative)
    Button buttonNegative;
    @BindView(R.id.buttonClear)
    Button buttonClear;
    @BindView(R.id.buttonDel)
    Button buttonDel;


    private Double operand1;

    private String pendingOperation = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);

                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                operation.setText(pendingOperation);

            }
        };

        buttonEquals.setOnClickListener(operationListener);
        buttonDivide.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if (value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        if (value.indexOf(".") < 0) {
                            Integer integerValue = Integer.valueOf(value);
                            integerValue *= -1;
                            newNumber.setText(integerValue.toString());
                        } else {
                            Double doubleValue = Double.valueOf(value);
                            doubleValue *= -1;
                            newNumber.setText(doubleValue.toString());
                        }


                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }

            }

            ;
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                newNumber.setText("");
                operation.setText("");
                operand1 = null;

            }
        });


    }

    @OnClick(R.id.buttonDel)
    void backspace(Button buttonDel) {
        int inputLength = newNumber.length();
        int startIndex = 0;
        if (inputLength >= 1) {
            String modifiedNumber = newNumber.getText().toString().substring(startIndex, inputLength - 1);
            newNumber.setText(modifiedNumber);
        }
    }

    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
        }
        double doubleValue = operand1;
        int intValue;
        if ((operand1 % 1) == 0) {
            intValue = (int) doubleValue;
            result.setText("" + intValue);
        } else {
            result.setText("" + doubleValue);
        }
        newNumber.setText("");


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        operation.setText(pendingOperation);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }
}