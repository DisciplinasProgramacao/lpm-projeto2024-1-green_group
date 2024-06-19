import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma requisição de mesa no restaurante.
 */
public class Requisicao {

    private Cliente cliente;
    private Mesa mesa;
    private int quantPessoas;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private boolean encerrada;
    private Pedido pedido;

    public Requisicao(int quantPessoas, Cliente cliente) {
        this.quantPessoas = 1;
        if (quantPessoas > 1) {
            this.quantPessoas = quantPessoas;
        }
        this.cliente = cliente;
        this.entrada = this.saida = null;
        this.mesa = null;
        this.encerrada = false;
        this.pedido = new Pedido();
    }

    public Mesa encerrar() {
        Mesa liberada = null;
        if (!encerrada && mesa != null) {
            saida = LocalDateTime.now();
            mesa.desocupar();
            liberada = mesa;
            encerrada = true;
        }
        return liberada;
    }

    public void alocarMesa(Mesa mesa) {
        if (!encerrada && mesa.estahLiberada(quantPessoas)) {
            this.mesa = mesa;
            entrada = LocalDateTime.now();
            this.mesa.ocupar();
        }
    }

    public boolean estahEncerrada() {
        return encerrada;
    }

    public boolean ehDaMesa(int idMesa) {
        return idMesa == mesa.getIdMesa();
    }

    public int getQuantPessoas() {
        return quantPessoas;
    }

    public void adicionarAoPedido(Item item) {
        if (item != null) {
            pedido.adicionarItem(item);
        }
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder stringReq = new StringBuilder(cliente.toString());
        if (mesa != null) {
            stringReq.append("\n").append(mesa.toString()).append("\n");
            stringReq.append("Entrada em ").append(formato.format(entrada)).append("\n");
            if (saida != null) {
                stringReq.append("Saída em ").append(formato.format(saida)).append("\n");
            }
            stringReq.append(pedido.toString());
        } else {
            stringReq.append(" Em espera.");
        }
        return stringReq.toString();
    }
}
