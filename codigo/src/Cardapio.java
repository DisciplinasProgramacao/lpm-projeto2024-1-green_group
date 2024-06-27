import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa o cardápio do restaurante.
 */
public class Cardapio {

    protected Map<Integer, Item> itens;
    private static int ultimoCodigo;

    public Cardapio() {
        itens = new HashMap<>();
        carregarItens();
    }

    protected void carregarItens() {
        addItem("Moqueca de Palmito", 32.00);
        addItem("Falafel Assado", 20.00);
        addItem("Salada Primavera com Macarrão Konjac", 25.00);
        addItem("Escondidinho de Inhame", 18.00);
        addItem("Strogonoff de Cogumelos", 35.00);
        addItem("Caçarola de legumes", 22.00);
    }

    protected void addItem(String nome, double preco) {
        itens.put(++ultimoCodigo, new Item(nome, preco));
    }

    public Item getItem(int codigo) {
        return itens.get(codigo);
    }

    public Map<Integer, Item> getItens() {
        return itens;
    }

    @Override
    public String toString() {
        StringBuilder descricao = new StringBuilder("Cardápio:\n");
        for (Map.Entry<Integer, Item> entry : itens.entrySet()) {
            descricao.append(String.format("%d - %s\n", entry.getKey(), entry.getValue()));
        }
        return descricao.toString();
    }