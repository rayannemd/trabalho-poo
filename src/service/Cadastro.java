package service;

import model.Produto;
import service.exceptions.ProductNotFoundException;

import utils.Logger;

import java.util.Scanner;

public class Cadastro {

    private Produto[] produtos;
    private int contador;

    public Cadastro(int capacidade) {
        produtos = new Produto[capacidade];
        contador = 0;
    }

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
        produtos[contador] = produto;
        contador++;

        if (produto.estoqueBaixo()) {
            System.out.println("ALERTA: O estoque de " + nome + " está abaixo de 10 Kg.");
        }

        System.out.println("Produto cadastrado com sucesso!");

        // Log de cadastro
        Logger.log("Produto cadastrado: " + nome + " - Quantidade: " + quantidade + "kg - Preço: R$ " + preco);
    }

    public void mostrarEstoque() {
        System.out.println("Estoque Atual:");
        for (int i = 0; i < contador; i++) {
            produtos[i].mostrarInfo();
            if (produtos[i].estoqueBaixo()) {
                System.out.println("ALERTA: O estoque de " + produtos[i].getNome() + " está abaixo de 10 Kg.");
            }
        }
    }

    public void atualizarProduto() throws ProductNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto que você deseja atualizar:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (int i = 0; i < contador; i++) {
            if (produtos[i].getNome().equalsIgnoreCase(nome)) {
                encontrado = true;

                System.out.println("Produto encontrado! Atualize os dados do produto.");
                System.out.print("Novo nome (deixe em branco para manter o mesmo): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    produtos[i].setNome(novoNome);
                }

                System.out.print("Nova descrição (deixe em branco para manter a mesma): ");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.isEmpty()) {
                    produtos[i].setDescricao(novaDescricao);
                }

                System.out.print("Nova quantidade: ");
                int novaQuantidade = scanner.nextInt();
                produtos[i].setQuantidade(novaQuantidade);

                System.out.print("Novo preço: R$ ");
                double novoPreco = scanner.nextDouble();
                produtos[i].setPreco(novoPreco);

                System.out.println("Produto atualizado com sucesso!");

                if (produtos[i].estoqueBaixo()) {
                    System.out.println("ALERTA: O estoque de " + produtos[i].getNome() + " está abaixo de 10 Kg.");
                }

                // Log de atualização
                Logger.log("Produto atualizado: " + produtos[i].getNome() + " - Nova Quantidade: " + novaQuantidade + "kg - Novo Preço: R$ " + novoPreco);
                break;
            }
        }

        if (!encontrado) {
            throw new ProductNotFoundException("Produto não encontrado para atualização!");
        }
    }

    public void removerProduto() throws ProductNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto que você deseja remover:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (int i = 0; i < contador; i++) {
            if (produtos[i].getNome().equalsIgnoreCase(nome)) {
                encontrado = true;

                for (int j = i; j < contador - 1; j++) {
                    produtos[j] = produtos[j + 1];
                }
                produtos[contador - 1] = null;
                contador--;

                System.out.println("Produto removido com sucesso!");

                Logger.log("Produto removido: " + nome);
                break;
            }
        }

        if (!encontrado) {
            throw new ProductNotFoundException("Produto não encontrado para remoção!");
        }
    }
}
