## Math Executor Library by Sh18RW

The Math Executor Library. It can handle simple expressions like 2 + 2 as well as more advanced ones like:
```
14 / (7 / (3 + (1 + (3/log(2, 4))) * 2 / 2 * 2) * (5 ^ 0 + 1))
```

Features:
- Parse mathematical expressions into tokens.
- Build expression trees for evaluation.
- Calculate mathematical expression trees.

### How to Use

1. Parser Class
   
   The Parser class tokenizes a given mathematical expression. \
   *Note: The parser does not validate the logical sequence of tokens.*
   
   Example usage:
   ```java
   public class App {
        public static void main(String[] args) {
            Parser parser = new Parser("14 / (7 / (3 + (1 + (3/log(2tg(Pi/4), 4))) * 2 / 2 * 2) * (5 ^ 0 + 1))");
            try {
            parser.parse();
            } catch (ParserUnknownEntityException | ParserIllegalTokenValueException e) {
            // Handle exceptions
            }
   
           List<Token<?>> result = parser.getResult();
           // Process the list of tokens
       }
   }
   ```
2. ExpressionsTree Class

   The ExpressionsTree class creates a structured tree representation of a mathematical expression for evaluation. \
   *Note: It only validates critical errors, not token sequence correctness.*
   
   Example usage:
   ```java
   public class App {
      public static void process(List<Token<?>> tokens) {
         ExpressionsTree expressionsTree = new ExpressionsTree(tokens);
         try {
         expressionsTree.build();
         } catch (ExpressionException e) {
         // Handle exception
         }
   
         Expression expression = expressionsTree.getRoot();
         // Use the expression object
       }
   }
   ```

3. Calculator Class
   
   The Calculator class evaluates the mathematical expression. It can be reused for multiple calculations, avoiding repetitive instantiation.
   
   Example usage:
   ```java
   public class App {
      public static void calculate(Expression expression) {
         double result = 0.0;
         try {
            result = Calculator.getInstance().calculate((SequenceExpression) expression);
         } catch (CalculatorException e) {
         // Handle exception
         }
         
         System.out.println(result);
      }
   }
   ```

4. Numbers Utility Class

   The Numbers class provides utility methods for working with numbers, though direct usage is not typically required.
   
   Example usage:
   ```java
   public class App {
      public static void main(String[] args) {
         System.out.println(Numbers.getPrettierDoubleAsString(1.2345478, 4).equals("1.2345")); // true
         System.out.println(Numbers.countTensNumbers(11234455.01246) == 8); // true
         System.out.println(Numbers.countNumbersAfterPoint(1.100293) == 6); // true
         System.out.println(Numbers.equals(12.0056, 12.0040, 0.01)); // true
         System.out.println(Numbers.lessThan(12.09, 12.0, 0.1, true)); // true
      }
   }
   ```

### Full Example

Below is a complete example demonstrating the parsing, building, and evaluating of a mathematical expression:
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

      // Step 1: Parse the expression
      Parser parser = new Parser(expression);
      parser.parse();

      // Step 2: Build the expression tree
      ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
      expressionsTree.build();

      // Step 3: Calculate the result
      double result = Calculator.getInstance()
              .calculate((SequenceExpression) expressionsTree.getRoot());

      System.out.printf("%s = %f%n", expression, result);
   }
}
```

### Adding New Function and Constants
You can expand the library by adding new mathematical functions. Here’s how:
1.	Add the function name to `FunctionExpression#supportedFunctions`.
2.	Implement the function logic in `FunctionCalculator`.
3.	Don’t forget to register your new calculation function in the `processors`.

The same process applies if you want to add new constants.