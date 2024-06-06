import java.util.Arrays;
import java.util.stream.Collectors;

public class Restaurante {

    private static final int MAX_FILA = 1000;
    private static final int MAX_MESAS = 10;
    private static final int MAX_CLIENTES = 1000;
    private Mesa[] mesas;
    private Cliente[] clientes;
    private Requisicao[] atendidas;
    private Requisicao[] espera;
    private int quantClientes;
    private int quantMesas;
    private int requisicoesAtendidas;
    private int requisicoesEmEspera;
    private Cardapio cardapio;

    public Restaurante() {
        mesas = new Mesa[MAX_MESAS];
        clientes = new Cliente[MAX_CLIENTES];
        atendidas = new Requisicao[MAX_FILA];
        espera = new Requisicao[MAX_FILA];
        quantMesas = quantClientes = requisicoesAtendidas = requisicoesEmEspera = 0;
        cardapio = new Cardapio();
        criarMesas();
    }

    private void criarMesas() {
        for (int i = 0; i < 4; i++) {
            mesas[quantMesas++] = new Mesa(4);
        }
        for (int i = 0; i < 4; i++) {
            mesas[quantMesas++] = new Mesa(6);
        }
        for (int i = 0; i < 2; i++) {
            mesas[quantMesas++] = new Mesa(8);
        }
    }

    public void addCliente(Cliente novo) {
        if (novo != null && quantClientes < MAX_CLIENTES) {
            clientes[quantClientes++] = novo;
        }
    }

    public Cliente localizarCliente(int idCli) {
        return Arrays.stream(clientes)
            .filter(cliente -> cliente != null && cliente.hashCode() == idCli)
            .findFirst()
            .orElse(null);
    }

    private Mesa localizarMesaDisponivel(int quantPessoas) {
        return Arrays.stream(mesas)
            .filter(mesa -> mesa != null && mesa.estahLiberada(quantPessoas))
            .findFirst()
            .orElse(null);
    }

    public Requisicao encerrarAtendimento(int idMesa) {
        Requisicao encerrada = localizarRequisicao(idMesa);
        if (encerrada != null) {
            encerrada.encerrar();
        }
        return encerrada;
    }

    public Requisicao processarFila() {
        for (int i = 0; i < requisicoesEmEspera; i++) {
            Requisicao requisicao = espera[i];
            Mesa mesaLivre = localizarMesaDisponivel(requisicao.getQuantPessoas());
            if (mesaLivre != null) {
                atenderRequisicao(requisicao, mesaLivre);
                retirarDaFila(i);
                return requisicao;
            }
        }
        return null;
    }

    private void retirarDaFila(int pos) {
        System.arraycopy(espera, pos + 1, espera, pos, requisicoesEmEspera - pos - 1);
        espera[--requisicoesEmEspera] = null;
    }

    public void registrarRequisicao(Requisicao novaRequisicao) {
        if (novaRequisicao != null && requisicoesEmEspera < MAX_FILA) {
            espera[requisicoesEmEspera++] = novaRequisicao;
        }
    }

    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        requisicao.alocarMesa(mesa);
        atendidas[requisicoesAtendidas++] = requisicao;
    }

    public String statusMesas() {
        String livres = Arrays.stream(mesas)
            .filter(mesa -> mesa != null && mesa.estahLiberada(1))
            .map(Mesa::toString)
            .collect(Collectors.joining("\n", "Mesas livres: \n", "\n"));

        String ocupadas = Arrays.stream(mesas)
            .filter(mesa -> mesa != null && !mesa.estahLiberada(1))
            .map(Mesa::toString)
            .collect(Collectors.joining("\n", "Mesas em atendimento: \n", "\n"));

        return livres + ocupadas;
    }

    public String filaDeEspera() {
        if (requisicoesEmEspera > 0) {
            return Arrays.stream(espera, 0, requisicoesEmEspera)
                .map(Requisicao::toString)
                .collect(Collectors.joining("\n", "Fila de espera: \n", "\n"));
        } else {
            return "Fila vazia";
        }
    }

    public String exibirCardapio() {
        return cardapio.toString();
    }

    public boolean adicionarItemAoPedido(int idMesa, String nomeItem) {
        Requisicao requisicao = localizarRequisicao(idMesa);
        Item item = cardapio.getItem(nomeItem);
        if (requisicao != null && item != null) {
            requisicao.adicionarAoPedido(item);
            return true;
        }
        return false;
    }

    private Requisicao localizarRequisicao(int idMesa) {
        return Arrays.stream(atendidas)
            .filter(requisicao -> requisicao != null && !requisicao.estahEncerrada() && requisicao.ehDaMesa(idMesa))
            .findFirst()
            .orElse(null);
    }
}
