import java.util.Scanner;

public class InfixToPostfix {
    static char[] stack;
    static int top = -1;

    static void push(char c) {
        stack[++top] = c;
    }

    static char pop() {
        return stack[top--];
    }

    static char peek() {
        return stack[top];
    }

    static boolean isEmpty() {
        return top == -1;
    }

    static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    static String convert(String infix) {
        stack = new char[infix.length()];
        top = -1;
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (c == ' ') {
                continue;
            }
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);                       // operand goes straight to output
            } else if (c == '(') {
                push(c);
            } else if (c == ')') {
                while (!isEmpty() && peek() != '(') {
                    postfix.append(pop());
                }
                if (isEmpty()) {
                    return "Invalid expression: unbalanced parentheses";
                }
                pop();                                   // remove '('
            } else if (precedence(c) != -1) {
                // '^' is right-associative, so pop only strictly higher precedence for it
                while (!isEmpty() && peek() != '('
                        && (precedence(peek()) > precedence(c)
                        || (precedence(peek()) == precedence(c) && c != '^'))) {
                    postfix.append(pop());
                }
                push(c);
            } else {
                return "Invalid character: " + c;
            }
        }

        while (!isEmpty()) {
            if (peek() == '(') {
                return "Invalid expression: unbalanced parentheses";
            }
            postfix.append(pop());
        }
        return postfix.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter infix expression : ");
        String infix = sc.nextLine();
        System.out.println("Postfix expression: " + convert(infix));
        sc.close();
    }
}