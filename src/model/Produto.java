package model;

public class Produto {

    private String nome;
    private String descricao;
    private int quantidade;
    private double preco;

    public Produto(String nome, String descricao, int quantidade, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void mostrarInfo() {
        System.out.println("Produto: " + nome);
        System.out.println("Descrição: " + descricao);
        System.out.println("Quantidade em Kg: " + quantidade);
        System.out.println("Preço: R$ " + preco);
        System.out.println("------------");
    }

    public boolean estoqueBaixo() {
        return quantidade < 10; 
    }
}
