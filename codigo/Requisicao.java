import java.time.LocalDateTime;

public class Requisicao {
    private String id;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private int qntdPessoa;
    private Mesa mesa;
    private Cliente cliente;

    public Requisicao(String id, Cliente cliente, int qntdPessoa) {
        this.id = id;
        this.cliente = cliente;
        this.qntdPessoa = qntdPessoa;
        this.horaEntrada = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public int getQntdPessoa() {
        return qntdPessoa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
