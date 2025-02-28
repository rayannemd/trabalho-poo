package utils;

import model.Produto;
import model.Role;
import model.User;
import model.Venda;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String USERS_FILE_PATH = "./resources/users.txt";
    private static final String ESTOQUE_FILE_PATH = "./resources/estoque.txt";  
    private static final String VENDAS_FILE_PATH = "./resources/vendas.txt";

    public static void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getUsername() + ";" + user.getPassword() + ";" + user.getEmail() + ";" + user.getRole().getName());
                writer.newLine();
            }
        }
    }

    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Role role = new Role(data[3], null);
                User user = new User(data[0], data[1], data[2], role);
                users.add(user);
            }
        }
        return users;
    }

    public static void saveEstoque(List<Produto> produtos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ESTOQUE_FILE_PATH))) {
            for (Produto produto : produtos) {
                writer.write(produto.getNome() + ";" + produto.getDescricao() + ";" + produto.getQuantidade() + ";" + produto.getPreco());
                writer.newLine();
            }
        }
    }

    public static List<Produto> loadEstoque() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ESTOQUE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Produto produto = new Produto(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]));
                produtos.add(produto);
            }
        }
        return produtos;
    }

    public static void saveVenda(Venda venda) throws IOException {
        File file = new File("vendas.txt");
    
        if (!file.exists()) {
            try {
                file.createNewFile();  
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo de vendas: " + e.getMessage());
            }
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VENDAS_FILE_PATH, true))) {
            writer.write(venda.toString());
            writer.newLine();
        }
    }

    public static List<String> loadVendas() throws IOException {
        List<String> vendas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(VENDAS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                vendas.add(line); 
            }
        }

        return vendas; 
    }

}
