import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe que representa o restaurante, gerenciando mesas, clientes e pedidos.
 */
public class Restaurante {

    private static final int MAX_FILA = 1000;
    private static final int MAX_MESAS = 10;
    private static final int MAX_CLIENTES = 1000;
    private List<Mesa> mesas;
    private Map<Integer, Cliente> clientes;
    private List<Requisicao> atendidas;
    private List<Requisicao> espera;
    private int quantClientes;
    private Cardapio cardapio;

    public Restaurante() {
        mesas = new ArrayList<>(MAX_MESAS);
        clientes = new HashMap<>(MAX_CLIENTES);
        atendidas = new ArrayList<>(MAX_FILA);
        espera = new ArrayList<>(MAX_FILA);
        quantClientes = 0;
        cardapio = new Cardapio();
        criarMesas();
    }

    private void criarMesas() {
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(4));
        }
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(6));
        }
        for (int i = 0; i < 2; i++) {
            mesas.add(new Mesa(8));
        }
    }

    public void addCliente(Cliente novo) {
        if (novo != null && quantClientes < MAX_CLIENTES) {
            clientes.put(novo.hashCode(), novo);
            quantClientes++;
        }
    }

    /**
     * Localiza um cliente pelo seu identificador.
     *
     * @param idCli O identificador do cliente.
     * @return O cliente correspondente ao identificador, ou null se não encontrado.
     */
    public Cliente localizarCliente(int idCli) {
        return clientes.get(idCli);
    }

    private Optional<Mesa> localizarMesaDisponivel(int quantPessoas) {
        return mesas.stream()
            .filter(mesa -> mesa.estahLiberada(quantPessoas))
            .findFirst();
    }

    public Requisicao encerrarAtendimento(int idMesa) {
        Optional<Requisicao> encerrada = localizarRequisicao(idMesa);
        encerrada.ifPresent(Requisicao::encerrar);
        return encerrada.orElse(null);
    }

    public Requisicao processarFila() {
        for (int i = 0; i < espera.size(); i++) {
            Requisicao requisicao = espera.get(i);
            Optional<Mesa> mesaLivre = localizarMesaDisponivel(requisicao.getQuantPessoas());
            if (mesaLivre.isPresent()) {
                atenderRequisicao(requisicao, mesaLivre.get());
                retirarDaFila(i);
                return requisicao;
            }
        }
        return null;
    }

    private void retirarDaFila(int pos) {
        espera.remove(pos);
    }

    public void registrarRequisicao(Requisicao novaRequisicao) {
        if (novaRequisicao != null && espera.size() < MAX_FILA) {
            espera.add(novaRequisicao);
        }
    }

    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        requisicao.alocarMesa(mesa);
        atendidas.add(requisicao);
    }

    public String statusMesas() {
        String livres = mesas.stream()
            .filter(mesa -> mesa.estahLiberada(1))
            .map(Mesa::toString)
            .collect(Collectors.joining("\n", "Mesas livres: \n", "\n"));

        String ocupadas = mesas.stream()
            .filter(mesa -> !mesa.estahLiberada(1))
            .map(Mesa::toString)
            .collect(Collectors.joining("\n", "Mesas em atendimento: \n", "\n"));

        return livres + ocupadas;
    }

    public String filaDeEspera() {
        if (!espera.isEmpty()) {
            return espera.stream()
                .map(Requisicao::toString)
                .collect(Collectors.joining("\n", "Fila de espera: \n", "\n"));
        } else {
            return "Fila vazia";
        }
    }

    public String exibirCardapio() {
        return cardapio.toString();
    }

    public boolean adicionarItemAoPedido(int idMesa, int codigoItem) {
        Optional<Requisicao> requisicao = localizarRequisicao(idMesa);
        Item item = cardapio.getItem(codigoItem);
        if (requisicao.isPresent() && item != null) {
            requisicao.get().adicionarAoPedido(item);
            return true;
        }
        return false;
    }

    private Optional<Requisicao> localizarRequisicao(int idMesa) {
        return atendidas.stream()
            .filter(requisicao -> !requisicao.estahEncerrada() && requisicao.ehDaMesa(idMesa))
            .findFirst();
    }
}
