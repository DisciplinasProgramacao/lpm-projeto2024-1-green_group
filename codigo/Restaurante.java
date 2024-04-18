import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private List<Mesa> mesas;
    private List<Requisicao> requisicoesAlocadas;
    private List<Requisicao> filaEspera;

    public Restaurante(int numMesas) {
        mesas = new ArrayList<>();
        for (int i = 0; i < numMesas; i++) {
            mesas.add(new Mesa("M" + (i + 1), 4));
        }
        requisicoesAlocadas = new ArrayList<>();
        filaEspera = new ArrayList<>();
    }

    public void criarRequisicao(Cliente cliente, int qntdPessoa) {
        Requisicao requisicao = new Requisicao(generateId(), cliente, qntdPessoa);
        Mesa mesa = olharMesaVaga(qntdPessoa);
        if (mesa != null) {
            alocarRequisicao(requisicao, mesa);
        } else {
            filaEspera.add(requisicao);
        }
    }

    private Mesa olharMesaVaga(int capacidade) {
        return mesas.stream()
                .filter(m -> !m.isOcupado() && m.getLugares() >= capacidade)
                .findFirst()
                .orElse(null);
    }

    private void alocarRequisicao(Requisicao requisicao, Mesa mesa) {
        mesa.setOcupado(true);
        requisicao.setMesa(mesa);
        requisicoesAlocadas.add(requisicao);
        filaEspera.remove(requisicao);
    }

    public void incluirEmFilaDeEspera(Requisicao req) {
        if (!filaEspera.contains(req)) {
            filaEspera.add(req);
        }
    }

    public void liberarRequisicao(Cliente cliente) {
        Requisicao requisicao = requisicoesAlocadas.stream()
                .filter(r -> r.getCliente().equals(cliente))
                .findFirst()
                .orElse(null);
        if (requisicao != null) {
            liberarMesa(requisicao.getMesa());
            requisicoesAlocadas.remove(requisicao);
        }
    }

    public void removerDaFilaDeEspera(String idRequisicao) {
        filaEspera.removeIf(r -> r.getId().equals(idRequisicao));
    }

    private void liberarMesa(Mesa mesa) {
        mesa.setOcupado(false);
        Requisicao proximaRequisicao = preferenciaNaFilaDeEspera(mesa);
        if (proximaRequisicao != null) {
            alocarRequisicao(proximaRequisicao, mesa);
        }
    }

    private Requisicao preferenciaNaFilaDeEspera(Mesa mesa) {
        return filaEspera.stream()
                .filter(r -> r.getQntdPessoa() <= mesa.getLugares())
                .findFirst()
                .orElse(null);
    }

    private String generateId() {
        return "REQ" + System.currentTimeMillis();
    }
}
