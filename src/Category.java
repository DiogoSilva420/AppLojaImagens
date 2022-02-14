import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {
    private JPanel PCategory;
    private JButton saveCatButton;
    private JButton deleteCatButton;
    private JTextField textFieldCatID;
    private JButton searchByCatButton;
    private JTextField textFieldCategory;
    private JTextArea textAreaCatProd;

    public Category() {
        String cat;
        searchByCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cid = textFieldCatID.getText();
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("select NOMECAT from categorias where ID = ?");
                    pst.setString(1, cid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String cat= rs.getString(1);
                        textFieldCategory.setText(cat);
                    }
                    else
                    {
                        textFieldCategory.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

                //mostrar ma text area
                String cat=textFieldCatID.getText();

                textAreaCatProd.setText("");
                try{
                    Connection conn=Connect.createConnection() ;
                    String sql="SELECT NOMEPROD FROM PRODUTOS WHERE CATEGORIA = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, cat);
                    ResultSet rs=ps.executeQuery();
                    boolean fg=false;

                    while(rs.next())
                    {
                        String nomeprod = rs.getString(1);
                        textAreaCatProd.append(nomeprod + "\n");
                    }

                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro: " + ex.getMessage());
                }





            }
        });

        textAreaCatProd.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

            }
        });
        saveCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cat = textFieldCategory.getText();
                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("insert into categorias(NOMECAT)values(?)");
                    pst.setString(1, cat);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Category Added!!");
                    textFieldCategory.setText("");
                    textFieldCategory.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        deleteCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid;
                cid = textFieldCatID.getText();
                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("delete from categorias where ID = ?");
                    pst.setString(1, cid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Category Deleted!!");
                    textFieldCategory.setText("");

                    textFieldCatID.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Jogadores");
        frame.setContentPane(new Category().PCategory);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
