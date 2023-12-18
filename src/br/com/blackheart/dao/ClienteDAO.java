package br.com.blackheart.dao;

import br.com.blackheart.factory.ConnectionFactory;
import br.com.blackheart.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void save(Cliente cliente) throws Exception {
        String sql = "INSERT INTO cliente(nome_cliente, cpf_cliente, nasc_cliente) VALUES(?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.createConnectionToMySql();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            //ADICIONAR OS VALORES ESPERADOS NA QUERY
            pstm.setString(1, cliente.getNome_cliente());
            pstm.setString(2, cliente.getCpf_cliente());
            pstm.setDate(3, new Date(cliente.getNasc_cliente().getTime()));
            //EXECUTA A QUERY
            pstm.execute();

            System.out.println("Cliente salvo com sucesso");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //FECHAR AS CONEXÕES
            try{
                if (pstm != null){
                    pstm.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static List<Cliente> getCliente() throws Exception {

        String sql = "SELECT * FROM cliente;";
        List<Cliente> clientes = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        //CLASSE QUE RECUPERA OS DADOS DO BANCO
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySql();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Cliente cliente = new Cliente();
                //recuperar id_cliente
                cliente.setId_cliente(rset.getInt("id_cliente"));
                //recuperar nome_cliente
                cliente.setNome_cliente(rset.getString("nome_cliente"));
                //recuperar cpf_cliente
                cliente.setCpf_cliente(rset.getString("cpf_cliente"));
                //recuperar nasc_cliente
                cliente.setNasc_cliente(rset.getDate("nasc_cliente"));

                clientes.add(cliente);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rset != null){
                rset.close();
            }
            if (pstm != null){
                pstm.close();
            }
            if (conn != null){
                conn.close();
            }
        }
        return clientes;
    }

    public Cliente getClienteById(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySql();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();

            if (rset.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rset.getInt("id_cliente"));
                cliente.setNome_cliente(rset.getString("nome_cliente"));
                cliente.setCpf_cliente(rset.getString("cpf_cliente"));
                cliente.setNasc_cliente(rset.getDate("nasc_cliente"));
                return cliente;
            } else {
                return null; // Cliente não encontrado
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }


    public static void delete(int id_cliente) throws Exception{
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        Connection conn = null;

        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.createConnectionToMySql();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, id_cliente);

            pstm.execute();

            System.out.println("Cliente deletado com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn!= null) {
                conn.close();
            }
            if (pstm!= null) {
                pstm.close();
            }
        }
    }

}
