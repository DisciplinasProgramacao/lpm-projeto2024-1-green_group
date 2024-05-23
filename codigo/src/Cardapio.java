import java.util.HashMap;
import java.util.Map;

public class Cardapio {

    private Map<String, Item> itens;

    public Cardapio() {
        itens = new HashMap<>();
        carregarItens();
    }
    
    private void carregarItens() {
        itens.put("Moqueca de Palmito", new Item("Moqueca de Palmito", 32.00));
        itens.put("Falafel Assado", new Item("Falafel Assado", 20.00));
        itens.put("Salada Primavera com Macarrão Konjac", new Item("Salada Primavera com Macarrão Konjac", 25.00));
        itens.put("Escondidinho de Inhame", new Item("Escondidinho de Inhame", 18.00));
        itens.put("Strogonoff de Cogumelos", new Item("Strogonoff de Cogumelos", 35.00));
        itens.put("Caçarola de legumes", new Item("Caçarola de legumes", 22.00));
    }

    public Item getItem(String nome) {
        return itens.get(nome);
    }

    @Override
    public String toString() {
        StringBuilder descricao = new StringBuilder("Cardápio:\n");
        for (Item item : itens.values()) {
            descricao.append(item.toString()).append("\n");
        }
        return descricao.toString();
    }
}
