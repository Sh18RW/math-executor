## Math Executor lib by Sh18RW
Here is a library to run as simple math expression like as `2 + 2` as `14 / (7 / (3 + (1 + (3/log(2, 4))) * 2 / 2 * 2) * (5 ^ 0 + 1))`.
### How to use
Library contains:
1. Parser class.
    ```java
    public class App {
        public static void main(String[] args) {
            Parser parser = new Parser("14 / (7 / (3 + (1 + (3/log(2, 4))) * 2 / 2 * 2) * (5 ^ 0 + 1))");
            try {
                parser.parse();
            } catch (ParserUnknownEntityException | ParserIllegalTokenValueException e) {
                // process exceptions
            }

            List<Token<?>> result = parser.getResult();
            // do something
        }
    }
    ```
    It parses an input expression to List of tokens.
    **It doesn't check for valid sequence.**
2. ExpressionTree class.
    ```java
    public class App {
         public static void process(List<Token<?>> tokens) {
             ExpressionsTree expressionsTree = new ExpressionsTree(tokens);
             try {
                 expressionsTree.build();
             } catch (ExpressionException e) {
                 // process exception
             }

             Expression expression = expressionsTree.getRoot();
             // do something
         }
     }
    ```
   **Doesn't check for valid sequence at all. Only critical misspells.**
3. Calculator class.
    ```java
   public class App {
        public static void calculate(Expression expression) {
           double result = 0;
           try {
               result = Calculator.getInstance().calculate((SequenceExpression) expression);
           } catch (CalculatorException e) {
               // process exception
           }
   
           System.out.println(result);
        }
   }
    ```
   You needn't instantiate Calculator every use, you can make it once.
4. Number class, but I don't recommend you to use it.
    ```java
    public class App {
        public static void main(String[] args) {
            System.out.println(
                    Numbers.getPrettierDoubleAsString(1.2345478, 4)
                            .equals("1.2345")); // true
            System.out.println(Numbers.countTensNumbers(11234455.01246) == 8); // true
            System.out.println(Numbers.countNumbersAfterPoint(1.100293) == 6); // true
            System.out.println(Numbers.equals(12.0056, 12.0040, 0.01)); // true
            System.out.println(Numbers.lessThan(12.09, 12.0, 0.1, true)); // true
        }
    }
    ```
### Example
```java
package ru.corvinella;

import ru.corvinella.expressions.ExpressionsTree;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.expressions.exceptions.ExpressionException;
import ru.corvinella.math.Calculator;
import ru.corvinella.math.exceptions.CalculatorException;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.exceptions.ParserIllegalTokenValueException;
import ru.corvinella.parser.exceptions.ParserUnknownEntityException;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws ParserIllegalTokenValueException, ParserUnknownEntityException, ExpressionException, CalculatorException {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Please enter an expression: ");

       String expression = scanner.nextLine();

       Parser parser = new Parser(expression);
       parser.parse();

       ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
       expressionsTree.build();

       double result = Calculator.getInstance()
               .calculate((SequenceExpression) expressionsTree.getRoot());

       System.out.printf("%s = %f%n", expression, result);
    }
}
```