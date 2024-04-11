import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int capacidade;
    private List<Cliente> clientesAlocados;

    public Mesa(int capacidade) {
        this.capacidade = capacidade;
        clientesAlocados = new ArrayList<>();
    }

    public boolean adicionarCliente(Cliente cliente) {
        if (!isCheia() && clientesAlocados.size() + cliente.getNumeroPessoas() <= capacidade) {
            clientesAlocados.add(cliente);
            return true;
        }
        return false;
    }

    public void removerCliente(Cliente cliente) {
        clientesAlocados.remove(cliente);
    }

    public boolean isCheia() {
        return clientesAlocados.size() == capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
