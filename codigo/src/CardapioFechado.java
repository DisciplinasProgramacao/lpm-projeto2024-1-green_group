
/**
 * Classe que representa o cardápio fechado do restaurante, com itens restritos.
 */
public class CardapioFechado extends Cardapio {

    @Override
    protected void carregarItens() {
        addItem("Falafel Assado", 32.00);
        addItem("Caçarola de legumes", 32.00);
        addItem("Copo de suco", 5.00);
        addItem("Refrigerante orgânico", 5.00);
        addItem("Cerveja vegana", 5.00);
    }
}