package com.example.calculate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Нахождение всех кнопок по id и установка обработчика нажатий
        setNumberButtonListeners();
        setOperatorButtonListeners();
        setUtilityButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (isOperatorPressed) {
                    currentInput = "";  // Начинаем вводить новое число
                    isOperatorPressed = false;
                }
                currentInput += button.getText().toString();
                display.setText(currentInput);
            }
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.button_add, R.id.button_subtract,
                R.id.button_multiply, R.id.button_divide
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                operator = button.getText().toString();
                firstNumber = Double.parseDouble(currentInput);
                isOperatorPressed = true;
            }
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setUtilityButtonListeners() {
        // Кнопка "="
        findViewById(R.id.button_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double secondNumber = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "×":
                        result = firstNumber * secondNumber;
                        break;
                    case "÷":
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                currentInput = String.valueOf(result);
                display.setText(currentInput);
            }
        });

        // Кнопка "C" (очистка)
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstNumber = 0;
                isOperatorPressed = false;
                display.setText("0");
            }
        });

        // Кнопка "+/-" (смена знака)
        findViewById(R.id.button_negate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    value = -value;
                    currentInput = String.valueOf(value);
                    display.setText(currentInput);
                }
            }
        });

        // Кнопка "%" (проценты)
        findViewById(R.id.button_percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    value = value / 100;
                    currentInput = String.valueOf(value);
                    display.setText(currentInput);
                }
            }
        });

        // Кнопка "," (десятичная точка)
        findViewById(R.id.button_comma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    display.setText(currentInput);
                }
            }
        });
    }
}