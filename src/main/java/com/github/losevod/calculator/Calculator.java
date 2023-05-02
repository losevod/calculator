package com.github.losevod.calculator;

import org.springframework.stereotype.Component;

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
    }
    private String textValue;
    public String getTextValue() {
        return textValue;
    }
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    private double result;
    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }
    private final Map<Character, Integer> operations;
    public Map<Character, Integer> getOperations() {
        return operations;
    }
    private final List<Character> operands;
    public List<Character> getOperands() {
        return operands;
    }
}
