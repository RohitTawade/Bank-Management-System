package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class BalanceEnquiry  extends JFrame implements ActionListener {
    String pin;
    JLabel label2;
    JButton b1;
    BalanceEnquiry(String pin) {
        this.pin=pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.png"));
        JLabel l3 = new JLabel(scaleImage(i1));
        // Use layout manager to position the image label
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(l3, BorderLayout.CENTER);
        setContentPane(contentPane);
        // Set the JFrame size to match the image's scaled size plus the requested increments
        int width = l3.getIcon().getIconWidth() + 100; // Increase width by 20 pixels
        int height = l3.getIcon().getIconHeight() + 30; // Increase height by 10 pixels
        setSize(width, height);
        // Set a preferred size for the label to allow increasing the frame size
        l3.setPreferredSize(new Dimension(l3.getIcon().getIconWidth(), l3.getIcon().getIconHeight()));

        JLabel label1 = new JLabel("Your Current Balance is Rs ");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System",Font.BOLD,14));
        label1.setBounds(360,135,500,35);
        l3.add(label1);

        label2 = new JLabel("");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System",Font.BOLD,14));
        label2.setBounds(360,170,400,35);
        l3.add(label2);

        b1=new JButton("Back");
        b1.setBounds(576,315,100,25);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        int balance = 0;
        try {
            Con c =new Con();
            ResultSet resultSet= c.statement.executeQuery("select * from bank where pin= '"+pin+"'");

            while ((resultSet.next())){
                if(resultSet.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(resultSet.getString("amount"));
                } else {
                    balance -= Integer.parseInt(resultSet.getString("amount"));
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        label2.setText(""+balance);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private ImageIcon scaleImage(ImageIcon imageIcon) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.9);
        int maxHeight = (int) (screenSize.getHeight() * 0.9);

        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();

        if (width > maxWidth || height > maxHeight) {
            float scaleFactor = Math.min((float) maxWidth / width, (float) maxHeight / height);
            width = (int) (width * scaleFactor);
            height = (int) (height * scaleFactor);
            Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }

        return imageIcon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Main_Class(pin);

    }

    public static void main(String[] args) {
        new BalanceEnquiry("");
    }
}
