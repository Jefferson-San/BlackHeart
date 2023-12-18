package br.com.blackheart.application;

import br.com.blackheart.dao.ClienteDAO;
import br.com.blackheart.model.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Deletar Cliente");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    deletarCliente();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void adicionarCliente() {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente();
        System.out.println("Digite o nome do cliente:");
        cliente.setNome_cliente(scanner.nextLine());
        System.out.println("Digite o CPF do cliente:");
        cliente.setCpf_cliente(scanner.nextLine());

        System.out.println("Digite a data de nascimento do cliente (formato: YYYY-MM-DD):");
        String dataNascimentoStr = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dataNascimento = dateFormat.parse(dataNascimentoStr);
            cliente.setNasc_cliente(dataNascimento);

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.save(cliente);
            System.out.println("Cliente adicionado com sucesso.");
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Tente novamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar cliente.");
        }
    }

    private static void listarClientes() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            List<Cliente> clientes = clienteDAO.getCliente();

            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId_cliente() +
                        ", Nome: " + cliente.getNome_cliente() +
                        ", CPF: " + cliente.getCpf_cliente() +
                        ", Data de Nascimento: " + cliente.getNasc_cliente());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar clientes.");
        }
    }

    private static void atualizarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente que deseja atualizar:");
        int idCliente = scanner.nextInt();

        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.getClienteById(idCliente);

            if (cliente != null) {
                System.out.println("Digite o novo nome do cliente:");
                cliente.setNome_cliente(scanner.nextLine());

                System.out.println("Digite o novo CPF do cliente:");
                cliente.setCpf_cliente(scanner.nextLine());

                System.out.println("Digite a nova data de nascimento do cliente (formato: YYYY-MM-DD):");
                String dataNascimentoStr = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dataNascimento = dateFormat.parse(dataNascimentoStr);
                    cliente.setNasc_cliente(dataNascimento);

                    clienteDAO.getClienteById(cliente.getId_cliente());
                    System.out.println("Cliente atualizado com sucesso.");
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Tente novamente.");
                }
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar cliente.");
        }
    }

    private static void deletarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID do cliente que deseja deletar:");
        int idCliente = scanner.nextInt();

        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.delete(idCliente);
            System.out.println("Cliente deletado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar cliente.");
        }
    }
}
