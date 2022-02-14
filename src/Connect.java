import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection createConnection() throws SQLException {

        String url="jdbc:mysql://localhost:3306/BDLojaDiogoSilva";
        String user="root";
        String pass="1234";

        Connection conexao=null;
        conexao= DriverManager.getConnection(url,user,pass);

        return conexao;
    }

}
