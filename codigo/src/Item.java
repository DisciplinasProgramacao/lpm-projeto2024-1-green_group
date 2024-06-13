/**
 * Classe que representa um item do cardápio.
 */
public class Item {

    private String nome;
    private double preco;

    public Item(String nome, double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f", nome, preco);
    }
}
