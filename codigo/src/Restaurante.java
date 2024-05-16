public class Restaurante {

    private static int MAX_FILA = 1000;
    private static int MAX_MESAS = 10;
    private static int MAX_CLIENTES = 1000;
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
            mesas[quantMesas] = new Mesa(4);
            quantMesas++;
        }
        for (int i = 0; i < 4; i++) {
            mesas[quantMesas] = new Mesa(6);
            quantMesas++;
        }
        for (int i = 0; i < 2; i++) {
            mesas[quantMesas] = new Mesa(8);
            quantMesas++;
        }
    }

    public void addCliente(Cliente novo) {
        if (novo != null) {
            clientes[quantClientes] = novo;
            quantClientes++;
        }
    }

    public Cliente localizarCliente(int idCli) {
        boolean achou = false;
        Cliente cliente = null;
        for (int i = 0; i < quantClientes && !achou; i++) {
            if (clientes[i].hashCode() == idCli) {
                achou = true;
                cliente = clientes[i];
            }
        }
        return cliente;
    }

    private Mesa localizarMesaDisponivel(int quantPessoas) {
        Mesa liberada = null;
        for (int i = 0; i < quantMesas && liberada == null; i++) {
            if (mesas[i].estahLiberada(quantPessoas)) {
                liberada = mesas[i];
            }
        }
        return liberada;
    }

    public Requisicao encerrarAtendimento(int idMesa) {
        Requisicao encerrada = null;
        for (int i = requisicoesAtendidas - 1; i >= 0 && encerrada == null; i--) {
            if (!atendidas[i].estahEncerrada() && atendidas[i].ehDaMesa(idMesa)) {
                atendidas[i].encerrar();
                encerrada = atendidas[i];
            }
        }
        return encerrada;
    }

    public Requisicao processarFila() {
        Requisicao atendida = null;
        for (int i = 0; i < requisicoesEmEspera && atendida == null; i++) {
            Requisicao requisicao = espera[i];
            Mesa mesaLivre = localizarMesaDisponivel(requisicao.getQuantPessoas());
            if (mesaLivre != null) {
                atenderRequisicao(requisicao, mesaLivre);
                retirarDaFila(i);
                atendida = requisicao;
            }
        }
        return atendida;
    }

    private void retirarDaFila(int pos) {
        for (int i = pos + 1; i < requisicoesEmEspera; i++) {
            espera[pos] = espera[pos + 1];
        }
        requisicoesEmEspera--;
        espera[requisicoesEmEspera] = null;
    }

    public void registrarRequisicao(Requisicao novaRequisicao) {
        if (novaRequisicao != null) {
            espera[requisicoesEmEspera] = novaRequisicao;
            requisicoesEmEspera++;
        }
    }

    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        requisicao.alocarMesa(mesa);
        atendidas[requisicoesAtendidas] = requisicao;
        requisicoesAtendidas++;
    }

    public String statusMesas() {
        StringBuilder livres = new StringBuilder("Mesas livres: \n");
        StringBuilder ocupadas = new StringBuilder("Mesas em atendimento: \n");
        for (int i = 0; i < quantMesas; i++) {
            if (mesas[i].estahLiberada(1)) {
                livres.append(mesas[i]).append("\n");
            } else {
                ocupadas.append(mesas[i]).append("\n");
            }
        }
        return livres.toString() + ocupadas.toString();
    }

    public String filaDeEspera() {
        String resposta = "Fila vazia";
        if (requisicoesEmEspera > 0) {
            StringBuilder emEspera = new StringBuilder("Fila de espera: \n");
            for (int i = 0; i < requisicoesEmEspera; i++) {
                emEspera.append(espera[i]).append("\n");
            }
            resposta = emEspera.toString();
        }
        return resposta;
    }

    public String exibirCardapio() {
        return cardapio.toString();
    }

    public boolean adicionarItemAoPedido(int idMesa, String nomeItem) {
        for (int i = 0; i < requisicoesAtendidas; i++) {
            if (!atendidas[i].estahEncerrada() && atendidas[i].ehDaMesa(idMesa)) {
                Item item = cardapio.getItem(nomeItem);
                if (item != null) {
                    atendidas[i].getPedido().adicionarItem(item);
                    return true;
                }
            }
        }
        return false;
    }
}
