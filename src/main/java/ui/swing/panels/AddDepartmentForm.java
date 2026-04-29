package ui.swing.panels;
import javax.swing.*;
import repository.DepartmentRepository;
   public class AddDepartmentForm extends JPanel {
    public AddDepartmentForm(int companyId){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JTextField name = new JTextField();
        JTextField budget = new JTextField();
        JButton addBtn = new JButton("Add");
        add(new JLabel("Name"));
        add(name);
        add(new JLabel("Budget"));
        add(budget);
        add(addBtn);
        addBtn.addActionListener(e -> {
            new DepartmentRepository().addDepartment(
                    companyId,
                    name.getText(),
                    Double.parseDouble(budget.getText())
            );
            JOptionPane.showMessageDialog(this,"Added!");
        });
    }

}