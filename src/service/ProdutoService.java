package service;

import model.Produto;
import service.exceptions.ProductNotFoundException;
import utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutoService {

    private List<Produto> produtos;

    public ProdutoService() {
        try {
            this.produtos = FileUtil.loadEstoque(); 
        } catch (IOException e) {
            System.out.println("Erro ao carregar o estoque: " + e.getMessage());
            this.produtos = new ArrayList<>();
        }
    }

    // Método para cadastrar um novo produto
    public void cadastrarProduto() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do produto:");
        String nome = scanner.nextLine();

        System.out.println("Digite a descrição do produto:");
        String descricao = scanner.nextLine();

        System.out.print("Digite a quantidade do produto em Kg: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço do produto: R$ ");
        double preco = scanner.nextDouble();

        Produto produto = new Produto(nome, descricao, quantidade, preco);
        produtos.add(produto);  

        try {
            FileUtil.saveEstoque(produtos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o produto no estoque: " + e.getMessage());
        }

        if (produto.estoqueBaixo()) {
            System.out.println("ALERTA: O estoque de " + nome + " está abaixo de 10 Kg.");
        }

        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("------------");
    }

    // Método para mostrar todos os produtos no estoque
    public void mostrarEstoque() {
        System.out.println("Estoque Atual:");
        for (Produto produto : produtos) {
            produto.mostrarInfo(); 
            if (produto.estoqueBaixo()) {
                System.out.println("ALERTA: O estoque de " + produto.getNome() + " está abaixo de 10 Kg.");
            }
        }
    }

    // Método para atualizar as informações de um produto
    public void atualizarProduto() throws ProductNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto que você deseja atualizar:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                encontrado = true;

                System.out.println("Produto encontrado! Atualize os dados do produto.");

                System.out.print("Novo nome (deixe em branco para manter o mesmo): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    produto.setNome(novoNome);
                }

                System.out.print("Nova descrição (deixe em branco para manter a mesma): ");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.isEmpty()) {
                    produto.setDescricao(novaDescricao);
                }

                System.out.print("Nova quantidade: ");
                int novaQuantidade = scanner.nextInt();
                produto.setQuantidade(novaQuantidade);

                System.out.print("Novo preço: R$ ");
                double novoPreco = scanner.nextDouble();
                produto.setPreco(novoPreco);

                try {
                    FileUtil.saveEstoque(produtos);  
                } catch (IOException e) {
                    System.out.println("Erro ao salvar o estoque após atualização: " + e.getMessage());
                }

                System.out.println("Produto atualizado com sucesso!");

                if (produto.estoqueBaixo()) {
                    System.out.println("ALERTA: O estoque de " + produto.getNome() + " está abaixo de 10 Kg.");
                }
                break;
            }
        }

        if (!encontrado) {
            throw new ProductNotFoundException("Produto não encontrado!");
        }
    }

    // Método para remover um produto do estoque
    public void removerProduto() throws ProductNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto que você deseja remover:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equalsIgnoreCase(nome)) {
                encontrado = true;

                produtos.remove(i); 

                try {
                    FileUtil.saveEstoque(produtos); 
                } catch (IOException e) {
                    System.out.println("Erro ao salvar o estoque após remoção: " + e.getMessage());
                }

                System.out.println("Produto removido com sucesso!");
                break;
            }
        }

        if (!encontrado) {
            throw new ProductNotFoundException("Produto não encontrado!");
        }
    }

    // Método para obter a lista de produtos
    public List<Produto> getProdutos() {
        return produtos;
    }
}
