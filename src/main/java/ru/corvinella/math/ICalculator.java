package ru.corvinella.math;

import ru.corvinella.math.exceptions.CalculatorException;

public interface ICalculator <T> {
    Double calculate(T expression) throws CalculatorException;
}
