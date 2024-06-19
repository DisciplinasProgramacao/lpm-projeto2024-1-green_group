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

/**
 * Classe que representa um pedido fechado no restaurante, com itens e quantidades restritas.
 */
public class PedidoFechado extends Pedido {

    private static final int MAX_COMIDAS = 1;
    private static final int MAX_BEBIDAS = 2;
    private static final double PRECO_FIXO = 32.00;
    private int contComidas;
    private int contBebidas;

    public PedidoFechado() {
        super();
        contComidas = 0;
        contBebidas = 0;
    }

    @Override
    public void adicionarItem(Item item) {
        String nome = item.getNome().toLowerCase();
        if (nome.contains("falafel") || nome.contains("caçarola")) {
            if (contComidas >= MAX_COMIDAS) {
                throw new IllegalArgumentException("Não é permitido adicionar mais de " + MAX_COMIDAS + " comida ao pedido fechado.");
            }
            contComidas++;
        } else if (nome.contains("suco") || nome.contains("refrigerante") || nome.contains("cerveja")) {
            if (contBebidas >= MAX_BEBIDAS) {
                throw new IllegalArgumentException("Não é permitido adicionar mais de " + MAX_BEBIDAS + " bebidas ao pedido fechado.");
            }
            contBebidas++;
        } else {
            throw new IllegalArgumentException("Item não permitido no cardápio fechado.");
        }
        super.adicionarItem(item);
    }

    @Override
    public double calcularTotal() {
        return PRECO_FIXO;
    }

    @Override
    public String toString() {
        StringBuilder descricao = new StringBuilder("Pedido Fechado:\n");
        for (Item item : itens) {
            descricao.append(item.toString()).append("\n");
        }
        descricao.append(String.format("Total: R$ %.2f", calcularTotal()));
        return descricao.toString();
    }
}
