import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class calc {

    /*An expression is a term followed by an operation followed by another term
    An operation is a plus or minus token
    A term is an integer token or an expression
     */
    static boolean checkInGrammar(String token) {
        Pattern grammar = Pattern.compile("^[0-9]+$|^([0-9]+(-|\\+|\\*|/))+[0-9]+$");

        Matcher matcher = grammar.matcher(token);

        if (matcher.find()) {
            // System.out.println(matcher.group(0));
            return true;
        }
        return false;
    }

    static int onOp(String l_term, String r_term, char op) {
        switch (op) {
            case '*':
//                System.out.println(String.format("%d * %d gives %d", Character.getNumericValue(l_term), Character.getNumericValue(r_term), Character.getNumericValue(l_term) * Character.getNumericValue(r_term)));
                return Integer.valueOf(l_term) * Integer.valueOf(r_term);
            case '/':
                return Integer.valueOf(l_term) / Integer.valueOf(r_term);
            case '+':
                return Integer.valueOf(l_term) + Integer.valueOf(r_term);
            case '-':
                return -Integer.valueOf(r_term);
            default:
                return Integer.valueOf(l_term);
        }
    }

    static void doOps(final LinkedList<String> token, char op_char) {
        int token_ptr = 1;
        while (token_ptr < token.size()) {

            //System.out.println("got" + token.get(token_ptr));
            if (token.get(token_ptr).equals(String.valueOf(op_char))) {
                //System.out.println(String.format("at state %s i %d found a %s", token, token_ptr, op_char));
                int result = onOp(token.get(token_ptr - 1), token.get(token_ptr + 1), op_char);

                if (op_char == '-') {
                    token.set(token_ptr + 1, String.valueOf(result));
                    token.set(token_ptr, "+");
                } else {
                    token.remove(token_ptr + 1);
                    token.remove(token_ptr);
                    token.set(token_ptr - 1, String.valueOf(result));
                }

                //System.out.println(token);

//                System.out.println(String.format("tk length %d vs i pos %d", token.size(), token_ptr));
                if (token.size() <= token_ptr || token.size() < 3) break;
                else token_ptr -= 1;
                continue;
            }

            token_ptr++;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String token = scanner.nextLine().replaceAll(" ", "");

        String[] tokenParts = token.split("(?=(\\+|-|/|\\*))|(?<=(\\+|-|/|\\*))");

        LinkedList<String> termsList = Arrays.stream(tokenParts).collect(Collectors.toCollection(LinkedList::new));

        //System.out.println(termsList);
        if (checkInGrammar(token)) {
            char[] operator_precedence = new char[]{'*', '/', '-', '+'};

            for (char operator : operator_precedence) {
                doOps(termsList, operator);
            }

            System.out.println(termsList);
        } else {
            throw new ArithmeticException("Incorrect Syntax");
        }
    }
}
