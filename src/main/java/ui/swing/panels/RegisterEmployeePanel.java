package ui.swing.panels;

import javax.swing.*;
import repository.CompanyRepository;
import repository.EmployeeRepository;
import utils.ValidationUtil;

public class RegisterEmployeePanel extends JPanel {

    public RegisterEmployeePanel(int companyId){

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField dept = new JTextField();
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();
        JTextField empType = new JTextField();
        JTextField salary = new JTextField();
        JTextField tier = new JTextField();

        JLabel empTypeLabel = new JLabel("Employee Type");
        JLabel tierLabel = new JLabel("Tier");

        JButton add = new JButton("Register");

        String companyType = new CompanyRepository().getCompanyType(companyId);

        add(new JLabel("Dept ID")); add(dept);
        add(new JLabel("Name")); add(name);
        add(new JLabel("Email")); add(email);
        add(new JLabel("Password")); add(pass);

        if(companyType.equalsIgnoreCase("private")){
            add(empTypeLabel);
            add(empType);
        }

        if(companyType.equalsIgnoreCase("government")){
            add(tierLabel);
            add(tier);
        }

        add(new JLabel("Salary")); add(salary);
        add(add);

        add.addActionListener(e -> {

            String nameValue = name.getText().trim();
            String emailValue = email.getText().trim().toLowerCase();
            String passwordValue = new String(pass.getPassword()).trim();
            String deptValue = dept.getText().trim();
            String salaryValue = salary.getText().trim();

            if(ValidationUtil.isEmpty(nameValue) ||
               ValidationUtil.isEmpty(emailValue) ||
               ValidationUtil.isEmpty(passwordValue) ||
               ValidationUtil.isEmpty(deptValue) ||
               ValidationUtil.isEmpty(salaryValue)){
                
                JOptionPane.showMessageDialog(this,"All fields required!");
                return;
            }

            if(!ValidationUtil.isValidEmail(emailValue)){
                JOptionPane.showMessageDialog(this,"Invalid Email!");
                return;
            }

            if(!ValidationUtil.isValidNumber(salaryValue)){
                JOptionPane.showMessageDialog(this,"Invalid Salary!");
                return;
            }

            if(companyType.equalsIgnoreCase("private") && ValidationUtil.isEmpty(empType.getText())){
                JOptionPane.showMessageDialog(this,"Employee Type required!");
                return;
            }

            if(companyType.equalsIgnoreCase("government") && ValidationUtil.isEmpty(tier.getText())){
                JOptionPane.showMessageDialog(this,"Tier required!");
                return;
            }

            try{
                int deptId = Integer.parseInt(deptValue);

                String employeeType = "NA";
                String tierValue = "NA";

                if(companyType.equalsIgnoreCase("private")){
                    employeeType = empType.getText().trim();
                }

                if(companyType.equalsIgnoreCase("government")){
                    tierValue = tier.getText().trim();
                }

                boolean success = new EmployeeRepository().registerEmployee(
                        companyId,
                        deptId,
                        nameValue,
                        emailValue,
                        passwordValue,
                        companyType,
                        employeeType,
                        Double.parseDouble(salaryValue),
                        tierValue
                );

                if(success){
                    JOptionPane.showMessageDialog(this,"Employee Registered");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Department OR Email exists!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"Department and Salary must be numbers!");
            }
        });
    }
}