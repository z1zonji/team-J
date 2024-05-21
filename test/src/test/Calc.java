package test;

import java.util.*;

public class Calc {
    public static double eval(String exp) {
        StringTokenizer st = new StringTokenizer(exp);
        Stack<Double> stack = new Stack<>();

        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (Infix2Postfix.isOperator(tok)) {
                double v1 = stack.pop();
                double v2 = stack.pop();
                double value = 0;
                switch (tok) {
                    case "+":
                        value = v2 + v1;
                        break;
                    case "-":
                        value = v2 - v1;
                        break;
                    case "*":
                        value = v2 * v1;
                        break;
                    case "/":
                        value = v2 / v1;
                        break;
                    case "%":
                        value = v2 % v1;
                        break;
                }
                stack.push(value);
            } else {
                stack.push(Double.parseDouble(tok));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String infix = sc.nextLine();
        String postfix = Infix2Postfix.convert(infix);
        double value = Calc.eval(postfix);
        System.out.printf("%s = %.2f\n", infix, value);
    }
}
