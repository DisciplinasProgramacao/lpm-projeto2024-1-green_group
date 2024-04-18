import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Restaurante {
    private Mesa[] mesas;
    private List<Requisicao> requisicoesAlocadas;
    private List<Requisicao> filaEspera;

    public Restaurante(int numMesas) {
        mesas = new Mesa[numMesas];
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
        for (Mesa mesa : mesas) {
            if (!mesa.isOcupado() && mesa.getLugares() >= capacidade) {
                return mesa;
            }
        }
        return null;
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
            requisicao.getMesa().setOcupado(false);
            requisicoesAlocadas.remove(requisicao);
            Requisicao proximaRequisicao = preferenciaNaFilaDeEspera(requisicao.getMesa());
            if (proximaRequisicao != null) {
                alocarRequisicao(proximaRequisicao, requisicao.getMesa());
            }
        }
    }

    public Mesa deslocarReq(Cliente cliente) {
        for (Requisicao r : requisicoesAlocadas) {
            if (r.getCliente().equals(cliente)) {
                Mesa mesa = r.getMesa();
                mesa.setOcupado(false);
                requisicoesAlocadas.remove(r);
                return mesa;
            }
        }
        return null;
    }

    public Requisicao preferenciaNaFilaDeEspera(Mesa mesa) {
        List<Requisicao> candidatos = filaEspera.stream()
                .filter(r -> r.getQntdPessoa() <= mesa.getLugares())
                .collect(Collectors.toList());

        if (!candidatos.isEmpty()) {
            return candidatos.get(0);
        }

        return null;
    }

    public void removerDaFilaDeEspera(String idRequisicao) {
        filaEspera.removeIf(r -> r.getId().equals(idRequisicao));
    }

    private String generateId() {
        return "REQ" + System.currentTimeMillis();
    }
}
