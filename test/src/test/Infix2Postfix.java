package test;

import java.util.*;

public class Infix2Postfix {
    public static String convert(String exp) {
        if (exp == null || exp.length() == 0) return null;
        StringTokenizer st = new StringTokenizer(exp, "+-*/()%()", true);
        Stack<String> stack = new Stack<>();
        StringBuffer buf = new StringBuffer();
        boolean isPrevOperator = true; // Flag to track if the previous token was an operator

        while (st.hasMoreTokens()) {
            String tok = st.nextToken().trim();

            if (isOperator(tok)) {
                if (isPrevOperator && (tok.equals("-") || tok.equals("+"))) {
                    buf.append("0 "); 
                }

                // Process operators based on priority
                while (!stack.empty()) {
                    String op2 = stack.peek();
                    if (!op2.equals("(") && hasHigherPrecedence(op2, tok)) {
                        buf.append(stack.pop()).append(" ");
                    } else break;
                }
                stack.push(tok);
                isPrevOperator = true;
            } else if (tok.equals("(")) {
                stack.push(tok);
                isPrevOperator = true;
            } else if (tok.equals(")")) {
                while (!stack.peek().equals("(")) {
                    buf.append(stack.pop()).append(" ");
                }
                stack.pop(); // Discard the "("
                isPrevOperator = false;
            } else {
                // Token is a number
                buf.append(tok).append(" ");
                isPrevOperator = false;
            }
        }

        // Pop remaining operators from stack
        while (!stack.empty()) {
            buf.append(stack.pop()).append(" ");
        }

        return buf.toString().trim();
    }

    public static boolean isOperator(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%");
    }

    private static boolean hasHigherPrecedence(String op1, String op2) {
        int p1 = getPriority(op1.charAt(0));
        int p2 = getPriority(op2.charAt(0));
        return p1 >= p2;
    }

    private static int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        String exp = "-3 + 2";
        System.out.printf("%s => %s %n", exp, Infix2Postfix.convert(exp));
    }
}
