package ui.swing.panels;

import java.awt.*;
import javax.swing.*;
import model.Employee;
import repository.EmployeeRepository;
import service.PayrollService;

public class SearchEmployeePanel extends JPanel {

    public SearchEmployeePanel(int companyId){

        setLayout(new BorderLayout());

        JTextField input = new JTextField(15);
        JButton search = new JButton("Search");

        JTextArea result = new JTextArea();
        result.setEditable(false);

        JPanel top = new JPanel();
        top.add(new JLabel("Enter Email or ID:"));
        top.add(input);
        top.add(search);

        search.addActionListener(e -> {

            EmployeeRepository repo = new EmployeeRepository();
            Employee emp = null;

            try{
                int id = Integer.parseInt(input.getText());
                emp = repo.findById(id,companyId);
            }catch(Exception ex){
                emp = repo.findByEmail(input.getText(),companyId);
            }

            if(emp == null){
                result.setText("Employee not found!");
                return;
            }

            PayrollService p = new PayrollService();
            double net = p.calculateSalary(emp);

            result.setText(
                    "ID: "+emp.getId()+"\n"+
                    "Name: "+emp.getName()+"\n"+
                    "Email: "+emp.getEmail()+"\n"+
                    "Salary: "+emp.getSalary()+"\n"+
                    "HRA: "+emp.getHraPercent()+"%\n"+
                    "DA: "+emp.getDaPercent()+"%\n"+
                    "TA: "+emp.getTaPercent()+"%\n"+
                    "Bonus: "+emp.getBonus()+"\n"+
                    "Increment: "+emp.getIncrementPercent()+"%\n"+
                    "Goals: "+emp.getGoals()+"\n"+
                    "Leaves: "+emp.getLeaves()+"\n"+
                    "Net Salary: "+net
            );
        });

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(result),BorderLayout.CENTER);
    }
}