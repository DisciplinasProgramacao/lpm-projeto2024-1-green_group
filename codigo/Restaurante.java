import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private List<Mesa> mesasDisponiveis;
    private FilaClientes filaEspera;

    public Restaurante() {
        mesasDisponiveis = new ArrayList<>();
        filaEspera = new FilaClientes();
    }

    public void adicionarCliente(Cliente cliente) {
        if (mesasDisponiveis.isEmpty()) {
            filaEspera.adicionarCliente(cliente);
        } else {
            alocarMesa(cliente);
        }
    }

    private void alocarMesa(Cliente cliente) {
        Mesa mesa = olharMesaVaga(cliente.getqntdPessoa());
        if (mesa != null) {
            mesa.adicionarCliente(cliente);
        } else {
            filaEspera.adicionarCliente(cliente);
        }
    }

    private Mesa olharMesaVaga(int numPessoas) {
        for (Mesa mesa : mesasDisponiveis) {
            if (!mesa.isCheia() && mesa.getCapacidade() >= numPessoas) {
                return mesa;
            }
        }
        return null;
    }

    public void liberarMesa(Mesa mesa) {
        mesasDisponiveis.add(mesa);
    }
}
