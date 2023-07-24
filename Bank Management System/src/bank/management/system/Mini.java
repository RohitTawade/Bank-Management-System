package bank.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Mini extends JFrame implements ActionListener {

    String pin;

    JButton button;
    JTextArea textArea; // Add a JTextArea to display the transaction records
    Mini(String pin) {
        this.pin = pin;
        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(new Color(255, 204, 204));
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20, 140, 400, 200);
        add(label1);

        JLabel label2 = new JLabel("MGB Bank");
        label2.setFont(new Font("system", Font.BOLD, 15));
        label2.setBounds(150, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 300, 20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20, 400, 300, 20);
        add(label4);

        try {
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM login WHERE pin='" + pin + "'");
            while (resultSet.next()) {
                label3.setText("Card Number: " + resultSet.getString("card_number").substring(0, 4) + "XXXXXXXX" + resultSet.getString("card_number").substring(12));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 255, 204));
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 140, 350, 250);
        add(scrollPane);

        try {
            int balance = 0;
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin='" + pin + "'");
            StringBuilder statementText = new StringBuilder(); // Use a StringBuilder to accumulate text

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String type = resultSet.getString("type");
                int amount = Integer.parseInt(resultSet.getString("amount"));


                statementText.append(date)
                        .append("     ")
                        .append(type)
                        .append("     ")
                        .append(amount)
                        .append("\n");

                if (type.equals("Deposit")) {
                    balance += amount;
                } else if (type.equals("Withdrawal")) {
                    balance -= amount;
                }
            }


            textArea.setText(statementText.toString());
            label4.setText("Your Total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new Mini("");
    }
}
