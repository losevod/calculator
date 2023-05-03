package com.github.losevod.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Calculator {
    public Calculator() {

        operations = new HashMap<>();
        operations.put('+', 1);
        operations.put('-', 1);
        operations.put('*', 2);
        operations.put('/', 2);
        operations.put('√', 3);

        operands = new ArrayList<>();
        operands.add('0');
        operands.add('1');
        operands.add('2');
        operands.add('3');
        operands.add('4');
        operands.add('5');
        operands.add('6');
        operands.add('7');
        operands.add('8');
        operands.add('9');
        operands.add('.');

    }
    private String textValue;
    public String getTextValue() {
        return textValue;
    }
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
    private final Map<Character, Integer> operations;
    public Map<Character, Integer> getOperations() {
        return operations;
    }
    private final List<Character> operands;
    public List<Character> getOperands() {
        return operands;
    }
    public double doOperation(char operation, double num1, double num2) {
        if ( operation == '/') return num2 / num1;
        if ( operation == '-') return num2 - num1;
        if ( operation == '*') return num1 * num2;
        if ( operation == '+') return num1 + num2;
        return 0.0;
    }
}
