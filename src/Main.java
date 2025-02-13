import model.Role;
import model.User;
import service.AuthService;
import service.Cadastro;
import service.exceptions.ProductNotFoundException;
import service.exceptions.UserNotFoundException;
import utils.Logger;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AuthService authService = new AuthService();
        Cadastro cadastro = new Cadastro();  
        Scanner scanner = new Scanner(System.in);

        // Fluxo de Cadastro de Usuário
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Fazer Login");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            // Cadastro de Usuário
            System.out.print("Digite o nome de usuário: ");
            String username = scanner.nextLine();

            if (authService.userExists(username)) {
                System.out.println("Usuário já existe. Tente outro nome.");
                return;
            }

            System.out.print("Digite a senha: ");
            String password = scanner.nextLine();

            System.out.print("Digite o email: ");
            String email = scanner.nextLine();

            // Criação do novo usuário
            User newUser = new User(username, password, email, new Role("User", null));

            try {
                authService.addUser(newUser);  // Salva o novo usuário no arquivo
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

        // Fluxo de Cadastro de Produtos (após login bem-sucedido)
        boolean systemRunning = true;
        while (systemRunning) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Mostrar Estoque");
            System.out.println("3 - Atualizar Produto");
            System.out.println("4 - Remover Produto");
            System.out.println("5 - Sair");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    cadastro.cadastrarProduto(); 
                    break;
                case 2:
                    cadastro.mostrarEstoque();  
                    break;
                case 3:
                    try {
                        cadastro.atualizarProduto();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        Logger.log("Erro ao atualizar produto: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        cadastro.removerProduto();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        Logger.log("Erro ao remover produto: " + e.getMessage());
                    }
                    break;
                case 5:
                    systemRunning = false;
                    Logger.log("Sistema fechado por " + loggedInUser.getUsername());
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
