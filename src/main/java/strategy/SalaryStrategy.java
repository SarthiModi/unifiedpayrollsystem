package strategy;

import model.Employee;

public interface SalaryStrategy {
    double calculate(Employee e);
}