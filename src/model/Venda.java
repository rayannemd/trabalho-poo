package model;

public class Venda {
    
    private Produto produto;
    private double quantidadeVendida;
    private double totalVenda;

    public Venda(Produto produto, double quantidadeVendida) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
        this.totalVenda = quantidadeVendida * produto.getPreco();
    }

    public Produto getProduto() {
        return produto;
    }

    public double getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    @Override
    public String toString() {
        return "Produto: " + produto.getNome() + "\nQuantidade vendida: " + quantidadeVendida + "\nValor apurado:" + totalVenda + "\n------------------------";
    }
}
