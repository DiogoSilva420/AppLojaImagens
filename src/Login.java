import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JTextField textFieldUser;
    private JButton submitButton;
    private JPanel PLogin;
    private JPasswordField passwordField1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Management");
        frame.setContentPane(new Login().PLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Login() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser=textFieldUser.getText();
                String txtPass=passwordField1.getText();


                try
                {
                    Connection conn=Connect.createConnection();

                    String sql="SELECT NOME, USERNAME, PASSWRD FROM FUNCIONARIOS";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs=ps.executeQuery();
                    boolean fg=false;

                    while(rs.next())
                    {
                        String nome = rs.getString(1);
                        String user=rs.getString("USERNAME");
                        String pass=rs.getString("PASSWRD");
                        System.out.printf("%s - %s - %s", nome,user,pass);
                        //System.out.printf(nome+"-" + user + "-" + pass);

                        if (txtUser.equals(user) && txtPass.equals(pass))
                        {
                            new Menu().setVisible(true);
                            fg=true;
                        }
                    }
                    if(!fg)
                    {
                        JOptionPane.showMessageDialog(null,"Invalid Login! Incorrect username or password!");
                    }
                }
                catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro: " + ex.getMessage());
                }
            }
        });
    }
}
