package com.example.mycalculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    // Declared object for EditText

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fetch the Buttons

        Button but_1 = findViewById(R.id.button_id1);
        Button but_2 = findViewById(R.id.button_id2);
        Button but_3 = findViewById(R.id.button_id3);
        Button but_4 = findViewById(R.id.button_id4);
        Button but_5 = findViewById(R.id.button_id5);
        Button but_6 = findViewById(R.id.button_id6);
        Button but_7 = findViewById(R.id.button_id7);
        Button but_8 = findViewById(R.id.button_id8);
        Button but_9 = findViewById(R.id.button_id9);
        Button but_0 = findViewById(R.id.button_0);
        Button but_add = findViewById(R.id.button_plus);
        Button but_sub = findViewById(R.id.button_minus);
        Button but_mul = findViewById(R.id.button_multiply);
        Button but_div = findViewById(R.id.button_divide);
        Button but_eq = findViewById(R.id.button_equals);
        Button but_clr = findViewById(R.id.button_clear);

        // Initialize the EditText
        editText = findViewById(R.id.edittext_id);

        // Set onClickListeners
        but_0.setOnClickListener(this::onButtonClick);
        but_1.setOnClickListener(this::onButtonClick);
        but_2.setOnClickListener(this::onButtonClick);
        but_3.setOnClickListener(this::onButtonClick);
        but_4.setOnClickListener(this::onButtonClick);
        but_5.setOnClickListener(this::onButtonClick);
        but_6.setOnClickListener(this::onButtonClick);
        but_7.setOnClickListener(this::onButtonClick);
        but_8.setOnClickListener(this::onButtonClick);
        but_9.setOnClickListener(this::onButtonClick);
        but_add.setOnClickListener(this::onButtonClick);
        but_sub.setOnClickListener(this::onButtonClick);
        but_mul.setOnClickListener(this::onButtonClick);
        but_div.setOnClickListener(this::onButtonClick);
        but_eq.setOnClickListener(this::onButtonClick);
        but_clr.setOnClickListener(this::onButtonClick);
    }

    // Method to handle button clicks
    public void onButtonClick(View view) {
        Button button = (Button) view; // Cast the clicked view to Button
        String buttonText = button.getText().toString(); // Get the text from the button

        switch (buttonText) {
            case "=":
                calculateResult();
                break;
            case "C":
                editText.setText("");
                break;
            default:
                editText.append(buttonText);
                break;
        }
    }

    // Method to calculate the result
    private void calculateResult() {
        String expression = editText.getText().toString();

        try {
            double result = evaluateExpression(expression);
            editText.setText(String.valueOf(result));
        } catch (Exception e) {
            editText.setText(R.string.error);
            Toast.makeText(this, "Error in expression", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to evaluate the expression
    private double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                numbers.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    applyOperator(operators, numbers);
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    applyOperator(operators, numbers);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(operators, numbers);
        }

        return numbers.pop();
    }

    // Method to apply an operator to the top two numbers in the stack
    private void applyOperator(Stack<Character> operators, Stack<Double> numbers) {
        char operator = operators.pop();
        double b = numbers.pop();
        double a = numbers.pop();

        switch (operator) {
            case '+':
                numbers.push(a + b);
                break;
            case '-':
                numbers.push(a - b);
                break;
            case '*':
                numbers.push(a * b);
                break;
            case '/':
                if (b != 0) {
                    numbers.push(a / b);
                } else {
                    throw new ArithmeticException("Division by zero");
                }
                break;
        }
    }

    // Method to get the precedence of operators
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}
