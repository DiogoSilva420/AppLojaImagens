import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JButton productsButton;
    private JButton categoryButton;
    private JPanel PPrincipal;

    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Jogadores");
        frame.setContentPane(new Menu().PPrincipal);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Menu() {


        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FProduct().setVisible(true);
            }
        });
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Category().setVisible(true);

            }
        });
    }


}
