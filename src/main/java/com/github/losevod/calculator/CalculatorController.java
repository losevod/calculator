package com.github.losevod.calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes("calculator")
public class CalculatorController {
    private Calculator calculator;

    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    private List<String> parseTextValue (String str) {
        List<String> result = new ArrayList<>();
        if (str.equals("")) return result;
        String token = "";
        int i = 0;
        if (calculator.getOperations().containsKey(str.charAt(0))) {
            token = str.charAt(0) == '-' ? "-" : "";
            i++;
        }
        for (;i < str.length(); i++) {
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
            } else {
                if (calculator.getOperands().contains(c)) {
                    token += c;
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
                    continue;
                }
                if (c == ' ') continue;
                // return new ArrayList<>();
            }
        }
        if (!token.equals("")) result.add(token);
        return result;
    }

    private double doMath(List<String> list) {
        if (list.size() < 3) return 0.0;
        Stack<String> operands = new Stack<>();
        Stack<Character> operations = new Stack<>();
        double result = 0.0;
//        int i = 0;
//        while(i != list.size()) {
//            String token = list.get(i);
//            if (token.length() > 1 || calculator.getOperands().contains(token.charAt(0))) {
//                operands.add(token);
//                i++;
//            }
//            if (calculator.getOperations().containsKey(token.charAt(0))) {
//                if (operations.empty()) {
//                    operations.add(token.charAt(0));
//                    i++;
//                }
//                if (calculator.getOperations().get(operations.peek()) < calculator.getOperations().get(token.charAt(0)) || calculator.getOperations().get(operations.peek()) == '(' || calculator.getOperations().get(operations.peek()) == ')') {
//                    operations.add(token.charAt(0));
//                    i++;
//                }
//                if (calculator.getOperations().get(operations.peek()) >= calculator.getOperations().get(token.charAt(0))) {
//                    double localResult = 0.0;
//                    if (operations.peek() == '√') {
//                        double num = Double.parseDouble(operands.pop());
//                        localResult = Math.sqrt(num);
//                    } else {
//                        double numTop = Double.parseDouble(operands.pop());
//                        double numBot = Double.parseDouble(operands.pop());
//                        char operation = operations.pop();
//                        if ( operation == '/') localResult = numBot / numTop;
//                        if ( operation == '-') localResult = numBot - numTop;
//                        if ( operation == '*') localResult = numTop * numBot;
//                        if ( operation == '+') localResult = numBot + numBot;
//                    }
//                    operands.add(String.valueOf(localResult));
//                }
//                if (token.equals("(")) {
//                    operations.add(token.charAt(0));
//                    i++;
//                }
//                if (token.equals(")")) {
//                    String localToken = "";
//                    while(!localToken.equals("(")) {
//                        //localToken = operations.pop()
//                    }
//                }
//            }
//        }
        return result;
    }

    public void calculate(String str) {
        List<String> list = parseTextValue(str);
        log.info(list.toString());
        double result = doMath(list);
        calculator.setResult(result);
        calculator.setTextValue(calculator.getResult() + "");
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
