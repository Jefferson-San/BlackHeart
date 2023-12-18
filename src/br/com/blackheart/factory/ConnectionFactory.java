package br.com.blackheart.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    //nome usuario SGBD Mysql
    private static final String USERNAME = "root";
    //senha banco Mysql
    private static final String PASSWORLD = "root";
    //caminho do banco de dados
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/blackheart";

    public static Connection createConnectionToMySql() throws Exception {


        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORLD);

        return connection;
    }

    public static void main(String[] args) throws Exception {
        //recuperar a conexão com o banco de dados
        Connection con = createConnectionToMySql();

        if (con != null){
            System.out.println("Conexão obtida com sucesso!");
            con.close();
        }

    }
}
