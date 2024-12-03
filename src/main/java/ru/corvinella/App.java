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
    /**
     * Main method for testing.
     */
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
