import java.util.LinkedList;
import java.util.List;

/**
 * Classe que representa um pedido no restaurante.
 */
public class Pedido {

    protected List<Item> itens;

    public Pedido() {
        itens = new LinkedList<>();
    }

    public void adicionarItem(Item item) {
        if (item != null) {
            itens.add(item);
        }
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(Item::getPreco).sum();
    }

    @Override
    public String toString() {
        StringBuilder descricao = new StringBuilder("Pedido:\n");
        itens.forEach(item -> descricao.append(item.toString()).append("\n"));
        descricao.append(String.format("Total: R$ %.2f", calcularTotal()));
        return descricao.toString();
    }
}
