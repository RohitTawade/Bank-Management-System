package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pin extends JFrame implements ActionListener {
    JButton b1,b2;
    JPasswordField p1,p2;
    String pin;
    Pin(String pin) {
        this.pin = pin;
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

        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System",Font.BOLD,14));
        label1.setBounds(360,135,400,35);
        l3.add(label1);

        JLabel label2 = new JLabel("NEW PIN: ");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System",Font.BOLD,12));
        label2.setBounds(360,175,150,30);
        l3.add(label2);

        p1= new JPasswordField();
        p1.setBounds(490,175,150,25);
        p1.setFont(new Font("Raleway",Font.BOLD,20));
        p1.setBackground(new Color(65,125,128));
        p1.setForeground(Color.WHITE);
        l3.add(p1);

        b1=new JButton("CHANGE");
        b1.setBounds(575,282,100,25);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        JLabel label3 = new JLabel("Re-Enter New PIN: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System",Font.BOLD,12));
        label3.setBounds(360,210,150,35);
        l3.add(label3);

        p2= new JPasswordField();
        p2.setBounds(490,210,150,25);
        p2.setFont(new Font("Raleway",Font.BOLD,20));
        p2.setBackground(new Color(65,125,128));
        p2.setForeground(Color.WHITE);
        l3.add(p2);

        b2=new JButton("BACK");
        b2.setBounds(575,317,100,25);
        b2.setBackground(new Color(65,125,128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        try{
            String pin1 = p1.getText();
            String pin2 = p2.getText();

            if(!pin1.equals(pin2)) {
                JOptionPane.showMessageDialog(null,"Entered PIN does not match");
                return;
            }
            if(e.getSource()==b1){
                if(p1.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Enter New PIN");
                    return;
                }
                if(p2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Re-Enter the PIN");
                    return;
                }


                Con c=new Con();

                String q1 ="update bank set pin = '"+pin1+"' where pin='"+pin+"'";
                String q2 ="update login set pin = '"+pin1+"' where pin='"+pin+"'";
                String q3 ="update signupthree set pin = '"+pin1+"' where pin='"+pin+"'";

                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);
                c.statement.executeUpdate(q3);

                JOptionPane.showMessageDialog(null,"PIN changed successfully");
                setVisible(false);
                new Main_Class(pin);
            } else if (e.getSource()==b2) {
                new Main_Class(pin);
                setVisible(false);
            }
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }
}
