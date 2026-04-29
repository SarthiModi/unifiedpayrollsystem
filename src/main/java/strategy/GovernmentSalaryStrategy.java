package strategy;
import model.Employee;
public class GovernmentSalaryStrategy implements SalaryStrategy {
    @Override
    public double calculate(Employee e){

        double base = e.getSalary();
        double hra = base * e.getHraPercent() / 100;
        double da  = base * e.getDaPercent() / 100;
        double ta  = base * e.getTaPercent() / 100;
        double gross = base + hra + da + ta;
        double increment = gross * e.getIncrementPercent() / 100;
        gross += increment;
        double tax = gross * 0.10;
        double deduction = 0;
        if(e.getLeaves() > 4){
            deduction = (gross / 30) * (e.getLeaves() - 4);
        }
        return gross - tax - deduction;
    }
}