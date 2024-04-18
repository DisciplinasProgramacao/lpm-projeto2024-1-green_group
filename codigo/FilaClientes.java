import java.util.LinkedList;
import java.util.Queue;

public class FilaClientes {
    private Queue<Cliente> clientes;

    public FilaClientes() {
        clientes = new LinkedList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.offer(cliente);
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente proximoCliente() {
        return clientes.poll();
    }
}