package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JButton b1,b2,b3,b4,b5,b6,b7;
    String pin;
    FastCash(String pin) {
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


        JLabel label = new JLabel("SELECT WITHDRAWAL AMOUNT");
        label.setBounds(362,140,350,30);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System",Font.BOLD,18));
        l3.add(label);

        b1=new JButton("Rs. 100");
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(65,125,128));
        b1.setBounds(350,207,110,27);
        b1.addActionListener(this);
        l3.add(b1);

        b2=new JButton("Rs. 500");
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(65,125,128));
        b2.setBounds(525,207,151,27);
        b2.addActionListener(this);
        l3.add(b2);

        b3=new JButton("Rs. 1000");
        b3.setForeground(Color.WHITE);
        b3.setBackground(new Color(65,125,128));
        b3.setBounds(350,244,110,27);
        b3.addActionListener(this);
        l3.add(b3);

        b4=new JButton("Rs. 2000");
        b4.setForeground(Color.WHITE);
        b4.setBackground(new Color(65,125,128));
        b4.setBounds(525,243,151,27);
        b4.addActionListener(this);
        l3.add(b4);

        b5=new JButton("Rs. 5000");
        b5.setForeground(Color.WHITE);
        b5.setBackground(new Color(65,125,128));
        b5.setBounds(350,280,110,27);
        b5.addActionListener(this);
        l3.add(b5);

        b6=new JButton("Rs. 10000");
        b6.setForeground(Color.WHITE);
        b6.setBackground(new Color(65,125,128));
        b6.setBounds(525,280,151,27);
        b6.addActionListener(this);
        l3.add(b6);

        b7=new JButton("Back");
        b7.setForeground(Color.WHITE);
        b7.setBackground(new Color(65,125,128));
        b7.setBounds(525,315,150,27);
        b7.addActionListener(this);
        l3.add(b7);


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

        if(e.getSource()==b7) {
            setVisible(false);
            new Main_Class(pin);
        } else {
            String amount = ((JButton)e.getSource()).getText().substring(4);

            Con c=new Con();
            Date date=new Date();
            try{
                ResultSet resultSet= c.statement.executeQuery("select * from bank where pin='"+pin+"'");
                int balance = 0;

                while (resultSet.next()){
                    if(resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    }
                    else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }

                } String num="17";
                if(e.getSource() != b7 && balance <Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    return;
                }

                c.statement.executeUpdate("insert into bank values('"+pin+"','"+date+"', 'withdrawl', '"+amount+"')");
                JOptionPane.showMessageDialog(null,"Rs. "+ amount+" Debited Successfully");
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        setVisible(false);
        new Main_Class(pin);

    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
