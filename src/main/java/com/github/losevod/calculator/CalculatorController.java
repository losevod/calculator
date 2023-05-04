package com.github.losevod.calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes("calculator")
public class CalculatorController {
    private final Calculator calculator;

    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    private List<String> parseTextValue(String str) {
        List<String> result = new ArrayList<>();
        if (str.equals("")) return result;
        String token = "";
        int i = 0;
        if (str.contains("sqrt_")) {
            i = 5;
            result.add("sqrt");
        }
        if (calculator.getOperations().containsKey(str.charAt(0))) {
            token = str.charAt(0) == '-' ? "-" : "";
            i++;
        }
        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (calculator.getOperations().containsKey(c)) {
                if (result.size() > 0) {
                    String prev = result.get(result.size() - 1);
                    boolean isPreviousTokenIsOperation = calculator.getOperations().containsKey(prev.charAt(0)) && prev.length() == 1;
                    if (isPreviousTokenIsOperation || prev.equals("(")) {
                        if (c == '-') {
                            token = "-";
                            continue;
                        }
                    }
                }
                token = String.valueOf(c);
                result.add(token);
                token = "";
            }
            if (calculator.getOperands().contains(c)) {
                token = token.concat(String.valueOf(c));
                if (i < str.length() - 1) {
                    int j = i + 1;
                    while (true) {
                        if (str.charAt(j) != ' ') {
                            if (str.charAt(j) == '(' || str.charAt(j) == ')' || calculator.getOperations().containsKey(str.charAt(j))) {
                                result.add(token);
                                token = "";
                            }
                            break;
                        } else j++;
                    }
                }
                continue;
            }
            if (c == '(' || c == ')') {
                token = String.valueOf(c);
                result.add(token);
                token = "";
            }
        }
        if (!token.equals("")) result.add(token);
        return result;
    }

    private BigDecimal doMath(List<String> list) {
        if (list.size() < 2) return BigDecimal.valueOf(0.0);
        Stack<String> operands = new Stack<>();
        Stack<Character> operations = new Stack<>();
        int i = 0;
        if (list.get(0).equals("sqrt")) i = 1;
        while (i < list.size()) {
            String token = list.get(i);
            if (token.length() > 1 || calculator.getOperands().contains(token.charAt(0))) {
                operands.add(token);
                i++;
                continue;
            }
            if (calculator.getOperations().containsKey(token.charAt(0))) {
                if (operations.empty()) {
                    operations.add(token.charAt(0));
                    i++;
                    continue;
                }
                if (operations.peek() == '(' || operations.peek() == ')' || calculator.getOperations().get(operations.peek()) < calculator.getOperations().get(token.charAt(0))) {
                    operations.add(token.charAt(0));
                    i++;
                    continue;
                }
                if (calculator.getOperations().get(operations.peek()) >= calculator.getOperations().get(token.charAt(0))) {
                    double localResult;
                    try {
                        double numTop = Double.parseDouble(operands.pop());
                        double numBot = Double.parseDouble(operands.pop());
                        char operation = operations.pop();
                        localResult = calculator.doOperation(operation, numTop, numBot);
                        operands.add(String.valueOf(localResult));
                    } catch (EmptyStackException ex) {
                        ex.printStackTrace();
                    }
                    continue;
                }
            }
            if (token.equals("(")) {
                operations.add(token.charAt(0));
                i++;
                continue;
            }
            if (token.equals(")")) {
                char localToken = 0;
                double localResultInsideBrackets = 0.0;
                while (true) {
                    localToken = operations.pop();
                    if (localToken == '(') break;
                    double numTop = Double.parseDouble(operands.pop());
                    double numBot = Double.parseDouble(operands.pop());
                    localResultInsideBrackets = calculator.doOperation(localToken, numTop, numBot);
                }
                operands.add(String.valueOf(localResultInsideBrackets));
                i++;
            }
        }
        while (!operations.empty()) {
            double localResult;
            try {
                double numTop = Double.parseDouble(operands.pop());
                double numBot = Double.parseDouble(operands.pop());
                char operation = operations.pop();
                localResult = calculator.doOperation(operation, numTop, numBot);
                operands.add(String.valueOf(localResult));
            } catch (EmptyStackException ex) {
                ex.printStackTrace();
            }
        }
        double result = Double.parseDouble(operands.pop());
        if (list.get(0).equals("sqrt")) return BigDecimal.valueOf(Math.sqrt(result));
        return BigDecimal.valueOf(result);
    }

    public void calculate(String str) {
        List<String> list = parseTextValue(str);
        BigDecimal result = doMath(list);
        calculator.setTextValue(String.valueOf(result));
    }

    @GetMapping
    public String getCalculator(Model model) {
        model.addAttribute("calculator", calculator);
        return "calculator";
    }

    @PostMapping
    public String processCalculator(@ModelAttribute Calculator calculator) {
        if (calculator == null) {
            return "calculator";
        }


        calculate(calculator.getTextValue());

        return "calculator";
    }

}
