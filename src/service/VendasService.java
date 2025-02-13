package service;

import utils.FileUtil;
import model.Produto;
import model.Venda;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class VendasService {
    
    private ProdutoService cadastro;

    public VendasService(ProdutoService cadastro) {
        this.cadastro = cadastro;
    }

    public void realizarVenda() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto que deseja vender:");
        String nome = scanner.nextLine();

        List<Produto> produtos = cadastro.getProdutos();
        boolean encontrado = false;

        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                encontrado = true;
                System.out.print("Qual quantidade a vender em Kg: ");
                int quantidadeVendida = scanner.nextInt();

                if (quantidadeVendida > produto.getQuantidade()) {
                    System.out.println("Estoque insuficiente!");
                } else {
                    // Cria a venda
                    Venda venda = new Venda(produto, quantidadeVendida);

                    produto.setQuantidade(produto.getQuantidade() - quantidadeVendida);
                    System.out.println("Venda realizada com sucesso!");
                    System.out.println("Total: R$ " + venda.getTotalVenda());

                    // Salva a venda no arquivo vendas.txt
                    FileUtil.saveVenda(venda);
                    // Atualiza o estoque
                    atualizarEstoque();

                    if (produto.estoqueBaixo()) {
                        System.out.println("ALERTA: O estoque de " + produto.getNome() + " está abaixo de 10 Kg.");
                    }
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado!");
        }
    }

    private void atualizarEstoque() {
        try {
            FileUtil.saveEstoque(cadastro.getProdutos());  
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o estoque: " + e.getMessage());
        }
    }
    
}
