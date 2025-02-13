import model.Role;
import model.User;
import service.AuthService;
import service.ProdutoService;
import service.VendasService;
import service.exceptions.ProductNotFoundException;
import service.exceptions.UserNotFoundException;
import utils.FileUtil;
import utils.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        AuthService authService = new AuthService();
        ProdutoService cadastro = new ProdutoService();  
        VendasService vendas = new VendasService(cadastro);  
        Scanner scanner = new Scanner(System.in);

        // Fluxo de Cadastro de Usuário
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Fazer Login");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            System.out.print("Digite o nome de usuário: ");
            String username = scanner.nextLine();

            if (authService.userExists(username)) {
                System.out.println("Usuário já existe. Tente outro nome.");
                Logger.log("Tentativa de cadastro de usuário falhou. Usuário já existe: " + username);
                return;
            }

            System.out.print("Digite a senha: ");
            String password = scanner.nextLine();

            System.out.print("Digite o email: ");
            String email = scanner.nextLine();

            User newUser = new User(username, password, email, new Role("User", null));

            try {
                authService.addUser(newUser);  
                System.out.println("Usuário cadastrado com sucesso!");
                Logger.log("Novo usuário " + username + " cadastrado com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar o usuário: " + e.getMessage());
                Logger.log("Erro ao cadastrar usuário: " + e.getMessage());
                return;
            }
        }

        // Fluxo de Login
        System.out.println("\nPor favor, faça o login");
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String password = scanner.nextLine();

        User loggedInUser = null;

        try {
            loggedInUser = authService.authenticate(username, password);
            System.out.println("Bem-vindo, " + loggedInUser.getUsername() + "!");
            Logger.log("Usuário " + loggedInUser.getUsername() + " logado com sucesso.");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            Logger.log("Erro de login: " + e.getMessage());
            return;
        }

        // Escolha entre Gerenciamento de Estoque ou Vendas
        boolean systemRunning = true;
        while (systemRunning) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Gerenciar Estoque");
            System.out.println("2 - Gerenciar Vendas");
            System.out.println("3 - Sair");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    gerenciarEstoque(cadastro, scanner);
                    break;
                case 2:
                    gerenciarVendasService(vendas, scanner);
                    break;
                case 3:
                    systemRunning = false;
                    Logger.log("Sistema fechado por " + loggedInUser.getUsername());
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Método para gerenciar o estoque
    private static void gerenciarEstoque(ProdutoService cadastro, Scanner scanner) {
        boolean estoqueRunning = true;
        while (estoqueRunning) {
            System.out.println("\nEscolha uma opção do gerenciamento de estoque:");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Mostrar Estoque");
            System.out.println("3 - Atualizar Produto");
            System.out.println("4 - Remover Produto");
            System.out.println("5 - Voltar");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    cadastro.cadastrarProduto();
                    Logger.log("Produto cadastrado com sucesso.");
                    break;
                case 2:
                    cadastro.mostrarEstoque();
                    Logger.log("Visualização do estoque realizada.");
                    break;
                case 3:
                    try {
                        cadastro.atualizarProduto();
                        Logger.log("Produto atualizado com sucesso");
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        Logger.log("Erro ao atualizar produto: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        cadastro.removerProduto();
                        Logger.log("Produto removido com sucesso");
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        Logger.log("Erro ao remover produto: " + e.getMessage());
                    }
                    break;
                case 5:
                    estoqueRunning = false;
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Método para gerenciar as Vendas
    private static void gerenciarVendasService(VendasService vendas, Scanner scanner) throws IOException {
        boolean vendasRunning  = true;
        while (vendasRunning) {
            System.out.println("\nEscolha uma opção de gerenciamento de vendasSeVendasService:");
            System.out.println("1 - Realizar Venda");
            System.out.println("2 - Visualizar Vendas");
            System.out.println("3 - Voltar");
            int option = scanner.nextInt();
            scanner.nextLine();  

            switch (option) {
                case 1:
                    vendas.realizarVenda();
                    Logger.log("Venda realizada com sucesso.");
                    break;
                case 2:
                    try {
                        List<String> listaVendas = FileUtil.loadVendas();
                        System.out.println("\nVendas Realizadas:");
                        for (String venda : listaVendas) {
                            System.out.println(venda); 
                        }
                        Logger.log("Visualização de vendas realizada.");
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar as vendas: " + e.getMessage());
                        Logger.log("Erro ao carregar vendas: " + e.getMessage());
                    }
                    break;
                case 3:
                    vendasRunning  = false;
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
