package ui.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    CardLayout layout = new CardLayout();
    JPanel container = new JPanel(layout);

    public MainFrame() {
        setTitle("Payroll System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.add(new LoginPanel(this), "login");
        container.add(new CreateCompanyPanel(this), "company");
        container.add(new AdminRegisterPanel(this), "register");

        add(container);
        layout.show(container, "login");

        setVisible(true);
    }

    public void showPage(String name) {
        layout.show(container, name);
    }

    public void openAdmin(int companyId) {
        container.add(new AdminDashboardUI(this, companyId), "admin");
        showPage("admin");
    }

    public void openEmployee(String email) {
        container.add(new EmployeeDashboardUI(this, email), "emp");
        showPage("emp");
    }
}