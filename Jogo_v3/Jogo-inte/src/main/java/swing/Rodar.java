
package swing;

import java.sql.Connection;
import java.util.Scanner;

public class Rodar {
    public static void main(String[] args) {
        ConectarBanco obj = new ConectarBanco();
        Connection conexao = obj.connectionMySql();
        // closse de conexao default para o banco de dados no java
        System.out.println("Digite o codigo para buscar no banco:");
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();
        boolean z = obj.BuscaCodnoBanco(code);
        System.out.println(z);
        //obj.dataBaseInsert("");
        //obj.closeConnectionMySql(conexao);
    }
}
