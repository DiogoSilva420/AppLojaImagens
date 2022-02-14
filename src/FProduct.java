import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;



public class FProduct {
    public JPanel PProdPrincipal;
    private JTextField textFieldPrice;
    private JTextField textFieldPName;
    private JTextField textFieldQuantity;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textFieldPID;
    private JButton searchButton;
    private JTextField textFieldCategory;
    private JLabel labelImagem;
    private JButton browseButton;
    private JButton buttonPrimeiro;
    private JButton buttonAnterior;
    private JButton buttonSeguinte;
    private JButton buttonUltimo;

    private String path=null;
    private byte[] userImage;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;


    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Jogadores");
        frame.setContentPane(new FProduct().PProdPrincipal);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    String name,price,qty, cat;

    public FProduct() {
    Connection();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                name = textFieldPName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQuantity.getText();
                cat = textFieldCategory.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("insert into produtos" +
                            "(NOMEPROD,PRECO,QUANTIDADE,CATEGORIA,FOTO)values(?,?,?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, cat);
                    pst.setBytes(5, userImage);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Product Added!!");
                    textFieldPName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldCategory.setText("");
                    textFieldPName.requestFocus();
                    labelImagem.setIcon(null);
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;
                bid = textFieldPID.getText();
                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("delete from produtos where ID = ?");
                    pst.setString(1, bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Deleted!!");
                    textFieldPName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldCategory.setText("");
                    textFieldPName.requestFocus();
                    textFieldPID.setText("");
                    labelImagem.setIcon(null);
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty,cat,img;
                name = textFieldPName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQuantity.getText();
                cat = textFieldCategory.getText();
                pid = textFieldPID.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("update produtos set NOMEPROD = ?," +
                            "PRECO = ?,QUANTIDADE = ?,CATEGORIA = ?,FOTO=? where ID = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, cat);
                    pst.setBytes(5, userImage);
                    pst.setString(6, pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Updated!");
                    textFieldPName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldCategory.setText("");
                    textFieldPName.requestFocus();
                    textFieldPID.setText("");
                    labelImagem.setIcon(null);
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pid = textFieldPID.getText();
                    Connection con = Connect.createConnection();
                    PreparedStatement pst = con.prepareStatement("select NOMEPROD,PRECO,QUANTIDADE,CATEGORIA,FOTO from produtos where ID = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);
                        String cat= rs.getString(4);
                        Blob img = rs.getBlob(5);
                        byte[] imageBytes = img.getBytes(1, (int) img.length());
                        ImageIcon imgIcon = new ImageIcon(new
                                ImageIcon(imageBytes).getImage().getScaledInstance(250, 250,
                                Image.SCALE_DEFAULT));
                        userImage = img.getBytes(1, (int) img.length());
                        textFieldPName.setText(name);
                        textFieldPrice.setText(price);
                        textFieldQuantity.setText(qty);
                        textFieldCategory.setText(cat);
                        labelImagem.setIcon(imgIcon);
                    }
                    else
                    {
                        textFieldPName.setText("");
                        textFieldPrice.setText("");
                        textFieldQuantity.setText("");
                        textFieldCategory.setText("");
                        labelImagem.setIcon(null);
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoosed=new JFileChooser();
                imgChoosed.showOpenDialog(null);
                File file=imgChoosed.getSelectedFile();
                path=file.getAbsolutePath();
                BufferedImage img;
                try{
                    img= ImageIO.read(imgChoosed.getSelectedFile());
                    ImageIcon imageIcon=
                            new ImageIcon(new ImageIcon(img).getImage().
                                    getScaledInstance(300,300, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imageIcon);

                    File image=new File (path);

                    FileInputStream fs=new FileInputStream(image);

                    ByteArrayOutputStream bos=new ByteArrayOutputStream();

                    byte[] buff=new byte[1024];
                    int nBytesRead=0;
                    while((nBytesRead=fs.read(buff))!=-1)
                        bos.write(buff,0,nBytesRead);
                    userImage =bos.toByteArray();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });





        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.first();

                    textFieldPID.setText(String.valueOf(rs.getInt("id")));
                    textFieldPName.setText(String.valueOf(rs.getString("nomeprod")));
                    textFieldPrice.setText(String.valueOf(rs.getInt("preco")));
                    textFieldQuantity.setText(String.valueOf(rs.getInt("quantidade")));
                    textFieldCategory.setText(String.valueOf(rs.getString("categoria")));
                    Blob blob = rs.getBlob("foto");
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                    ImageIcon imgIcon = new ImageIcon(new
                            ImageIcon(imageBytes).getImage().getScaledInstance(250, 250,
                            Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rs.isFirst()) {
                        rs.previous();

                        textFieldPID.setText(String.valueOf(rs.getInt("id")));
                        textFieldPName.setText(String.valueOf(rs.getString("nomeprod")));
                        textFieldPrice.setText(String.valueOf(rs.getInt("preco")));
                        textFieldQuantity.setText(String.valueOf(rs.getInt("quantidade")));
                        textFieldCategory.setText(String.valueOf(rs.getString("categoria")));
                        Blob blob = rs.getBlob("foto");
                        byte[] imageBytes = blob.getBytes(1, (int)
                                blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelImagem.setIcon(imgIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


        buttonSeguinte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!rs.isLast()) {
                        rs.next();

                        textFieldPID.setText(String.valueOf(rs.getInt("id")));
                        textFieldPName.setText(String.valueOf(rs.getString("nomeprod")));
                        textFieldPrice.setText(String.valueOf(rs.getInt("preco")));
                        textFieldQuantity.setText(String.valueOf(rs.getInt("quantidade")));
                        textFieldCategory.setText(String.valueOf(rs.getString("categoria")));
                        Blob blob = rs.getBlob("foto");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
                        labelImagem.setIcon(imgIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.last();

                    textFieldPID.setText(String.valueOf(rs.getInt("id")));
                    textFieldPName.setText(String.valueOf(rs.getString("nomeprod")));
                    textFieldPrice.setText(String.valueOf(rs.getInt("preco")));
                    textFieldQuantity.setText(String.valueOf(rs.getInt("quantidade")));
                    textFieldCategory.setText(String.valueOf(rs.getString("categoria")));
                    Blob blob = rs.getBlob("foto");
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void Connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BDLojaDiogoSilva",
                    "root", "1234");
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select id,nomeprod,preco, quantidade, categoria, foto from produtos ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
