
package swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConectarBanco {
    private static Connection conexao_My_sql = null;
    private static String localBD = "localhost";// IP
    private static String Link = "jdbc:mysql://" + localBD + ":3306/testetb";
    private static final String usuario = "root";
    private static final String senha = "";

    //Metodo para fazer a conexao  com o banco de dados
    public Connection connectionMySql() {
        try {
            conexao_My_sql = DriverManager.getConnection(Link, usuario, senha);
            //classe de Conexão com metodo getConnection  - link da conexão, usuario e senha
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro!!" + "Problema de conexão com BD", e);
        }
        return conexao_My_sql;
    }
    
    public boolean BuscarLogin(String login,String senha) {
        Connection con = connectionMySql();
        boolean x = false;
        String sql = "select login, senha from log where login = ? and senha = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = con.prepareStatement(sql);
            //Efetua a troca do '?' pelos valores na query
            preparedStmt.setString(1, login);
            preparedStmt.setString(2, senha);
            ResultSet rs = preparedStmt.executeQuery();
            //valida resultado
            while (rs.next()) {
                String logar1 = rs.getString("login");
                String senha2 = rs.getString("senha");
                System.out.println("login é; " + logar1);
                System.out.println("senha é; " + senha2);
                x = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
    }
    
    
    public boolean BuscaCodnoBanco(int cod) {
        Connection con = connectionMySql();
        boolean x = false;
        String sql = "select nome, email, cod from aluno where cod = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = con.prepareStatement(sql);
            //Efetua a troca do '?' pelos valores na query
            preparedStmt.setInt(1, cod);
            ResultSet rs = preparedStmt.executeQuery(); // busca no banco o cod do aluno
            //valida resultado
            while (rs.next()) {
                int code = rs.getInt("cod");
                String name = rs.getString("nome");
                String email = rs.getString("email");
                System.out.println("cod: " + code);
                System.out.println("name: " + name);
                System.out.println("email : " + email);
                x = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
    }
    
    
    
    public void Cadastrolog(String login, String senha) {
 
        Connection connection = connectionMySql();
        String sql = "INSERT INTO log (login, senha) VALUES (?,?)";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(sql);
 
            //Efetua a troca do '?' pelos valores na query 			
            preparedStmt.setString(1, login);
            preparedStmt.setString(2, senha);
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null, "Logado com exito!!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void closeConnectionMySql(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Fechamento OK");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um problema para encerrar a conexão com o BD.", e);
        }
    }
    
}
