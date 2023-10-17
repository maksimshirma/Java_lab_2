package org.lab;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * Calculator. This class provides calculating of expressions.
 */
public class Calculator {
    private String expression;

    private final ArrayList<String> operands;

    private int lastIndex = -1;

    Calculator() {
        this.operands = new ArrayList<>();
    }

    public boolean enterExpression(String _expression) {
        String currentExpression = _expression.replaceAll(" ", "");
        boolean isValid = checkIsValid(currentExpression);
        if (isValid) {
            this.expression = currentExpression;
            return true;
        }
        return false;
    }

    public String calculate() {
        convertExpressionToPostfix();
        calculatePostfixExpression();
        return this.expression;
    }

    private void calculatePostfixExpression() {
        Stack<Double> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        final int length = expression.length();
        for (int index = 0; index < length; index++) {
            char currentCharacter = expression.charAt(index);
            if (currentCharacter >= '0' && currentCharacter <= '9') {
                final String value = this.operands.get(Character.getNumericValue(currentCharacter));
                stack.push(Double.parseDouble(value));
            } else {
                final Double secondOperand = stack.peek();
                stack.pop();
                final Double firstOperand = stack.peek();
                stack.pop();
                switch (currentCharacter) {
                    case '+': {
                        final Double temp = firstOperand + secondOperand;
                        stack.push(temp);
                        break;
                    }
                    case '-': {
                        final Double temp = firstOperand - secondOperand;
                        stack.push(temp);
                        break;
                    }
                    case '*': {
                        final Double temp = firstOperand * secondOperand;
                        stack.push(temp);
                        break;
                    }
                    case '/': {
                        final Double temp = firstOperand / secondOperand;
                        stack.push(temp);
                        break;
                    }
                }
            }
        }
        this.expression = stack.peek().toString();
        this.operands.clear();
        this.lastIndex = -1;

    }

    private int getPriority(char operator) {
        return switch (operator) {
            case '*', '/' -> 1;
            case '+', '-' -> 0;
            default -> -1;
        };
    }

    private void clearStackBeforeOpenBracket(Stack<Character> stack, StringBuilder postfixExpression, char bracket) {
        char currentBracket = '0';
        switch (bracket) {
            case ')': {
                currentBracket = '(';
                break;
            }
            case '}': {
                currentBracket = '{';
                break;
            }
            case ']': {
                currentBracket = '[';
                break;
            }
        }
        char prevCharacter = stack.peek();
        stack.pop();
        while (!Objects.equals(prevCharacter, currentBracket)) {
            postfixExpression.append(prevCharacter);
            prevCharacter = stack.peek();
            stack.pop();
        };
    }

    private void convertExpressionToPostfix() {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfixExpression = new StringBuilder();
        StringBuilder operand = new StringBuilder();
        final int length = expression.length();
        for (int index = 0; index < length; index++) {
            char currentCharacter = expression.charAt(index);
            switch (currentCharacter) {
                case '(': case '{': case '[': {
                    stack.push(currentCharacter);
                    break;
                }
                case '+': case '-': case '*': case '/': {
                    if (!operand.isEmpty()) {
                        this.operands.add(operand.toString());
                        postfixExpression.append(++this.lastIndex);
                        operand = new StringBuilder();
                    }
                    boolean isPushed = false;
                    do {
                        if (stack.isEmpty()) {
                            stack.push(currentCharacter);
                            isPushed = true;
                        } else {
                            char prevCharacter = stack.peek();
                            if (Objects.equals(prevCharacter, '(') || Objects.equals(prevCharacter, '{') || Objects.equals(prevCharacter, '[') || getPriority(prevCharacter) < getPriority(currentCharacter)) {
                                stack.push(currentCharacter);
                                isPushed = true;
                            } else if (getPriority(prevCharacter) >= getPriority(currentCharacter)) {
                                postfixExpression.append(prevCharacter);
                                stack.pop();
                            }
                        }
                    } while (!isPushed);
                    break;
                }
                case ')': case '}': case ']': {
                    this.operands.add(operand.toString());
                    postfixExpression.append(++this.lastIndex);
                    operand = new StringBuilder();
                    clearStackBeforeOpenBracket(stack, postfixExpression, currentCharacter);
                    break;
                }
                default: {
                    if (currentCharacter >= '0' && currentCharacter <= '9') {
                        operand.append(currentCharacter);
                    }
                }
            }
        }
        this.operands.add(operand.toString());
        postfixExpression.append(++this.lastIndex);
        while (!stack.isEmpty()) {
            char currentCharacter = stack.peek();
            postfixExpression.append(currentCharacter);
            stack.pop();
        }
        this.expression = postfixExpression.toString();
    }

    private boolean checkCurrentBrackets(Stack<Character> stack, char bracket) {
        if (!stack.isEmpty()) {
            char prevBracket = stack.peek();
            char currentBracket = '0';
            switch (bracket) {
                case ')': {
                    currentBracket = '(';
                    break;
                }
                case '}': {
                    currentBracket = '{';
                    break;
                }
                case ']': {
                    currentBracket = '[';
                    break;
                }
            }
            if (Objects.equals(prevBracket, currentBracket)) {
                stack.pop();
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean checkIsValid(String _expression) {
        if (_expression.isEmpty()) return false;
        Stack<Character> stack = new Stack<>();
        final int length = _expression.length();
        boolean isOperator = false;
        for (int index = 0; index < length; index++) {
            char currentCharacter = _expression.charAt(index);
            switch (currentCharacter) {
                case '+': case '-': case '*': case '/': {
                    if (isOperator || index == 0 || index == length - 1) {
                        return false;
                    }
                    isOperator = true;
                    break;
                }
                case '(': case '{': case '[': {
                    isOperator = false;
                    stack.push(currentCharacter);
                    break;
                }
                case ')': case '}': case ']': {
                    if (isOperator || !checkCurrentBrackets(stack, currentCharacter)) {
                        return false;
                    }
                    break;
                }
                default: {
                    isOperator = false;
                    if (currentCharacter >= '0' && currentCharacter <= '9') {
                        if (index != 0 && Objects.equals(_expression.charAt(index - 1), ')')) {
                            return false;
                        }
                        if (index != length-1 && Objects.equals(_expression.charAt(index + 1), '(')) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }
}
